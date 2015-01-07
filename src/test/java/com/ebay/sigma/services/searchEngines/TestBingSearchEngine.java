package com.ebay.sigma.services.searchEngines;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TestBingSearchEngine {
	private final String EXPECTATION = "{\"title\":\"Bluvertigo - Iodio - YouTube\",\"link\":\"www.youtube.com /watch?v=0uepgU63b-U\",\"summary\":\"Nov 24, 2009  · Music video by Bluvertigo performing Iodio . (C) 1997 SONY BMG MUSIC ENTERTAINMENT (Italy) S.p.A.\"},";
			
	@Test
	public void whenQueryIsGivenExpectNoExceptionAndContainExpectation() throws JsonProcessingException, QueryExecutionException{
		SearchEngine engine = new BingSearchEngine();
		String actual = engine.getResults("iodfio");
		assertThat(actual).contains(EXPECTATION);
	}

}
