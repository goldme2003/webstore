package com.zlxy.webstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlxy.webstore.domain.Product;
import com.zlxy.webstore.domain.repository.ProductRepository;
import com.zlxy.webstore.exception.ProductNotFoundException;
import com.zlxy.webstore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository product_repository;
	
	private List<Product> listOfProducts = new ArrayList<Product>();

	public List<Product> getAllProducts() {

		return product_repository.getAllProducts();
	}
	
	public Product getProductById(String productId) {
		Product productById = null;
		
		listOfProducts = product_repository.getAllProducts();

		for (Product product : listOfProducts) {
			if (product != null && product.getProductId() != null && product.getProductId().equals(productId)) {
				productById = product;
				break;
			}
		}

		if (productById == null) {
//			throw new IllegalArgumentException("No products found with the product id: " + productId);
			throw new ProductNotFoundException("No products found with ID:" + productId);
		}

		return productById;
	}
	
	public List<Product> getProductsByCategory(String category) {
		return product_repository.getProductsByCategory(category);
	}
	
	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		    return product_repository.getProductsByFilter(filterParams);
	}
	
	public void addProduct(Product product) {
		product_repository.addProduct(product);
	}


	

}
