package admin_user.service;

import admin_user.model.ProductCatalog;
import admin_user.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductCatalog> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductCatalog findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductCatalog save(ProductCatalog product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    
    

    
}