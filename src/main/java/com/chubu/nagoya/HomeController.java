package com.chubu.nagoya;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/")
	public String main(Model model) throws Exception{
		model.addAttribute("msg", "本日もご活躍、誠にありがとうございます。");
		return "index";
	}
	
	 @RequestMapping(value = "index.do", method = RequestMethod.GET)
	    public String home(Locale locale, Model model) throws Exception {
	        logger.info("Welcome home! The client locale is {}.", locale);
	        
	        Date date = new Date();
	        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	        
	        String formattedDate = dateFormat.format(date);
	        
	        model.addAttribute("serverTime", formattedDate );
	        
	        // => servlet-context.xml
	        // <beans:property name="prefix" value="/WEB-INF/views/" />
	        // <beans:property name="suffix" value=".jsp" />
	        return "index";
	    }

	
	
}