package com.foodiezz.project.controller;

import com.foodiezz.project.global.GlobalDataCart;
import com.foodiezz.project.model.Product;
import com.foodiezz.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;



    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id){
        GlobalDataCart.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }
    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount",GlobalDataCart.cart.size());
        model.addAttribute("total",GlobalDataCart.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalDataCart.cart);
        return "cart";
    }
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        GlobalDataCart.cart.remove(index);
        return "redirect:/cart";
    }
    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total",GlobalDataCart.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }
    @PostMapping("/payNow")
    public String payment(Model model){
        model.addAttribute("total",GlobalDataCart.cart.stream().mapToDouble(Product::getPrice).sum());
        return "payment";
    }
    @PostMapping("/paymentmethod")
    public  String paymentPost(@RequestParam("payment") String paymentType){
        if(paymentType.equals("cash")) {
            return "cashPayment";
        }
        else if (paymentType.equals("upi")) {
            return "upiPayment";

        } else if (paymentType.equals("card")) {
            return "cardPayment";
        }
        else {
            return "errorPayment";
        }
    }
@PostMapping("/paymentconfirmation")
public String paymentConfirmation(@RequestParam("cardNumber")String Cardno, @RequestParam("expirationDate") String ExpirationDate, @RequestParam("cvv")String cvv, @RequestParam("cardholderName")String CardholderName, Model model){
    //System.out.println("buttonClicked = "+buttonClicked);
        if(Cardno.length()==16 && ExpirationDate.length()==5 && cvv.length()==3 && CardholderName.length()>0){
        // Payment confirmation logic
        model.addAttribute("message", "Payment successful!");


        return "confirmationPayment";
    } else {
        model.addAttribute("message", "Invalid payment information. Please try again.");
        return "confirmationPayment";
    }
}
}
