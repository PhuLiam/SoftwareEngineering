package admin_user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import admin_user.model.ProductCatalog;

@Repository
public interface ProductRepository extends JpaRepository<ProductCatalog, Long> {
	
//    
}
