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
public class DatosVehiculo {

    private String marca;

    private String modelo;

    private String estatus;

    private Boolean vendido;

    private Boolean eliminado;
}
