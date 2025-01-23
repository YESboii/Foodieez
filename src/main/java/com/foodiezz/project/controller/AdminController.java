package com.foodiezz.project.controller;

import com.foodiezz.project.dtto.ProductDTO;
import com.foodiezz.project.model.Category;
import com.foodiezz.project.model.Product;
import com.foodiezz.project.service.CategoryService;
import com.foodiezz.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

// the controller method starts to process the web request by interacting with the service layer to complete the work that needs to be done.
@Controller
public class AdminController {

    public String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

//===================================================Category Section=======================================================================

    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String addCategories(Model model){
        model.addAttribute("category",new Category());
        return "categoriesAdd";
    }
    @PostMapping("/admin/categories/add")
    public String postCategories(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }
    @GetMapping("/admin/categories/delete/{id}")
        public String deleteCategories(@PathVariable int id){
            categoryService.removeCategoryById(id);
            return "redirect:/admin/categories";
        }
    @GetMapping("/admin/categories/update/{id}")
    public String updateCategories(@PathVariable int id, Model model){
        Optional <Category> category = categoryService.updateCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category" , category.get());
            return "categoriesAdd";
        }
        else{
            return "404";
        }
    }


//=============================================================Product Section==============================================================

    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String addProducts(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories" , categoryService.getAllCategory());
        return "productsAdd";
    }

    //MultipartFile is used for handling image in the form. It is used by admin.
    @PostMapping("admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException{

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.updateCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

        String uuidImg;
        if(!file.isEmpty()){
            uuidImg = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, uuidImg);
            Files.write(fileNameAndPath,file.getBytes());
        }
        else{
            uuidImg = imgName;
        }
        product.setImageName(uuidImg);
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id,Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId((product.getCategory().getId()));
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());
        model.addAttribute("categories" , categoryService.getAllCategory());
        model.addAttribute("productDTO",productDTO);
        return "productsAdd";
    }
}



