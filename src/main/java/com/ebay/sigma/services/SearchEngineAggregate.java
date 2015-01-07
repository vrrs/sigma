package com.ebay.sigma.services;

import java.util.List;
import java.util.Map;

import com.ebay.sigma.services.searchEngines.QueryExecutionException;
import com.ebay.sigma.services.searchEngines.SearchEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

final class SearchEngineAggregate implements SearchEngine {
	private final List<SearchEngine> engines = Lists.newArrayList();
	
	public SearchEngineAggregate add(SearchEngine engine){
		engines.add(engine);
		return this;
	}

	@Override
	public String getResults(String query) throws QueryExecutionException, JsonProcessingException {
		Map<String, Object> jsonMap = Maps.newHashMap();
		for(SearchEngine engine: engines){
			jsonMap.put(engine.getName(), engine.getResults(query));
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(jsonMap);
	}

	@Override
	public String getName() {
		return "aggregateSearchEngine";
	}
	
	
}
