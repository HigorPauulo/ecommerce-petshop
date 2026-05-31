package br.com.importaai.ecommercepetshop.repository;

import br.com.importaai.ecommercepetshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    boolean existsByCategoryId(Long categoryId);
}
