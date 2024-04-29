package com.Spring.Boot.EU;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    
	
	 
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	System.out.println("Welcome");
	   // Reading the application-context file using
	    // class path of spring context xml file
	
	//RawdataService rawdata = new RawdataService();
	//rawdata.Exportexcel();
	
	EUSummaryService summary = new EUSummaryService();
	summary.Exportexcel1();

	
	}
	
	
	}

