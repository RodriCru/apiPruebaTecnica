package com.example.prueba.dtos;

import lombok.*;
import java.util.List;

import com.example.prueba.dtos.vehiculos.*;

/**
 * Este DTO es el que le da el formato al JSON se usa 
 * para listar los vehiculos, paginación y mostrar cuantos resultados totales son.
 */
@Getter
@Setter
@AllArgsConstructor
public class ListaVehiculosDTO {
    private int total;
    private int page;
    private int limit;
    private List<VehiculoDTO> vehiculos;
}
