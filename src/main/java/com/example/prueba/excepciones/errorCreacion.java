package com.example.prueba.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class errorCreacion extends RuntimeException {
    public errorCreacion(String mensaje){ super(mensaje);}
}
