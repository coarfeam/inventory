package com.eamapp.inventory.repository;

import com.eamapp.inventory.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryRepository extends CrudRepository<Category, Long> {
}
