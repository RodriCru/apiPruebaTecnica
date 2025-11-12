package com.example.prueba.dtos.vehiculos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoEliminadoDTO {
    private String marca;

    private String modelo;

    private Boolean eliminado;
}
