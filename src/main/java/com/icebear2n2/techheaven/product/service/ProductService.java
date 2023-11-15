package com.icebear2n2.techheaven.product.service;

import com.icebear2n2.techheaven.domain.entity.Product;
import com.icebear2n2.techheaven.domain.entity.ProductDetail;
import com.icebear2n2.techheaven.domain.repository.CategoryRepository;
import com.icebear2n2.techheaven.domain.repository.ProductDetailRepository;
import com.icebear2n2.techheaven.domain.repository.ProductRepository;
import com.icebear2n2.techheaven.domain.request.ProductRequest;
import com.icebear2n2.techheaven.domain.response.ProductResponse;
import com.icebear2n2.techheaven.exception.ErrorCode;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CategoryRepository categoryRepository;


    public ProductResponse createProduct(ProductRequest productRequest) {
        if (!categoryRepository.existsByCategoryName(productRequest.getCategoryName())) {
            return ProductResponse.failure(ErrorCode.CATEGORY_NOT_FOUND.toString());
        }

        if (productRepository.existsByProductName(productRequest.getProductName())) {
            return ProductResponse.failure(ErrorCode.DUPLICATED_PRODUCT_NAME.toString());
        }

        try {
            Product product = productRequest.toEntity();
            product.setCategory(categoryRepository.findByCategoryName(productRequest.getCategoryName()));
            Product savedProduct = productRepository.save(product);
            return ProductResponse.success(savedProduct);
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return ProductResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }


    public Page<ProductResponse.ProductData> findAll(PageRequest pageRequest) {
        Page<Product> all = productRepository.findAll(pageRequest);
        return all.map(ProductResponse.ProductData::new);
    }

    //   TODO: UPDATE
    public ProductResponse updateProduct(ProductRequest productRequest) {

        if (!productRepository.existsById(productRequest.getProductId())) {
            return ProductResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
        }

        if (!categoryRepository.existsByCategoryName(productRequest.getCategoryName())) {
            return ProductResponse.failure(ErrorCode.CATEGORY_NOT_FOUND.toString());
        }

        if (productRepository.existsByProductName(productRequest.getProductName())) {
            return ProductResponse.failure(ErrorCode.DUPLICATED_PRODUCT_NAME.toString());
        }

        try {
            Product existingProduct = productRepository.findById(productRequest.getProductId())
                    .orElseThrow(() -> new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND));
            productRequest.updateProductIfNotNull(existingProduct, categoryRepository);
            productRepository.save(existingProduct);
            return ProductResponse.success(existingProduct);
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return ProductResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public void removeProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        try {
            productRepository.deleteById(productId);
        } catch (DataIntegrityViolationException e) {
            throw new TechHeavenException(ErrorCode.PRODUCT_HAS_RELATED_PRODUCT_DETAIL);
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            throw new TechHeavenException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void removeProductAndDetails(Long productId) {
        // Step1: 상품에 연결된 상세 정보 찾기
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND));

        // Step2: 상품 관련 상세 정보 전체 삭제
        List<ProductDetail> productDetails = product.getProductDetails();
        productDetailRepository.deleteAll(productDetails);

        // Step3: 상품 삭제
        productRepository.delete(product);
    }
}