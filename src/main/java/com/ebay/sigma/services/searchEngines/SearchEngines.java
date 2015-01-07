package com.ebay.sigma.services.searchEngines;

public final class SearchEngines {
	
	private SearchEngines(){}
	
	public static SearchEngines getInstance(){
		return new SearchEngines();
	}
	
	public SearchEngine newBingSearchEngine(){
		return new BingSearchEngine();
	}
	
	public SearchEngine newGigaBlastEngine(){
		return new GigaBlastEngine();
	}

	public SearchEngine newGoogleSearchEngine(){
		return new GoogleSearchEngine();
	}
}
