package com.vti.configuration.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private Integer code;
    private String message;
    private String detailMessage;
    private Object errors;
    private String moreInfomation;

    public ErrorResponse(Integer code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
        moreInfomation = "http://localhost:8080/api/v1/exceptions/" + code;
    }

    public ErrorResponse(Integer code, String message, String detailMessage, Object errors) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
        this.errors = errors;
        moreInfomation = "http://localhost:8080/api/v1/exceptions/" + code;
    }
}
