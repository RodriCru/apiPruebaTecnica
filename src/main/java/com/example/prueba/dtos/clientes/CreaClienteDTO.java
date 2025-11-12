package com.example.prueba.dtos.clientes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreaClienteDTO {
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private LocalDate nacimiento;
    private String rfc;
    private String curp;
    private String telefono;
    private String correo;
    private Boolean eliminado;
    private String cp;
    private String estado;
    private String ciudad;
    private String colonia;
    private String calle;
    private String ext;
    private String interior;
    private String auto;
    private LocalDate inicio_credito;
    private LocalDate fin_credito;
    private float mensualidad;
}
