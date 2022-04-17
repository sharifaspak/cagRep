package com.cag;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EReportingCagApplication {


// Test project commits 2
	public static void main(String[] args) {
		SpringApplication.run(EReportingCagApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}
	
	@Bean
	public DataFormatter dataFormatter() {
		return new DataFormatter();
	}
	
	
}
