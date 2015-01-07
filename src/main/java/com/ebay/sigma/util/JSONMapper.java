package com.ebay.sigma.util;

import java.util.List;
import com.ebay.sigma.services.searchEngines.SearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JSONMapper {
	private static final JSONMapper instance = new JSONMapper(); 
	
	private JSONMapper(){}
	
	public static JSONMapper getInstance(){
		return instance;
	}
	
	public String toJSON(List<SearchResult> searchResults) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(searchResults);
	}

}
