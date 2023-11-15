package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String ProductName);
    Product findByProductName(String productName);

    Page<Product> findBySaleStartDateBeforeAndSaleEndDateAfter(Timestamp currentTimestamp, Timestamp currentTimestamp1, Pageable pageable);
}
