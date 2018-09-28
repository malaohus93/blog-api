package com.blog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceController {

    @GetMapping("/product")
    public String product(){
        return "this is an product";
    }

    @GetMapping("/order")
    public String order(){
        return "this is an order";
    }

}
