package com.ericho.coupleShare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController()
public class ApiController {

    public static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping(value = "/api/aaa",produces = "application/json")
    @ResponseBody
    public Book getAAA(){
        logger.debug("getAAA was call 1 time");
        return new Book();
    }

    @RequestMapping(value = "/api/a",produces = "application/json",method = RequestMethod.GET)

    public  @ResponseBody String getA(){
        logger.debug("getA was call 1 time @#$");
        return "aaaaaaaaaaa";
    }
}
