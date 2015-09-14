package com.github.ivos.springjpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.ivos.springjpa.customer.CustomerService;

public class Application {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		new Application().run();
	}

	private void run() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(ApplicationConfiguration.class);
			context.refresh();

			doWithContext(context);
		}
	}

	private void doWithContext(ApplicationContext context) {
		CustomerService service = context.getBean(CustomerService.class);

		service.testDrive();

		try {
			service.verifyConstraints();
		} catch (DataIntegrityViolationException e) {
			logger.info("Nulls not allowed in customer.");
		}
	}

}
