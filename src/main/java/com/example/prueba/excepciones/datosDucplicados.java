package com.example.prueba.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción cuando algun dato como el correo o el usuario, de un cliente o empleado existe en la base de datos.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DatosDucplicados extends RuntimeException {
    public DatosDucplicados(String mensaje){ super(mensaje);}
}
