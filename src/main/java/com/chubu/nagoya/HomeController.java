package com.chubu.nagoya;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public String main(Model model) throws Exception{
		return "index";
	}
	
	 @RequestMapping(value = "index.do", method = RequestMethod.GET)
	    public String home(Model model) throws Exception {
	       
	        return "index";
	    }

	
	
}