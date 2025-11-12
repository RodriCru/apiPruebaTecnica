package com.example.prueba.dtos.empleados;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO que ayuda a la creación, y actualización de un empleado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreaEmpleadoDTO {
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private LocalDate nacimiento;
    private String rfc;
    private String curp;
    private String telefono;
    private String correo;
    private String usuario;
    private String contrasena;
    private Boolean eliminado;
    private String cp;
    private String estado;
    private String ciudad;
    private String colonia;
    private String calle;
    private String ext;
    private String interior;
}
