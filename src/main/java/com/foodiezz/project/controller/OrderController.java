package com.foodiezz.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/myorders")
    public String getMyOrders(){
        return "orderPlaced";
    }
}
