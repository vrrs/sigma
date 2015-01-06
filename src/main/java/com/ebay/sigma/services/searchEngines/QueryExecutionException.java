package com.ebay.sigma.services.searchEngines;

public class QueryExecutionException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public QueryExecutionException(Throwable e) {
		super(e);
	}
	public QueryExecutionException(String msg, Throwable e) {
		super(msg,e);
	}

}
