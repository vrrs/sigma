package com.ebay.sigma.services;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebay.sigma.services.searchEngines.QueryExecutionException;
import com.ebay.sigma.services.searchEngines.SearchEngine;
import com.ebay.sigma.services.searchEngines.SearchEngines;

@WebServlet(
        urlPatterns = { "/search/v1/aggregate" }, 
        initParams = { 
                @WebInitParam(name = "query", value = "ebay")
        })

public final class SearchAggregatorService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(SearchAggregatorService.class);
	private SearchEngine engine;
	
	public void init(ServletConfig config) throws ServletException  {
		 engine = getSearchEngine();
	}

	private SearchEngine getSearchEngine() {
		SearchEngines engines = SearchEngines.getInstance();
		SearchEngineAggregate aggregateEngine = new SearchEngineAggregate().add(engines.newBingSearchEngine())
																			.add(engines.newGoogleSearchEngine())
																			.add(engines.newGigaBlastEngine());
		return aggregateEngine;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException{
		RequestWrapper requestWrapper = new RequestWrapper(req);
		try {
			String results = engine.getResults(requestWrapper.getQuery());
			populateResponse(response, results);
		} catch (QueryExecutionException e) {
			response.sendError(500, "Error occurred while retrieving results from one of our supported search engines..");	
		}
	}

	private void populateResponse(HttpServletResponse response, String results) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		out.write(results.getBytes());
		out.flush();
		out.close();
	}
}
