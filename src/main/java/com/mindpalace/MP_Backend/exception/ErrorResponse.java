package com.mindpalace.MP_Backend.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;
}
