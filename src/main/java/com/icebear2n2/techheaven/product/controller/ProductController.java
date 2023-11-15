package com.icebear2n2.techheaven.product.controller;

import com.icebear2n2.techheaven.domain.request.ProductIDRequest;
import com.icebear2n2.techheaven.domain.request.ProductRequest;
import com.icebear2n2.techheaven.domain.response.ProductResponse;
import com.icebear2n2.techheaven.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        if (productResponse.isSuccess()) {
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponse.ProductData>> getAllProducts(
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {

        PageRequest request = PageRequest.of(page, size);

        return new ResponseEntity<>(productService.findAll(request), HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct( @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(productRequest);
        if (productResponse.isSuccess()) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> removeProduct(@RequestBody ProductIDRequest productIDRequest) {
        productService.removeProduct(productIDRequest.getProductId());
        return new ResponseEntity<>("Product removed successfully.", HttpStatus.OK);
    }


    @DeleteMapping("/delete/all")
    public ResponseEntity<String> removeProductAndDetails(@RequestBody ProductIDRequest productIDRequest) {
        productService.removeProductAndDetails(productIDRequest.getProductId());
        return new ResponseEntity<>("Product and product Details removed successfully.", HttpStatus.OK);
    }
}