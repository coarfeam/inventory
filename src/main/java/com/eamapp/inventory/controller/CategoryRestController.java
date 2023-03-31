package com.eamapp.inventory.controller;

import com.eamapp.inventory.model.Category;
import com.eamapp.inventory.response.CategoryResponseRest;
import com.eamapp.inventory.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/")
public class CategoryRestController {
    @Autowired
    private ICategoryService service;
    //get all categories
    @GetMapping("categories")
    public ResponseEntity<CategoryResponseRest> searchCategories(){
        ResponseEntity<CategoryResponseRest> response = service.search();
        return response;
    }

    //get all category by id
    //@param id
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id){
        ResponseEntity<CategoryResponseRest> response = service.searchById(id);
        return response;
    }

    //save category
    //@param category
    @PostMapping("categories")
    public ResponseEntity<CategoryResponseRest> saveCategory(@RequestBody Category category){
        ResponseEntity<CategoryResponseRest> response = service.save(category);
        return response;
    }
}
