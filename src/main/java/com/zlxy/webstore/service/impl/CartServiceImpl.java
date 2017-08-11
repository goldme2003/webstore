package com.zlxy.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlxy.webstore.domain.Cart;
import com.zlxy.webstore.domain.repository.CartRepository;
import com.zlxy.webstore.service.CartService;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	

	@Override
	public Cart create(Cart cart) {
		
		return cartRepository.create(cart);
	}

	@Override
	public Cart read(String cartId) {
		
		return cartRepository.read(cartId);
	}

	@Override
	public void update(String cartId, Cart cart) {
		
		cartRepository.update(cartId, cart);
		
	}

	@Override
	public void delete(String cartId) {
		
		cartRepository.delete(cartId);
		
		
	}
	

}
