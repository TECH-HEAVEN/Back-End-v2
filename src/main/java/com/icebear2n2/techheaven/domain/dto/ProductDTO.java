package com.icebear2n2.techheaven.domain.dto;

import com.icebear2n2.techheaven.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private Long categoryId;
    private String productName;
    private Integer productPrice;
    private Integer discountPrice;
    private Timestamp saleStartDate;
    private Timestamp saleEndDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<ProductDetailDTO> productDetails;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.categoryId = product.getCategory().getCategoryId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.discountPrice = product.getDiscountPrice();
        this.saleStartDate = product.getSaleStartDate();
        this.saleEndDate = product.getSaleEndDate();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.productDetails = product.getProductDetails() != null ? product.getProductDetails().stream().map(ProductDetailDTO::new).toList() : null;
    }
}