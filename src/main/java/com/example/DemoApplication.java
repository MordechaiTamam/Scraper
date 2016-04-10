package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		ctx.getBean(Scraper.class).scrap();
	}

	@Bean
	Scraper scraper() {
		return new Scraper();
	}

	@Bean
	ObjectMapper mapper(){
		ObjectMapper retVal = new ObjectMapper();
		retVal.enable(SerializationFeature.INDENT_OUTPUT);
		return retVal;
	}

}
