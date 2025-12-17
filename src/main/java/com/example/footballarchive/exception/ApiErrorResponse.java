package com.example.footballarchive.exception;
import java.util.List;
import java.util.*;
public class ApiErrorResponse {
    private String errorCode;
    private String message;
    private List<String> details;

    public ApiErrorResponse(String errorCode, String message, List<String> details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }
}
