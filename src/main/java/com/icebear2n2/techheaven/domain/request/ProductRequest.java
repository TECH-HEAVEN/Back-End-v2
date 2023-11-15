package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Category;
import com.icebear2n2.techheaven.domain.entity.Product;
import com.icebear2n2.techheaven.domain.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long productId;
    private String categoryName;
    private String productName;
    private Integer productPrice;


    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .productPrice(productPrice)
                .build();
    }

    public void updateProductIfNotNull(Product product, CategoryRepository categoryRepository) {
        if (this.categoryName != null) {
            Category category = categoryRepository.findByCategoryName(this.categoryName);
            if (category != null) {
                product.setCategory(category);
            }
        }
        if (this.productName != null) {
            product.setProductName(this.productName);
        }
        if (this.productPrice != null) {
            product.setProductPrice(this.productPrice);
        }
    }
}

