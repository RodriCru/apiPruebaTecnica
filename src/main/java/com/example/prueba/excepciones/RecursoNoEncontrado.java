package com.example.prueba.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Este metodo es una excepción, que trabaja con el código de estado que devuelve, por eso se usa
 * @ResponseStatus, en caso de que devuela el código 400 este manda a llamara a la función resursoNoEncontrado
 * junto con un mensaje de error que es personalizable, esta excepción entra cuando al buscar un usuario, actualizar o listar este no tiene exito. 
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecursoNoEncontrado extends RuntimeException {
    public RecursoNoEncontrado(String mensaje){ super(mensaje); }
}
