package com.example.prueba.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prueba.modelos.Empleado;

public interface EmpleadoRepositorio extends JpaRepository<Empleado,UUID> {

    /**
     * Busca el email que se ah proporcionado
     * @param email del usurio
     * @return regresa el email y se entiende que existe
     */
    Optional<Empleado> findByCorreo(String email);

    /**
     * Busca el usuario del usuario
     * @param username del usuario
     * @return regresa el username del usuario y se valida que existe
     */
    Optional<Empleado> findByUsuario(String username);
}
