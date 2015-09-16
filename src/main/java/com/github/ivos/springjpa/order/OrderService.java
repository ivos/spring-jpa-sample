package com.github.ivos.springjpa.order;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ivos.springjpa.customer.Customer;
import com.github.ivos.springjpa.customer.CustomerRepository;

@Service
@Transactional
public class OrderService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderRepository repo;

	@Autowired
	private CustomerRepository customerRepository;

	public void clearAll() {
		logger.info("Clear orders");
		repo.deleteAll();
	}

	public Long createOrders(Long customerId) {
		logger.info("Retrieving customer by id:");
		Customer customer = customerRepository.findOne(customerId);
		logger.info("Found customer {}.", customer);

		Order order;
		logger.debug("Creating orders.");

		order = new Order();
		order.setCustomer(customer);
		order.setEffectiveDate(new Date());
		order.setOrderNumber("ORD-0001");
		addItem(order, "Spy mic", 2);
		addItem(order, "Notebook", 4);
		addItem(order, "Hummer", 3);
		repo.save(order);

		order = new Order();
		order.setCustomer(customer);
		order.setEffectiveDate(new Date());
		order.setOrderNumber("ORD-0002");
		addItem(order, "Mobile phone", 4);
		addItem(order, "IP table phone", 6);
		repo.saveAndFlush(order);

		return order.getId();
	}

	private void addItem(Order order, String product, int quatity) {
		OrderItem item = new OrderItem();
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(quatity);
		order.getOrderItems().add(item);
	}

	public void retrieveOrder(Long orderId) {
		logger.info("Find order");
		Order order = repo.findOne(orderId);
		logger.info("Get customer");
		Customer customer = order.getCustomer();
		logger.info("Access customer {}", customer);
		logger.info("Get items");
		List<OrderItem> orderItems = order.getOrderItems();
		logger.info("Get items size");
		orderItems.size();
		logger.info("Get first item");
		OrderItem orderItem = orderItems.get(0);
		logger.info("Access first item {}", orderItem);
	}

}
