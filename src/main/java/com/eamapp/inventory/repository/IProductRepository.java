package com.eamapp.inventory.repository;

import com.eamapp.inventory.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductRepository extends CrudRepository<Product, Long> {
}
