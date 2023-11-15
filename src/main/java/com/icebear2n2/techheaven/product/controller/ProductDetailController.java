package com.icebear2n2.techheaven.product.controller;

import com.icebear2n2.techheaven.domain.request.ProductDetailIDRequest;
import com.icebear2n2.techheaven.domain.request.ProductDetailRequest;
import com.icebear2n2.techheaven.domain.request.ProductIDRequest;
import com.icebear2n2.techheaven.domain.response.ProductDetailResponse;
import com.icebear2n2.techheaven.product.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/details")
public class ProductDetailController {
    private final ProductDetailService productDetailService;


    @PostMapping
    public ResponseEntity<ProductDetailResponse> createProductDetail(@RequestBody ProductDetailRequest productDetailRequest) {
        ProductDetailResponse productDetailResponse = productDetailService.createProductDetail(productDetailRequest);
        if (productDetailResponse.isSuccess()) {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<Page<ProductDetailResponse.ProductDetailData>> getAllByProductDetails(
            @RequestBody ProductIDRequest productIDRequest,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        PageRequest request = PageRequest.of(page, size);

        return new ResponseEntity<>(productDetailService.findAllByProduct(productIDRequest, request), HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<ProductDetailResponse> updateProductDetail(@RequestBody ProductDetailRequest productDetailRequest) {
        ProductDetailResponse productDetailResponse = productDetailService.updateProductDetail( productDetailRequest);

        if (productDetailResponse.isSuccess()) {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> removeProductDetail(@RequestBody ProductDetailIDRequest productDetailIDRequest) {
        productDetailService.removeProductDetail(productDetailIDRequest);
        return new ResponseEntity<>("Product Detail removed successfully.", HttpStatus.OK);
    }
}