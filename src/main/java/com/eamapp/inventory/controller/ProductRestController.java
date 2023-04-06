package com.eamapp.inventory.controller;

import com.eamapp.inventory.model.Product;
import com.eamapp.inventory.response.ProductResponseRest;
import com.eamapp.inventory.response.ResponseRest;
import com.eamapp.inventory.service.IProductService;
import com.eamapp.inventory.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/")
public class ProductRestController {

    private IProductService productService;

    public ProductRestController(IProductService productService) {
        this.productService = productService;
    }

    /*
    * @Param picture, name, price, quantity, categoryId
    * @returns
    * throws IOException
    * */
    @PostMapping("products")
    public ResponseEntity<ProductResponseRest> save(
            @RequestParam("picture")MultipartFile picture,
            @RequestParam("name")String name,
            @RequestParam("price")double price,
            @RequestParam("quantity")int quantity,
            @RequestParam("categoryId") Long categoryId
            )throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));
        ResponseEntity<ProductResponseRest> response = productService.save(product, categoryId);
        return response;
    }

    //get All Categories;
    @GetMapping("products")
    public ResponseEntity<ProductResponseRest> searchProducts(){
        ResponseEntity<ProductResponseRest> response = productService.search();
        return response;
    }

    /*
    * search by id
    * @param id
    * @return
    * */
    @GetMapping("products/{id}")
    public ResponseEntity<ProductResponseRest> searchById(@PathVariable Long id){
        ResponseEntity<ProductResponseRest> response = productService.searchById(id);
        return response;
    }

    /*
     * search by name
     * @param name
     * @return
     * */
    @GetMapping("products/filter/{name}")
    public ResponseEntity<ProductResponseRest> searchByName(@PathVariable String name){
        ResponseEntity<ProductResponseRest> response = productService.searchByName(name);
        return response;
    }

    /*
     * delete by id
     * @param id
     * @return
     * */
    @DeleteMapping("products/{id}")
    public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long id){
        ResponseEntity<ProductResponseRest> response = productService.deleteById(id);
        return response;
    }

    /*
     * update product
     * @Param picture, name, price, quantity, categoryId, id
     * @returns
     * throws IOException
     * */
    @PutMapping("products/{id}")
    public ResponseEntity<ProductResponseRest> update(
            @RequestParam("picture")MultipartFile picture,
            @RequestParam("name")String name,
            @RequestParam("price")double price,
            @RequestParam("quantity")int quantity,
            @RequestParam("categoryId") Long categoryId,
            @PathVariable Long id
    )throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));
        ResponseEntity<ProductResponseRest> response = productService.update(product, categoryId, id);
        return response;
    }
}
