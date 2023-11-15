package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.Product;
import com.icebear2n2.techheaven.domain.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Page<ProductDetail> findAllByProduct(Product product, Pageable pageable);

}
