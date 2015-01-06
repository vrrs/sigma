package com.ebay.sigma.services.searchEngines;

import com.ebay.sigma.property.NonBlockingServerProperties;


final class YahooSearchEngine implements SearchEngine {
	
	private final String baseURL;
	
	YahooSearchEngine(){
		final String apiKey = NonBlockingServerProperties.getProperties()
				 										 .get("searchEngine.yahoo.apiKey", "AIzaSyDhv1X8DrLHvtsB2FRmJfKx5u5CfzcXYns");
		this.baseURL = "";
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
