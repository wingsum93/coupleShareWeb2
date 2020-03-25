package com.ericho.coupleShare.controller;

import com.ericho.coupleShare.dao.ProductDao;
import com.ericho.coupleShare.model.Product;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/test")
@Controller
public class TestController {
    Logger log = LoggerFactory.getLogger(TestController.class);
    Gson gs = new Gson();
    @Autowired
    private ProductDao productDao;

    @RequestMapping(path = "/getAllProduct") //
    public @ResponseBody
    String addNewUser() {
        List<Product> list = productDao.findAll();
        return gs.toJson(list);
    }

    @RequestMapping(path = "/getAllProduct2") //
    public @ResponseBody
    List<Product> addNewUser22() {
        List<Product> list = productDao.findAll();
        return (list);
    }
}
