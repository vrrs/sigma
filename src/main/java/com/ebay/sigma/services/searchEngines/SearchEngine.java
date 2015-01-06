package com.ebay.sigma.services.searchEngines;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface SearchEngine {
	
	String getResults(String query) throws QueryExecutionException, JsonProcessingException;
	String getName();
}
