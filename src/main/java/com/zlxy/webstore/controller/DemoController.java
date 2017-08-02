package com.zlxy.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
	
	@RequestMapping("/wel")
	public String index() {
		return "welcome"; 
	}

}
