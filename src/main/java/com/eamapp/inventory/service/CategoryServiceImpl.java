package com.eamapp.inventory.service;

import com.eamapp.inventory.model.Category;
import com.eamapp.inventory.repository.ICategoryRepository;
import com.eamapp.inventory.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<Category> categories = (List<Category>) categoryRepository.findAll();
            response.getCategoryResponse().setCategories(categories);
            response.setMetadata("Ok","00","Respuesta Exitosa");
        }catch (Exception e){
            response.setMetadata("Failed","-1","Error al consultar");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()){
                list.add(category.get());
                response.getCategoryResponse().setCategories(list);
                response.setMetadata("Ok","00","Respuesta Exitosa");
            }else {
                response.setMetadata("Failed","-1","Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Failed","-1","Error al consultar por Id");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<CategoryResponseRest> save(Category category) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Category categorySaved = categoryRepository.save(category);
            if (categorySaved != null){
                list.add(categorySaved);
                response.getCategoryResponse().setCategories(list);
                response.setMetadata("Ok","00","Respuesta Exitosa");
            }
            else {
                response.setMetadata("Failed","-1","Categoria no guardada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            response.setMetadata("Failed","-1","Error al grabar categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
