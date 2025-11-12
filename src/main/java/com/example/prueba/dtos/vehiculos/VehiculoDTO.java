package com.example.prueba.dtos.vehiculos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * DTO para almacenar los datos en la BD.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoDTO {

    private UUID id;

    private String marca;

    private String modelo;

    private String estatus;

    private Boolean vendido;

    private Boolean eliminado;
}
