package com.eamapp.inventory.service;

import com.eamapp.inventory.model.Product;
import com.eamapp.inventory.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    public ResponseEntity<ProductResponseRest> search();
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> searchById(Long id);
    public ResponseEntity<ProductResponseRest> searchByName(String name);
}
