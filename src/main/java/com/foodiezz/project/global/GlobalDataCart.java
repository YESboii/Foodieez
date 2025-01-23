package com.foodiezz.project.global;

import com.foodiezz.project.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalDataCart {
    public static List <Product> cart;
    static{
        cart = new ArrayList<Product>();
    }
}
