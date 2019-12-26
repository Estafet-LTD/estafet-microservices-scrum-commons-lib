package com.estafet.microservices.scrum.lib.commons.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

	private static PropertyUtils instance;
	
	private Properties prop = null;
	
	private PropertyUtils() {}

	public String getProperty(String property) {
		if (prop == null) {
			prop = new Properties();
			InputStream inputStream = null;
			String propFileName = "integration-test.properties";
			try {
				inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new RuntimeException("property file '" + propFileName + "' not found in the classpath");
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return prop.getProperty(property);
	}
	
	public static PropertyUtils instance() {
		if (instance == null) {
			instance = new PropertyUtils();
		}
		return instance;
	}
}
