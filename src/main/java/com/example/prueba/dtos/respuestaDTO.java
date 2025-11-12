package com.example.prueba.dtos;

import lombok.*;

/**
 * Es el encargado de mostrar las respuestas mas estrcuturadas y personalizadas.
 */
@Getter
@AllArgsConstructor
@Setter
public class RespuestaDTO<T>{
    private int codigo;
    private String mensaje;
    private T resultado;
}
