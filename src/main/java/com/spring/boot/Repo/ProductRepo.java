package com.spring.boot.Repo;

import com.spring.boot.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryIdOrderByIdAsc(Pageable pageable , Long categoryId);  // ✅ صح
    Page<Product> findAllByOrderByIdAsc(Pageable pageable);

    Page<Product> findAllByCategoryIdAndNameContainingIgnoreCaseOrderByIdAsc(Long categoryId, String key, Pageable pageable);

    List<Product> findByIdIn(List<Long> ids);




}
