package com.example.novel_app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ACCOUNT_EXISTED(998,"Account existed"),
    ACCOUNT_NOT_EXISTED(999,"Account not existed"),
    TEST_ERROR(1000, "User existed"),
    ERROR_LENGTH(1002, "Length not suitable"),
    ACCOUNT_NOT_EXIST(1003,"Username or password not correct"),
    NOVEL_NOT_EXISTED(1004,"Novel not existed"),
    NOVEL_EXISTED(1005,"Novel existed"),
    GENRE_NOT_EXISTED(1006,"Genre not existed"),
    GENRE_EXISTED(1007,"Genre not existed"),
    AUTHOR_NOT_EXISTED(1008,"Author not existed"),
    PASSWORD_LEAST_8_CHARACTERS(1009,"Password least 8 characters"),
    PASSWORD_NOT_CORRECT(1010,"Password not correct"),
    AUTHOR_EXISTED(1011,"Author existed"),
    OTP_INVALID(1012,"Otp is invalid" ),
    SCOPE_NOT_FOUND(1013,"Scope not found"),
    COMMENT_NOT_FOUND(1014,"Comment not found"),




    TOKEN_INVALID(1100,"Token is invalid" ),;

    private final int code;
    private final String message;

}
