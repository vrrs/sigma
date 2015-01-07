package com.ebay.sigma.services.searchEngines;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TestGigaBlastEngine {
	
	private static final String EXPECTATION = "{\"title\":\"Chamber' s Blog - Hola Midland\",\"link\":\"http://midlandhcc.wordpress.com/\",\"summary\":\"\"}";
	
	@Test
	public void whenQueryIsGivenExpectNoExceptionAndContainExpectation() throws JsonProcessingException, QueryExecutionException{
		SearchEngine engine = new GigaBlastEngine();
		String actual = engine.getResults("hola+s");
		assertThat(actual).contains(EXPECTATION);
	}
}
