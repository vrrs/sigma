package com.ebay.sigma.services;

import javax.servlet.http.HttpServletRequest;

final class RequestWrapper {
	
	private static final String QUERY_PARAMETER = "query";
	private final HttpServletRequest req;

	RequestWrapper(HttpServletRequest req){
		this.req = req;
	}
	
	public String getQuery(){
		return req.getParameter(QUERY_PARAMETER);
	}

}
