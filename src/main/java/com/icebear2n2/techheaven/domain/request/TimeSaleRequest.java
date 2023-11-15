package com.icebear2n2.techheaven.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSaleRequest {
    private Long productId;
    private Integer discountPrice;
    private Timestamp startDate;
    private Timestamp endDate;
}
