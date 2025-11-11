package com.example.prueba.dtos;

import lombok.*;

@Getter
@AllArgsConstructor
@Setter
public class respuestaDTO<T>{
    private int codigo;
    private String mensaje;
    private T resultado;
}
