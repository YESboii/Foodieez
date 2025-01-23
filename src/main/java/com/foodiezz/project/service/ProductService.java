package com.foodiezz.project.service;

import com.foodiezz.project.model.Product;
import java.util.List;
import java.util.Optional;

import com.foodiezz.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public List <Product> getAllProducts(){
        return productRepository.findAll();
    }
    public void addProduct(Product product){
        productRepository.save(product);
    }
    public void removeProductById(long id){
        productRepository.deleteById(id);
    }
    public Optional <Product> getProductById(long id){
        return productRepository.findById(id);
    }

    //function that is used by user for filtering specific products......
    public List<Product> getAllProductsByCategoryId(int id){
        return productRepository.findAllByCategory_Id(id);
    }
}
