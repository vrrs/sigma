package com.ebay.sigma.services.searchEngines;

import com.ebay.sigma.property.NonBlockingServerProperties;


final class GoogleSearchEngine implements SearchEngine {
	
	private final String baseURL;
	
	GoogleSearchEngine(){
		final String apiKey = NonBlockingServerProperties.getProperties()
				 										 .get("searchEngine.google.apiKey", "AIzaSyDhv1X8DrLHvtsB2FRmJfKx5u5CfzcXYns");
		this.baseURL = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=017576662512468239146:omuauf_lfve&q=";
	}
	
	@Override
	public String getResults(String query) throws QueryExecutionException {
		final String completeURL = baseURL + query;
		return null;
	}
	
	@Override
	public String getName() {
		return "bing";
	}

}
