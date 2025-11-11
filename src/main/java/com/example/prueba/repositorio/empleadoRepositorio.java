package com.example.prueba.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prueba.modelos.empleado;

public interface empleadoRepositorio extends JpaRepository<empleado,UUID> {

    /**
     * Busca el email que se ah proporcionado
     * @param email del usurio
     * @return regresa el email y se entiende que existe
     */
    Optional<empleado> findByEmail(String email);

    /**
     * Busca el username del usuario
     * @param username del usuario
     * @return regresa el username del usuario y se valida que existe
     */
    Optional<empleado> findByUsername(String username);
}
