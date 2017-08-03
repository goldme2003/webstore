package com.zlxy.webstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlxy.webstore.domain.Customer;
import com.zlxy.webstore.domain.repository.CustomerRepository;
import com.zlxy.webstore.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	List<Customer> all_customer = new ArrayList<Customer>();
	
	@Autowired
	private CustomerRepository customer_repository;
	
	public List<Customer> getAllCustomers() {
		all_customer = customer_repository.getAllCustomers();
		return all_customer;
	}
	

}
