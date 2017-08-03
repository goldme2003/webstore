package com.zlxy.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zlxy.webstore.domain.*;
import com.zlxy.webstore.domain.repository.*;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository{
	private List<Customer> customer_list = new ArrayList<Customer>();
	public InMemoryCustomerRepository() {
				
		Customer ind_customer = new Customer(1, "Jimmy");
		ind_customer.setAddress("Hankou");
		ind_customer.setNoOfOrderMade(3);
		
		Customer comp_customer = new Customer(2, "NARI");
		comp_customer.setAddress("Wuchang");
		comp_customer.setNoOfOrderMade(5000);
		
		
		customer_list.add(ind_customer);
		customer_list.add(comp_customer);
	}

	public List<Customer> getAllCustomers() {

		return customer_list;
	}

}
