package com.ebay.sigma.services.searchEngines;

import java.io.IOException;
import java.util.List;

import com.ebay.sigma.property.NonBlockingServerProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.ebay.sigma.util.JSONMapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

final class GoogleSearchEngine implements SearchEngine {
	private static final String FAKE_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.168";
	private final String baseURL;
	
	GoogleSearchEngine(){
		final String apiKey = NonBlockingServerProperties.getProperties()
				 										 .get("searchEngine.google.apiKey", "AIklklDrLHvtsB2FRmJfKx5u5CfzcXYns");
		this.baseURL = "https://www.google.com/search?key=" + apiKey + "&ie=UTF-8&q=";
	}
	
	@Override
	public String getResults(String query) throws QueryExecutionException {
		final String completeURL = baseURL + query;
		try {
			Document doc = Jsoup.connect(completeURL).userAgent(FAKE_USER_AGENT).get();
			return querySearchEngine(doc);
		} 
		catch (JsonProcessingException e) {
			throw new QueryExecutionException("Failed to parse HTML response.", e);
		}catch (IOException e) {
			throw new QueryExecutionException("URL given is malformed.", e);
		}
	}
	
	private String querySearchEngine(Document doc) throws JsonProcessingException  {
		List<SearchResult> searchResults = Lists.newArrayList();
		Element divWithResults = doc.select("div.srg").first();
		for(Element liWithResult : divWithResults.select("li")){
			Element resultBody = liWithResult.select("div").first();
			if(resultBody == null)	continue;
			SearchResult searchResult = getSearchResult(resultBody);
			searchResults.add(searchResult);
		}
		return JSONMapper.getInstance()
						 .toJSON(searchResults);
	}

	private SearchResult getSearchResult(Element resultBody ) {
		SearchResult searchResult = new SearchResult();
		searchResult.setTitle(resultBody.select("h1,h2,h3,h4,h5,h6").first().text());
		Element resultContent = resultBody.select("div").first();
		searchResult.setLink(resultContent.select("cite").first().text());
		searchResult.setSummary(getSummary(resultContent));
		return searchResult;
	}

	private String getSummary(Element resultContent) {
		StringBuilder buff = new StringBuilder();
		for(Element span : resultContent.select("span")){
			buff.append(span.text());
		}
		return buff.toString();
	}

	@Override
	public String getName() {
		return "google";
	}

}
