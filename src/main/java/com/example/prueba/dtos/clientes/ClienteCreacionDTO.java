package com.example.prueba.dtos.clientes;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreacionDTO {
    private UUID id;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String correo;
}
