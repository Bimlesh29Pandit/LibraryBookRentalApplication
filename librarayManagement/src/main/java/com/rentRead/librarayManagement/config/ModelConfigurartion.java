package com.rentRead.librarayManagement.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ModelConfigurartion {
	
	@Bean
	public ModelMapper ModelMapper() {
		return new ModelMapper();
	}

}
