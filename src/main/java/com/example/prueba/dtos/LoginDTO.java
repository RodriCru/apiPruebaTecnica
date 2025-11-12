package com.example.prueba.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que se encarga del login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String usuario;
    private String contrasena;
}
