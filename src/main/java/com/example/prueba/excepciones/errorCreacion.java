package com.example.prueba.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción que funciona cuando algun registro, actualización sale mal.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ErrorCreacion extends RuntimeException {
    public ErrorCreacion(String mensaje){ super(mensaje);}
}
