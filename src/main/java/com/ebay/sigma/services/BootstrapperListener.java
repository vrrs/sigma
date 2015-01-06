package com.ebay.sigma.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ebay.sigma.property.NonBlockingServerProperties;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextListener;

@WebListener
public class BootstrapperListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(BootstrapperListener.class);

 
    public void contextInitialized(ServletContextEvent arg0)  { 
    	log.info("Starting to initialize API context...");
    	try {
    		NonBlockingServerProperties.getProperties().reload();
    	} catch (FileNotFoundException e) {
    		throw new RuntimeException("Property File not found", e);
    	} catch (IOException e) {
    		throw new RuntimeException("IO error occur while loading property file.", e);
    	}
    	log.info("API context initiliazed.");
    }
    
    public void contextDestroyed(ServletContextEvent arg0)  {}
	
}
