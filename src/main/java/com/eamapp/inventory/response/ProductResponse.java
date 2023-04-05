package com.eamapp.inventory.response;

import com.eamapp.inventory.model.Product;
import lombok.Data;

import java.util.List;
@Data
public class ProductResponse {
    private List<Product> products;
}
