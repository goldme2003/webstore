package com.zlxy.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zlxy.webstore.service.CustomerService;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customer_service;

	@RequestMapping("/customer")
	public String show_customer(Model model) {
		
		model.addAttribute("customers", customer_service.getAllCustomers());
		
		return "all_customer";
		
	}
	
	
	

}
