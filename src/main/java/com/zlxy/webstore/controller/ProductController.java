package com.zlxy.webstore.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zlxy.webstore.domain.Product;
import com.zlxy.webstore.domain.repository.ProductRepository;
import com.zlxy.webstore.service.ProductService;
//import com.zlxy.webstore.domain.repository.impl.InMemoryProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	

	@RequestMapping()
	public ModelAndView list(Model model) {
		
/*		Product iphone = new Product("P1234", "iPhone 5s", new BigDecimal(500));
		iphone.setDescription("Apple iPhone 5s smartphone with 4.00-inch 640x1136 display and 8-megapixel rear camera");
		iphone.setCategory("Smart Phone");
		iphone.setManufacturer("Apple");
		iphone.setUnitsInStock(1000);*/
		ModelAndView modelandview = new ModelAndView();
		
		modelandview.addObject("products", productService.getAllProducts());
		modelandview.setViewName("products");

//		model.addAttribute("products", productService.getAllProducts());

		return modelandview;
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		model.addAttribute("products", productService.getProductsByCategory(productCategory));
		return "products";
	}
	
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(@MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> filterParams,
			Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
	

	@RequestMapping("/product")
	public ModelAndView getProductById(@RequestParam("id")String productId) {
		ModelAndView prod_url = new ModelAndView();
		prod_url.addObject("product", productService.getProductById(productId));
		prod_url.setViewName("product");
		
		//model.addAttribute("product", productService.getProductById(productId));
		
		
		
		return prod_url;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
		
		productService.addProduct(newProduct);
		
		
		return "redirect:/products";
		
	}
	


	

}
