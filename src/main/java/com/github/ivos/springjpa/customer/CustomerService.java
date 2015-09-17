package com.github.ivos.springjpa.customer;

import java.util.Arrays;

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
		Long createdId = Arrays.asList("Jack Bauer,Chloe O'Brian,Kim Bauer,David Palmer,Michelle Dessler".split(","))
				.stream().map(name -> {
					String[] names = name.split(" ");
					Customer created = new Customer(names[0], names[1]);
					repo.save(created);
					return created.getId();
				}).reduce((one, next) -> {
					return one;
				}).get();

		logger.info("Retrieving all customers:");
		repo.findAll().forEach(customer -> {
			logger.info("Found customer {}.", customer);
		});

		logger.info("Retrieving customer by id:");
		Customer found = repo.findOne(createdId);
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
