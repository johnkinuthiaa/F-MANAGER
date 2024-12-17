package com.slippery.fmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//    created this to view my data-----
    @GetMapping("/transactions")
    public String transactions(){
        return "transactions.html";
    }
    @GetMapping("/swagger/docs")
    public String swagger(){
        return "/swagger-ui/index.html";
    }
}
