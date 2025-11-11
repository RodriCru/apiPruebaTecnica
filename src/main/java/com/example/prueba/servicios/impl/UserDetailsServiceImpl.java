package com.example.prueba.servicios.impl;

import com.example.prueba.modelos.Empleado;
import com.example.prueba.repositorio.EmpleadoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmpleadoRepositorio empleadoRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado empleado= empleadoRepositorio.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return org.springframework.security.core.userdetails.User
                .withUsername(empleado.getUsuario())  // email es username
                .password(empleado.getContrasena())
                .roles("USER") //Este esta bie, no mover
                .build();
    }
}
