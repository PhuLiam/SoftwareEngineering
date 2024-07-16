package admin_user.service;

import admin_user.model.ProductCatalog;
import admin_user.repositories.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public interface ProductService {

	 
    List<ProductCatalog> findAll();
    ProductCatalog findById(Long id);
    ProductCatalog save(ProductCatalog product);
    void deleteById(Long id);
    

}