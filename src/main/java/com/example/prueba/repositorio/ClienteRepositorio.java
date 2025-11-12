package com.example.prueba.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prueba.modelos.Clientes;
import java.util.List;


/**
 * Interfaz que define el repositorio para la entidad cliente, que 
 * extiende {@link JpaRepository} de Spring Data JPA, proporcionando métodos CRUD
 * y operaciones de paginación y ordenación para la entidad User.
 * 
 * Esta interfaz permite interactuar con la base de datos utilizando el tipo de
 * identificador que es del tipo UUID para la entidad User.
 */

public interface ClienteRepositorio extends JpaRepository<Clientes,UUID> {
    /**
     * Busca el correo que se ah proporcionado
     * @param correo del usurio
     * @return regresa el email y se entiende que existe
     */
    Optional<Clientes> findByCorreo(String email);

    /**
     * Busca si el usurio a sido eliminado
     * @param isDeleted del usurio
     * @return el atributo y depende del resultado se hace la validación, ya que puede ser false o true
     */
    List<Clientes> findByEliminado(boolean isDeleted);
}
