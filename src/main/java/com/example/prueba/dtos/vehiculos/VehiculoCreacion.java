package com.example.prueba.dtos.vehiculos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * DTO que ayuda a la respuesta despues de crear un nuevo vehiculo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoCreacion {
   
    private UUID id;

    private String marca;

    private String modelo;
}
