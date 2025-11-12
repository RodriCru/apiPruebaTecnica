package com.example.prueba.dtos.empleados;

import java.util.UUID;
import lombok.*;

/**
 * Este DTO ayuda a la respuesta después de crear un nuevo empleado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoCreacionDTO {
    private UUID id;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String correo;
    private String usuario;
    
}
