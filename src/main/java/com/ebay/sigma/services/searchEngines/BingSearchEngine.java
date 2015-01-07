package com.ebay.sigma.services.searchEngines;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ebay.sigma.util.JSONMapper;
import com.ebay.sigma.property.NonBlockingServerProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

final class BingSearchEngine implements SearchEngine {
	private static final String RESULT_SELECTOR = "li.sa_wr";
	private static final String RESULT_CONTENT_SELECTOR = "div div";
	private static final String SUMMARY_SELECTOR = "p";
	private static final String TITLE_SELECTOR = "div.sb_tlst h3 a";
	private static final String LINK_SELECTOR = "div.sb_meta cite";
	private final String baseURL;
	
	BingSearchEngine(){
		final String apiKey = NonBlockingServerProperties.getProperties()
				 										 .get("searchEngine.bing.apiKey", "AIzaSyDhv1X8DrLHvtsB2FRmJfKx5u5CfzcXYns");
		this.baseURL = "http://www.bing.com/search?key=" + apiKey + "&q=";
	}

	@Override
	public String getResults(String query) throws QueryExecutionException {
		final String completeURL = baseURL + query;
		try {
			 final WebClient webClient = new WebClient();
			 final HtmlPage page = (HtmlPage) webClient.getPage(completeURL);
			 return parseHTMLAndGetResultsInJSON(Jsoup.parse(page.asXml()));
		} 
		catch (JsonProcessingException e) {
			throw new QueryExecutionException("Failed to parse HTML response.", e);
		}catch (IOException e) {
			throw new QueryExecutionException("URL given is malformed.", e);
		}
	}

	private String parseHTMLAndGetResultsInJSON(Document doc) throws JsonProcessingException {
		List<SearchResult> searchResults = Lists.newArrayList();
		Element ulTagWithResults = doc.select("div#results").first().select("ul").first();
		for(Element liTagWithResult : ulTagWithResults.select(RESULT_SELECTOR)){
			Element divWithResultContent = liTagWithResult.select(RESULT_CONTENT_SELECTOR).first();
			SearchResult searchResult = getSearchResult(divWithResultContent);
			searchResults.add(searchResult);
		}
		return JSONMapper.getInstance()
						 .toJSON(searchResults);
	}

	private SearchResult getSearchResult(Element divWithResultContent) {
		SearchResult searchResult = new SearchResult();
		searchResult.setTitle(getTitleFrom(divWithResultContent));
		searchResult.setLink(getLinkFrom(divWithResultContent));
		searchResult.setSummary(getSummaryFrom(divWithResultContent));
		return searchResult;
	}

	private String getSummaryFrom(Element divWithResultContent) {
		return divWithResultContent.select(SUMMARY_SELECTOR).first().text();
	}

	private String getLinkFrom(Element divWithResultContent) {
		 return divWithResultContent.select(LINK_SELECTOR).first().text();
	}

	private String getTitleFrom(Element divWithResultContent) {
		 return divWithResultContent.select(TITLE_SELECTOR).first().text();
	}

	@Override
	public String getName() {
		return "bing";
	}

}
