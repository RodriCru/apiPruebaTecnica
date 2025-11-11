package com.example.prueba.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Este metodo es una excepción, que trabaja con el código de estado que devuelve, por eso se usa
 * @ResponseStatus, en caso de que devuela el código 500 este manda a llamara a la función errorServidor
 * junto con un mensaje de error que es personalizable, esta excepción entra cuando falla algo 
 * en el servidor
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class errorServidor extends RuntimeException{
    public errorServidor(String mensaje){ super(mensaje);}
       
}
