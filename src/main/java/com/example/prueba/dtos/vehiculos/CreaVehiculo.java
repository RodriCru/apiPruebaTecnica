package com.example.prueba.dtos.vehiculos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * DTO que ayuda para crear un nuevo vehiculo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreaVehiculo {

    private String marca;

    private String modelo;

    private String estatus;

    private Boolean vendido;

    private Boolean eliminado;
}
