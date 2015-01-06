package com.ebay.sigma.property;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ServerProperties {
	
	public static final String CONFIGURATION_FILE = "/data/sigma/conf/global.prop";
	
	String get(String key, String defaultValue);
	int getInt(String key, int defaultValue);
	boolean getBoolean(String key, boolean defaultValue);
	
	void reload() throws FileNotFoundException, IOException;
}
