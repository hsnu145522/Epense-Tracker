package com.example.expense_tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseTrackerApplication {

	private static final Logger log = LoggerFactory.getLogger(ExpenseTrackerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
		log.info("Application Started");
	}

}
