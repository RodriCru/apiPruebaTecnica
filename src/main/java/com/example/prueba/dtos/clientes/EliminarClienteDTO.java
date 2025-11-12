package com.example.prueba.dtos.clientes;

import lombok.*;

import java.util.UUID;

/**
 * DTO que ayuda a eliminar un cliente sin necesitar mas parámetros.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EliminarClienteDTO {
    /**
     * Identificador único del usuario 
     */

    private UUID id;

    private Boolean eliminado;
}
