package com.mindpalace.MP_Backend.model.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponseDTO {
    private Long id;

    @Builder
    public LoginResponseDTO(Long id) {
        this.id = id;
    }
}
