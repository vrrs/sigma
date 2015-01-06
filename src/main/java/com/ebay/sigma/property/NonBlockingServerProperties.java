package com.ebay.sigma.property;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NonBlockingServerProperties  implements ServerProperties{
	private static volatile ServerProperties instance;
	private static final Logger log = LoggerFactory.getLogger(NonBlockingServerProperties.class);
	
	private final AtomicReference<Properties> properties = new AtomicReference<Properties>(new Properties());
	
	private NonBlockingServerProperties(){}

	@Override
	public String get(String key, String defaultValue) {
		return getProperty(key, defaultValue);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return Integer.parseInt(getProperty(key, defaultValue));
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return Boolean.parseBoolean(getProperty(key, defaultValue));
	}
	
	private <T> String getProperty(String key, T defaultValue){
		return properties.get()
						 .getProperty(key, String.valueOf(defaultValue));
	}

	@Override
	public void reload() throws FileNotFoundException, IOException {
		log.info("Loading server properties...");
		Properties oldValue;
		Properties newValue = new Properties();
		newValue.load(new FileInputStream(CONFIGURATION_FILE));
		do{
			 oldValue = properties.get();
		}
		while(!properties.compareAndSet(oldValue, newValue));
		log.info("Successfully finished loading server properties.");
	}
	
	public static ServerProperties getProperties(){
		if(instance == null){
			synchronized(NonBlockingServerProperties.class){
				if(instance == null)
					instance = new NonBlockingServerProperties();
			}
		}
		return instance;
	}

}
