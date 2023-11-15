package com.icebear2n2.techheaven.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequest {
    private Long userId;
    private String phone;
}
