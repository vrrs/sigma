package com.ebay.sigma.services.searchEngines;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TestGoogleSearchEngine {
	
	private final String EXPECTATION = "{\"title\":\"Mami - Wikipedia, the free encyclopedia\",\"link\":\"en.wikipedia.org/wiki/Mami\",\"summary\":\"Mami may refer to: Mami Sasazaki, a member of japanese girl rock band \\\"SCANDAL\\\"; Mami (goddess), a goddess in the Babylonian epic Atra-Hasis; Mami ...\"},";
	
	@Test
	public void whenQueryIsGivenExpectNoExceptionAndContainExpectation() throws JsonProcessingException, QueryExecutionException{
		SearchEngine engine = new GoogleSearchEngine();
		String actual = engine.getResults("mami");
		assertThat(actual).contains(EXPECTATION);
	}
}
