package com.mindpalace.MP_Backend.model.dto;

import lombok.Data;

@Data
public class EmailCheckDTO {
    private boolean duplicated; //중복이면 true 아니면 false
    private String message;
}
