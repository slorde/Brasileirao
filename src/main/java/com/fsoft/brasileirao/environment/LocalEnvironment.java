package com.fsoft.brasileirao.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalEnvironment implements CommandLineRunner {

	@Autowired
	private EnvironmentService service;
	
	@Override
	public void run(String... args) throws Exception {
	//	service.execute();
	}

}
