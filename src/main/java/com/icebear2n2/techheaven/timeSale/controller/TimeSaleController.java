package com.icebear2n2.techheaven.timeSale.controller;

import com.icebear2n2.techheaven.domain.request.TimeSaleRequest;
import com.icebear2n2.techheaven.domain.response.ProductResponse;
import com.icebear2n2.techheaven.timeSale.service.TimeSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale/time")
public class TimeSaleController {
    private final TimeSaleService timeSaleService;

    @PutMapping
    public ResponseEntity<ProductResponse> startProductTimeSale(@RequestBody TimeSaleRequest timeSaleRequest) {
        ProductResponse productResponse = timeSaleService.startProductTimeSale(timeSaleRequest);
        if (productResponse.isSuccess()) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse.ProductData>> getProductStaredTimeSale(
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page
    ) {
        PageRequest request = PageRequest.of(page, size);
        return new ResponseEntity<>(timeSaleService.getProductStaredTimeSale(request), HttpStatus.OK);
    }
}
