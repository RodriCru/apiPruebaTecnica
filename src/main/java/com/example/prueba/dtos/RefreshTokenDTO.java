package com.example.prueba.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * DTO que se encarga cuando se quiere gtenerar un uevo access token.
 */
@Data
@Builder
public class RefreshTokenDTO {
    private String refreshToken;
}
