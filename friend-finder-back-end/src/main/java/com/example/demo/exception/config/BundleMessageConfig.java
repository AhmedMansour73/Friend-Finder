package com.example.demo.exception.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class BundleMessageConfig {
	
	@Value("${spring.messages.basename}")
	private String baseName;

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename(baseName);
		source.setDefaultEncoding("UTF-8");
		source.setUseCodeAsDefaultMessage(true); // If the key is not found
	    source.setCacheSeconds(3600); // cache for one hour
		return source;
	}
}
