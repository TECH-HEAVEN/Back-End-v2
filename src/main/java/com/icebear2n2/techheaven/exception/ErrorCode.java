package com.icebear2n2.techheaven.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD REQUEST."),
    USER_IS_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "USER IS UNAUTHORIZED."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER NOT FOUND."),
    USER_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "USER EMAIL NOT FOUND."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "USERNAME NOT FOUND."),
    USER_PHONE_NOT_FOUND(HttpStatus.NOT_FOUND, "USER PHONE NOT FOUND."),
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "USER EMAIL IS DUPLICATED."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "PERMISSION IS INVALID."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR."),
    INVALID_CREDENTIAL(HttpStatus.UNAUTHORIZED, "INVALID CREDENTIAL."),
    FAILED_LOGIN(HttpStatus.BAD_REQUEST, "Your email or password does not match."),
    FAILED_CREATE_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR OCCURRED WHILE CREATING AND SAVING TOKEN."),
    FAILED_SEND_AUTH_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR SENDING AUTH CODE."),
    INVALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "INVALID AUTH CODE."),
    EXPIRED_AUTH_CODE(HttpStatus.NOT_FOUND, "AUTH CODE EXPIRED."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "The password must be at least 8 characters long, including capital letters."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Passwords do not match."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
