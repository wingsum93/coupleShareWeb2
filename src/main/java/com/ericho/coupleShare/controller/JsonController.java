package com.ericho.coupleShare.controller;

import com.ericho.coupleShare.service.ProductService;
import com.ericho.coupleShare.service.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/json")
public class JsonController {
	private Logger log = LoggerFactory.getLogger(ApiController.class);
	private Gson gson = new Gson();
	
	@Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;
    
    
    
}
