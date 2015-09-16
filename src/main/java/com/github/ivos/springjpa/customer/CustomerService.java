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

	public void clearAll() {
		logger.info("Clear customers");
		repo.deleteAllInBatch();
	}

	public Long testDrive() {
		logger.info("Saving customers.");
		repo.save(new Customer("Jack", "Bauer"));
		repo.save(new Customer("Chloe", "O'Brian"));
		repo.save(new Customer("Kim", "Bauer"));
		repo.save(new Customer("David", "Palmer"));
		Customer created = new Customer("Michelle", "Dessler");
		repo.save(created);

		logger.info("Retrieving all customers:");
		repo.findAll().forEach(customer -> {
			logger.info("Found customer {}.", customer);
		});

		logger.info("Retrieving customer by id:");
		Customer found = repo.findOne(created.getId());
		logger.info("Found customer {}.", found);

		logger.info("Retrieving customer by last name:");
		repo.findByLastName("Bauer").forEach(customer -> {
			logger.info("Found customer {}.", customer);
		});

		return found.getId();
	}

	public void verifyConstraints() {
		logger.info("Verfying constraints.");
		repo.save(new Customer());
	}

}
