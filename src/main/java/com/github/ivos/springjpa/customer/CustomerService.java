package com.github.ivos.springjpa.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerRepository repo;

	public void testDrive() {
		logger.info("Saving customers.");
		repo.save(new Customer("Jack", "Bauer"));
		repo.save(new Customer("Chloe", "O'Brian"));
		repo.save(new Customer("Kim", "Bauer"));
		repo.save(new Customer("David", "Palmer"));
		repo.save(new Customer("Michelle", "Dessler"));

		logger.info("Retrieving all customers:");
		repo.findAll().forEach(customer -> {
			logger.info("Found customer {}.", customer);
		});

		logger.info("Retrieving customer by id:");
		logger.info("Found customer {}.", repo.findOne(1L));

		logger.info("Retrieving customer by last name:");
		repo.findByLastName("Bauer").forEach(customer -> {
			logger.info("Found customer {}.", customer);
		});
	}

	public void verifyConstraints() {
		logger.info("Verfying constraints.");
		repo.save(new Customer());
	}

}
