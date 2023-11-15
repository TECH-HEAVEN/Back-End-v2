package com.icebear2n2.techheaven.timeSale.service;

import com.icebear2n2.techheaven.domain.entity.Product;
import com.icebear2n2.techheaven.domain.repository.ProductRepository;
import com.icebear2n2.techheaven.domain.request.TimeSaleRequest;
import com.icebear2n2.techheaven.domain.response.ProductResponse;
import com.icebear2n2.techheaven.exception.ErrorCode;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class TimeSaleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeSaleService.class);

    private static final String TIME_SALE_REDIS_KEY = "timeSale:products";
    private final AtomicBoolean isSaleStarted = new AtomicBoolean(false);

    private final StringRedisTemplate stringRedisTemplate;
    private final ProductRepository productRepository;

    public Page<ProductResponse.ProductData> getProductStaredTimeSale(PageRequest pageRequest) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Page<Product> saleEndDateAfter = productRepository.findBySaleStartDateBeforeAndSaleEndDateAfter(currentTimestamp, currentTimestamp, pageRequest);
        return saleEndDateAfter.map(ProductResponse.ProductData::new);

    }

    public ProductResponse startProductTimeSale(TimeSaleRequest timeSaleRequest) {
        Product product = productRepository.findById(timeSaleRequest.getProductId()).orElseThrow(() -> new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND));


        try {
            product.setDiscountPrice(timeSaleRequest.getDiscountPrice());
            product.setSaleStartDate(timeSaleRequest.getStartDate());
            product.setSaleEndDate(timeSaleRequest.getEndDate());
            Product updateProduct = productRepository.save(product);

            // 세일 종료 시간 Redis Sorted Set 에 저장
            stringRedisTemplate.opsForZSet().add(TIME_SALE_REDIS_KEY, product.getProductId().toString(), product.getSaleEndDate().getTime());
            isSaleStarted.set(true);
            return ProductResponse.success(updateProduct);
        } catch (Exception e) {
            return ProductResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public void endProductTimeSale(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setDiscountPrice(null);
        product.setSaleStartDate(null);
        product.setSaleEndDate(null);

        productRepository.save(product);
        LOGGER.info("END TIME SALE: {}", System.currentTimeMillis());
    }


    /**
     * 세일 기간이 만료된 상품 세일을 종료하기 위한 스케줄링 작업
     */

// TODO: 수정 작업 필요: 세일 작업 들어가고 스케줄링 작업 시작되게 수정
    @Scheduled(fixedRate = 60000)   // 매 1분마다 작업 실행
    public void checkAndEndExpiredProductSales() {
        if (!isSaleStarted.get()) {
            return; // Exit if the sale hasn't started
        }
        isSaleStarted.set(false);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        // Step 1: 세일 종료 시간이 현재 시간으로부터 이전인 상품 ID 를 Redis 에서 조회
        Set<String> expiredProductIds = stringRedisTemplate.opsForZSet()
                .rangeByScore(TIME_SALE_REDIS_KEY, 0, currentTimestamp.getTime());

        if (expiredProductIds != null && !expiredProductIds.isEmpty()) {
            for (String productIdStr : expiredProductIds
                 ) {
                long productId = Long.parseLong(productIdStr);
                endProductTimeSale(productId);


                // Step 2: 세일이 종료되면 Redis 에서 상품 ID 삭제
                stringRedisTemplate.opsForZSet().remove(TIME_SALE_REDIS_KEY, productIdStr);
            }
        }
    }

}
