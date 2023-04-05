package com.eamapp.inventory.service;

import com.eamapp.inventory.model.Category;
import com.eamapp.inventory.model.Product;
import com.eamapp.inventory.repository.ICategoryRepository;
import com.eamapp.inventory.repository.IProductRepository;
import com.eamapp.inventory.response.ProductResponseRest;
import com.eamapp.inventory.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{
    private ICategoryRepository categoryRepository;
    private IProductRepository productRepository;

    public ProductServiceImpl(ICategoryRepository categoryRepository, IProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> search() {
        ProductResponseRest response = new ProductResponseRest();
        try {
            List<Product> products = (List<Product>) productRepository.findAll();
            response.getProductResponse().setProducts(products);
            response.setMetadata("Ok","00","Respuesta Exitosa");
        }catch (Exception e){
            response.setMetadata("Failed","-1","Error al consultar");
            e.printStackTrace();
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        try{
            //search category to set in the product object
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (category.isPresent()){
                product.setCategory(category.get());
            }else {
                response.setMetadata("respuesta fallida","-1","Categoria no asociada al producto");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
            // save the product
            Product productSaved = productRepository.save(product);
            if(productSaved != null){
                list.add(productSaved);
                response.getProductResponse().setProducts(list);
                response.setMetadata("respuesta ok","00","producto guardado");
            }else{
                response.setMetadata("respuesta fallida","-1","producto no guardado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.getStackTrace();
            response.setMetadata("respuesta fallida","-1","Errorl al guardar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        try{
            //search product by id
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()){
                byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDescompressed);
                list.add(product.get());
                response.getProductResponse().setProducts(list);
                response.setMetadata("Respuesta ok","00","producto encontrado");

            }else {
                response.setMetadata("respuesta fallida","-1","producto no encontrado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            e.getStackTrace();
            response.setMetadata("respuesta fallida","-1","Errorl al buscar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }
}