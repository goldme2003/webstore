package com.zlxy.webstore.controller;

import java.io.File;
import java.util.ArrayList;
//import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zlxy.webstore.domain.Product;
import com.zlxy.webstore.exception.NoProductsFoundUnderCategoryException;
import com.zlxy.webstore.exception.ProductNotFoundException;
//import com.zlxy.webstore.domain.repository.ProductRepository;
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
		List<Product> prod_by_categ = new ArrayList<Product>();
		prod_by_categ = productService.getProductsByCategory(productCategory);
		if (prod_by_categ == null || prod_by_categ.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}
		model.addAttribute("products", prod_by_categ);
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
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct, BindingResult result, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			return "addProduct";
		}

		String[] suppressedFields = result.getSuppressedFields();
		if(suppressedFields.length > 0) {
			throw new RuntimeException("Attemping to bind disallowed fileds:" + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		
		MultipartFile productImage = newProduct.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");

		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(
						new File(rootDirectory + "resources\\images\\" + newProduct.getProductId() + ".png"));
			} catch (Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
			}
		}

		
		productService.addProduct(newProduct);
		
		
		return "redirect:/products";
		
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setDisallowedFields("UnitsInOrder", "discontinued");
		binder.setAllowedFields("productId", "name", "unitPrice", "description", "manufacturer", "category", "unitsInStock", "condition", "productImage");
		
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidproductid", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
		
		mav.setViewName("errorpage/productNotFound");
		
		return mav;
		
	}

}
