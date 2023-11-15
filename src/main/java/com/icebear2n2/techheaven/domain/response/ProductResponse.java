package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.dto.ProductDetailDTO;
import com.icebear2n2.techheaven.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private boolean success;
    private String message;
    private ProductData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductData {
        private Long productId;
        private String categoryName;
        private String productName;

        private Integer productPrice;
        private Integer discountPrice;
        private Timestamp saleStartDate;
        private Timestamp saleEndDate;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private List<ProductDetailDTO> productDetails;

        public ProductData(Product product) {
            this.productId = product.getProductId();
            this.categoryName = product.getCategory().getCategoryName();
            this.productName = product.getProductName();
            this.productPrice = product.getProductPrice();
            this.discountPrice = product.getDiscountPrice();
            this.saleStartDate = product.getSaleStartDate() != null ? product.getSaleStartDate() : null;
            this.saleEndDate = product.getSaleEndDate() != null ? product.getSaleEndDate() : null;
            this.createdAt = product.getCreatedAt();
            this.updatedAt = product.getUpdatedAt();
            this.productDetails = product.getProductDetails() != null ? product.getProductDetails().stream().map(ProductDetailDTO::new).toList() : null;
        }
    }

    public static ProductResponse success(Product product) {
        return new ProductResponse(true, "Success", new ProductData(product));
    }

    public static ProductResponse failure(String message) {
        return new ProductResponse(false, message, null);
    }
}