package com.leonti.slickpm.service;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;


@Service("configService")
public class ConfigurationService {

	Properties props;
	
	public String getProperty(String name) {
		return readPropertiesFile().getProperty(name);
	} 
	
	private Properties readPropertiesFile() {
		
		if (props == null) {
			try {
				props = PropertiesLoaderUtils.loadAllProperties("configuration.properties");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return props;
	}
}
