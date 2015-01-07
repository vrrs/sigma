package com.ebay.sigma.services.searchEngines;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ebay.sigma.property.NonBlockingServerProperties;
import com.ebay.sigma.util.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

final class GigaBlastEngine implements SearchEngine {
	private final String baseURL;
	
	GigaBlastEngine(){
		final String apiKey = NonBlockingServerProperties.getProperties()
				 										 .get("searchEngine.GigaBlastEngine.apiKey", "AIzaSyDfff1X8DrLHvtsB2FRmJfKx5u5CfzcXYns");
		this.baseURL = "http://www.gigablast.com/search??key=" + apiKey + "&q=";
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
		Element tdWithResults = doc.select("div").first();
		for(Element aTagWithResult : tdWithResults.select("a")){
			if(aTagWithResult.select("font").isEmpty())		continue;
			SearchResult searchResult = getSearchResult(aTagWithResult);
			searchResults.add(searchResult);
		}
		return JSONMapper.getInstance()
						 .toJSON(searchResults);
	}
	
	private SearchResult getSearchResult(Element aTagWithResult) {
		SearchResult searchResult = new SearchResult();
		searchResult.setTitle(aTagWithResult.select("font").first().text());
		searchResult.setLink(aTagWithResult.attr("href"));
		searchResult.setSummary("");
		return searchResult;
	}

	@Override
	public String getName() {
		return "GigaBlast";
	}

}
