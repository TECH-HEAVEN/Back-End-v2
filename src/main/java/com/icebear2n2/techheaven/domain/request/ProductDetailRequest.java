package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Product;
import com.icebear2n2.techheaven.domain.entity.ProductDetail;
import com.icebear2n2.techheaven.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    private Long productDetailId;
    private String productName;
    private List<String> productColors;
    private List<String> productSizes;
    private List<Integer> stockQuantity;

    public ProductDetail toEntity() {
        return ProductDetail.builder()
                .productColors(productColors)
                .productSizes(productSizes)
                .stockQuantity(stockQuantity)
                .build();
    }

    public void updateProductDetailIfNotNull(ProductDetail productDetail, ProductRepository productRepository) {
        if (this.productName != null) {
            Product product = productRepository.findByProductName(this.productName);
            if (product != null) {
                productDetail.setProduct(product);
            }
        }

        updateList(productDetail.getProductColors(), this.productColors);
        updateList(productDetail.getProductSizes(), this.productSizes);
        updateList(productDetail.getStockQuantity(), this.stockQuantity);
    }

    private <T> void updateList(List<T> originalList, List<T> newList) {
        if (newList != null) {

            // 기존 리스트 값 업데이트
            for (int i = 0; i < Math.min(originalList.size(), newList.size()); i++) {
                if (newList.get(i) != null) {
                    originalList.set(i, newList.get(i));
                }
            }

            // TODO: 해당 기능 삭제하는 것에 대해 고려
            // 기존 리스트에서 요청으로부터 받은 리스트 크기가 기존 리스트보다 작으면 기존 리스트 뒷 부분 삭제
            while (originalList.size() > newList.size()) {
                originalList.remove(originalList.size() - 1);
            }

            // 기존 리스트에서 요청으로부터 받은 리스트 크기가 기존 리스트보다 크다면, 추가된 부분을 새로운 값으로 추가
            for (int i = originalList.size(); i < newList.size(); i++) {
                originalList.add(newList.get(i));
            }
        }
    }

}
