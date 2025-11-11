package com.example.prueba.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenDTO {
    private String refreshToken;
}
