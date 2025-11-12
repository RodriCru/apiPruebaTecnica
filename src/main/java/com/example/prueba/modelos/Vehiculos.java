package com.example.prueba.modelos;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Modelo del vehiculo, es el encargado de crear la tabla vehiculos en la BD
 */
@Data
@Entity
@Table(name="vehiculos")
public class Vehiculos {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="marca")
    @NotBlank(message = "Marca Obligatoria.")
    @Size(min=1,max=20, message= "Longitud maxima de 20 cacracteres.")
    private String marca;

    @Column(name="modelo")
    @NotBlank(message = "Modelo obligatorio")
    @Size(min=1,max=20, message= "Longitud maxima de 20 cacracteres.")
    private String modelo;

    @Column(name="estatus")
    @NotBlank(message = "Estatus obligatorio.")
    @Size(min=1,max=10, message= "Longitud maxima de 10 cacracteres.")
    private String estatus;

    @Column(name="vendido")
    private Boolean vendido;

    @Column(name="eliminado")
    private Boolean eliminado;
}
