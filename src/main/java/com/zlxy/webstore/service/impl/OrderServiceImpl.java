package com.zlxy.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zlxy.webstore.domain.Product;
import com.zlxy.webstore.domain.repository.ProductRepository;
import com.zlxy.webstore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductRepository productRepository;

	
	public void processOrder(String productId, int quantity) {
		

		Product productById = productRepository.getProductById(productId);

		if (productById.getUnitsInStock() < quantity) {
			throw new IllegalArgumentException(
					"Out of Stock. Available Units in stock" + productById.getUnitsInStock());
		}

		productById.setUnitsInStock(productById.getUnitsInStock() - quantity);
	}
}
