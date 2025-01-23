package com.foodiezz.project.controller;

import com.foodiezz.project.global.GlobalDataCart;
import com.foodiezz.project.service.CategoryService;
import com.foodiezz.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping({"/","/home"})
    public String toHome(Model model){
        model.addAttribute("cartCount", GlobalDataCart.cart.size());
        return "index";
    }
    @GetMapping("/shop")
    public String toShop(Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("cartCount",GlobalDataCart.cart.size());
        return "shop";
    }
    @GetMapping("/shop/category/{id}")
    public String toShopByCategory(Model model, @PathVariable int id){
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("products",productService.getAllProductsByCategoryId(id));
        return "shop";
    }
    @GetMapping("/shop/viewproduct/{id}")
    public String toViewProduct(Model model, @PathVariable int id){
        model.addAttribute("product",productService.getProductById(id).get());
        return "viewProduct";
    }

}
