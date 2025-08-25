package com.spring.boot.Repo;

import com.spring.boot.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String categoryName);

    List<Category> findByNameIn(Collection<String> names);
    List<Category> findByIdIn(Collection<Long> ids);
    Page<Category> findAllByOrderByNameAsc(Pageable pageable);
}
