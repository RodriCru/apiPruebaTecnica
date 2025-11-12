package com.example.prueba.dtos.vehiculos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * DTO que ayuda a mostrar los datos de un auto ya sea por bisqieda o lista.
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
