package com.mindpalace.MP_Backend.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;
    private final LinkedHashMap<String, String> validation = new LinkedHashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
