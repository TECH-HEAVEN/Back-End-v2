package com.icebear2n2.techheaven.domain.dto;
import com.icebear2n2.techheaven.domain.entity.ProductDetail;
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
public class ProductDetailDTO {
    private Long productDetailId;
    private Long productId;
    private List<String> productColors;
    private List<String> productSizes;
    private List<Integer> stockQuantity;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ProductDetailDTO(ProductDetail productDetail) {
        this.productDetailId = productDetail.getProductDetailId();
        this.productId = productDetail.getProduct().getProductId();
        this.productColors = productDetail.getProductColors();
        this.productSizes = productDetail.getProductSizes();
        this.stockQuantity = productDetail.getStockQuantity();
        this.createdAt = productDetail.getCreatedAt();
        this.updatedAt = productDetail.getUpdatedAt();
    }
}