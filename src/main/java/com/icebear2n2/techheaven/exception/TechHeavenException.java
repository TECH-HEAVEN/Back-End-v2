package com.icebear2n2.techheaven.exception;

import lombok.Getter;

@Getter
public class TechHeavenException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public TechHeavenException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
