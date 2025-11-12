package com.example.prueba.dtos;

import lombok.*;
import java.util.List;

import com.example.prueba.dtos.clientes.ClienteDTO;

/**
 * Este DTO es el que le da el formato al JSON se usa 
 * para listar los empelados,clientes, paginación y mostrar cuantos resultados totales son.
 */
@Getter
@Setter
@AllArgsConstructor
public class ListaDTO {
    private int total;
    private int page;
    private int limit;
    private List<ClienteDTO> clientes;
}
