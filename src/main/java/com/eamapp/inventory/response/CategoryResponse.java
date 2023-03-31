package com.eamapp.inventory.response;

import com.eamapp.inventory.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<Category> categories;
}
