package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    private boolean success;
    private String message;
    private ProductDetailData productDetailData;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetailData {
        private Long productId;
        private List<String> productColors;
        private List<String> productSizes;
        private List<Integer> stockQuantity;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public ProductDetailData(ProductDetail productDetail) {
            this.productId = productDetail.getProduct().getProductId();
            this.productColors = productDetail.getProductColors() != null ? productDetail.getProductColors() : null;
            this.productSizes = productDetail.getProductSizes() != null ? productDetail.getProductSizes() : null;
            this.stockQuantity = productDetail.getStockQuantity();
            this.createdAt = productDetail.getCreatedAt();
            this.updatedAt = productDetail.getUpdatedAt();
        }
    }

    public static ProductDetailResponse success(ProductDetail productDetail) {
        return new ProductDetailResponse(true, "Success", new ProductDetailData(productDetail));
    }
    public static ProductDetailResponse failure(String message) {
        return new ProductDetailResponse(false, message, null);
    }
}
