package com.foodiezz.project.repository;

import com.foodiezz.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

//provides all the basic methods to access the database
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
