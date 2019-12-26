package com.estafet.microservices.scrum.lib.commons.jms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AbstractTopic {

	protected String getProperty(String property) {
		Properties prop = new Properties();
		InputStream inputStream = null;
		String propFileName = "amq.properties";
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
		return prop.getProperty(property);
	}
	
	protected Connection createConnection() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(getProperty("jboss.amq.broker.url"));
		return connectionFactory.createConnection(getProperty("jboss.amq.broker.user"),
				getProperty("jboss.amq.broker.password"));
	}
	
}
