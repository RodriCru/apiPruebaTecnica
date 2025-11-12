package com.example.prueba.modelos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Modelo del empleado, es el encargado de crear la tabla empleados en la BD
 */
@Data
@Entity
@Table(name="empleados")
public class Empleado {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="nombre")
    @NotBlank(message = "Nombre obligatorio")
    @Size(min=1,max=60, message= "Longitud maxima de 60 cacracteres.")
    private String nombre;

    @Column(name="apellidoP")
    @NotBlank(message = "Nombre obligatorio")
    @Size(min=1,max=60, message= "Longitud maxima de 60 cacracteres.")
    private String apellidoP;

    @Column(name="apellidoM")
    private String apellidoM;

    @Column(name="nacimiento")
    @NotNull
    private LocalDate nacimiento;

    @Column(name="rfc")
    @NotBlank(message = "RFC obligatorio.")
    @Size(min=10,max=13, message= "Longitud maxima de 13 cacracteres")
    private String rfc;

    @Column(name="curp")
    @NotBlank(message = "CURP obligatorio.")
    @Size(min=18,max=18, message= "Longitud maxima de 18 cacracteres")
    private String curp;

    @Column(name="telefono")
    @Size(min=10,max=10, message= "Longitud maxima de 10 cacracteres")
    private String telefono;

    @Column(name="correo")
    @NotBlank(message = "Correo obligatorio.")
    @Size(min=1,max=60, message= "Longitud maxima de 60 cacracteres")
    private String correo;

    @Column(name="usuario")
    @NotBlank(message = "Usuario obligatorio.")
    @Size(min=1,max=10, message= "Longitud maxima de 10 cacracteres.")
    private String usuario;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(name="eliminado")
    private Boolean eliminado;

    @Column(name="cp")
    @Size(min=5,max=5, message= "Longitud maxima de 5 cacracteres")
    private String cp;

    @Column(name="estado")
    @Size(min=1,max=18, message= "Longitud maxima de 18 cacracteres")
    private String estado;

    @Column(name="ciudad")
    @Size(min=1,max=25, message= "Longitud maxima de 25 cacracteres")
    private String ciudad;

    @Column(name="colonia")
    @Size(min=1,max=25, message= "Longitud maxima de 25 cacracteres")
    private String colonia;

    @Column(name="calle")
    @Size(min=1,max=25, message= "Longitud maxima de 25 cacracteres")
    private String calle;

    @Column(name="exterior")
    @Size(min=1,max=60, message= "Longitud maxima de 60 cacracteres")
    private String ext;

    @Column(name="interior")
    private String interior;
}
