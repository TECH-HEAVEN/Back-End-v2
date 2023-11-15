package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);
    Category findByCategoryName(String categoryName);

    Page<Category> findAllByCategoryNameContaining(String categoryName, Pageable pageable);
    List<Category> findByCategoryNameContaining(String categoryName);

}
