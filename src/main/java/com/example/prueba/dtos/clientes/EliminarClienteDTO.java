package com.example.prueba.dtos.clientes;

import lombok.*;

import java.util.UUID;

/**
 * Esta clase representa el DTO de la entidad usuario
 * Se utilza para borrar un usuario o restaurar un usario borrado, 
 * Este no elimina el usuario de la base de datos, solo marca el campo
 * deleted como verdadero.
 * 
 * @author Luis Rodrigo Cruz Hernández
 * @version 1.0
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
