package com.example.prueba.servicios.impl;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.security.Principal;
import java.time.OffsetDateTime;
import com.example.prueba.dtos.*;
import com.example.prueba.dtos.empleados.*;
import com.example.prueba.excepciones.*;
import com.example.prueba.modelos.empleado;
import com.example.prueba.repositorio.empleadoRepositorio;
import com.example.prueba.servicios.empleadoServices;

@Service
@RequiredArgsConstructor
public class empleadoServiciosImpl implements empleadoServices {
    private final empleadoRepositorio empleadoRepositorio;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo usuario a partir de un objeto UserDTO.
     * 
     * Primero valida que los campos obligatorios no estén vacíos. Si algún campo obligatorio
     * está vacío, se lanza una excepción.
     * 
     * Luego, mapea los datos del DTO a una entidad usuario, la guarda en la base de datos
     * y devuelve un nuevo usuario con los datos del usuario ya creado.
     * 
     * @param userDTO Objeto {@link UserDTO} que contiene los datos del usuario a crear.
     * @return Un objeto {@link UserDTO} con los datos del usuario creado.
     * @throws creacionFallida Si algún campo obligatorio está vacío.
    */
    @Override
    public respuestaDTO<empleadoCreacionDTO> creaEmpleado(creaEmpleadoDTO creaEmpleadoDTO){
        try {
            if(creaEmpleadoDTO.getNombre() == null
                || creaEmpleadoDTO.getApellidoP() == null
                || creaEmpleadoDTO.getNacimiento() == null
                || creaEmpleadoDTO.getRfc() == null
                || creaEmpleadoDTO.getCurp() == null
                || creaEmpleadoDTO.getCorreo() == null
                || creaEmpleadoDTO.getUsuario() == null
                || creaEmpleadoDTO.getContrasena() == null
            ){
                throw new errorCreacion("Datos invalidos.");
            }

            if (empleadoRepositorio.findByEmail(creaEmpleadoDTO.getCorreo()).isPresent()){
                throw new datosDucplicados("El correo ya existe.");
            }

            if(empleadoRepositorio.findByUsername(creaEmpleadoDTO.getUsuario()).isPresent()){
                throw new datosDucplicados("El usuario ya existe.");
            }

            empleado empleado = new empleado();
            empleado.setNombre(creaEmpleadoDTO.getNombre());
            empleado.setApellidoP(creaEmpleadoDTO.getApellidoP());
            empleado.setApellidoM(creaEmpleadoDTO.getApellidoM());
            empleado.setNacimiento(creaEmpleadoDTO.getNacimiento());
            empleado.setRfc(creaEmpleadoDTO.getRfc());
            empleado.setCurp(creaEmpleadoDTO.getCurp());
            empleado.setTelefono(creaEmpleadoDTO.getTelefono());
            empleado.setCorreo(creaEmpleadoDTO.getCorreo());
            empleado.setUsuario(creaEmpleadoDTO.getUsuario());
            empleado.setContrasena(passwordEncoder.encode(creaEmpleadoDTO.getContrasena()));
            empleado.setEliminado(false);
            empleado.setCp(creaEmpleadoDTO.getCp());
            empleado.setEstado(creaEmpleadoDTO.getEstado());
            empleado.setCiudad(creaEmpleadoDTO.getCiudad());
            empleado.setColonia(creaEmpleadoDTO.getColonia());
            empleado.setCalle(creaEmpleadoDTO.getCalle());
            empleado.setExt(creaEmpleadoDTO.getExt());
            empleado.setInterior(creaEmpleadoDTO.getInterior());

            empleado = empleadoRepositorio.save(empleado);

            empleadoCreacionDTO nuevoEmpleadoDTO = new empleadoCreacionDTO();
            nuevoEmpleadoDTO.setId(empleado.getId());
            nuevoEmpleadoDTO.setNombre(empleado.getNombre());
            nuevoEmpleadoDTO.setApellidoP(empleado.getApellidoP());
            nuevoEmpleadoDTO.setApellidoM(empleado.getApellidoM());
            nuevoEmpleadoDTO.setCorreo(empleado.getCorreo());
            nuevoEmpleadoDTO.setUsuario(empleado.getUsuario());

            return new respuestaDTO<empleadoCreacionDTO>(201, "Empleado creado correctamente.", nuevoEmpleadoDTO);
        } catch (errorCreacion e) {
            throw new errorCreacion("Datos invalidos.");
        } catch (datosDucplicados e){
            throw new datosDucplicados("El usuario ya existe.");
        } catch (Exception e){
            throw new errorServidor("Error interno del servidor.");
        }
    }
    
    /**
     * Actualiza un empleado a partir de su Id.
     * 
     * Primero valida que los campos obligatorios no estén vacíos. Si algún campo
     * obligatorio
     * está vacío, se lanza una excepción @link creacionFallida.
     * 
     * <p>
     * Luego, mapea los datos del DTO a una entidad @link User, la actualiza en la
     * base de datos por completo
     * y devuelve un nuevo @link UserDTO con los datos del usuario actualizado
     * 
     * @param userDTO y id, Objeto @link UserDTO que contiene los datos del usuario
     *                a actualizar.
     * @return Un objeto @link UserDTO con los datos del usuario actualizado.
     * @throws creacionFallida Si algún campo obligatorio está vacío.
     */
    @Override
    public respuestaDTO<empleadoCreacionDTO> actualizaCompleto(UUID id, empleadoDTO empleadoDTO){
        try {
            if(empleadoDTO.getNombre() == null
                || empleadoDTO.getApellidoP() == null
                || empleadoDTO.getNacimiento() == null
                || empleadoDTO.getRfc() == null
                || empleadoDTO.getCurp() == null
                || empleadoDTO.getCorreo() == null
                || empleadoDTO.getUsuario() == null
                || empleadoDTO.getContrasena() == null
            ){
                throw new errorCreacion("Datos invalidos.");
            }

            if (empleadoRepositorio.findByEmail(empleadoDTO.getCorreo()).isPresent()){
                throw new datosDucplicados("El correo ya existe.");
            }

            if(empleadoRepositorio.findByUsername(empleadoDTO.getUsuario()).isPresent()){
                throw new datosDucplicados("El usuario ya existe.");
            }

            empleado empleado = new empleado();
            empleado.setNombre(empleadoDTO.getNombre());
            empleado.setApellidoP(empleadoDTO.getApellidoP());
            empleado.setApellidoM(empleadoDTO.getApellidoM());
            empleado.setNacimiento(empleadoDTO.getNacimiento());
            empleado.setRfc(empleadoDTO.getRfc());
            empleado.setCurp(empleadoDTO.getCurp());
            empleado.setTelefono(empleadoDTO.getTelefono());
            empleado.setCorreo(empleadoDTO.getCorreo());
            empleado.setUsuario(empleadoDTO.getUsuario());
            empleado.setContrasena(null);
            empleado.setEliminado(empleadoDTO.getEliminado());
            empleado.setCp(empleadoDTO.getCp());
            empleado.setEstado(empleadoDTO.getEstado());
            empleado.setCiudad(empleadoDTO.getCiudad());
            empleado.setColonia(empleadoDTO.getColonia());
            empleado.setCalle(empleadoDTO.getCalle());
            empleado.setExt(empleadoDTO.getExt());
            empleado.setInterior(empleadoDTO.getInterior());

            empleado = empleadoRepositorio.save(empleado);

            empleadoCreacionDTO nuevoEmpleadoDTO = new empleadoCreacionDTO();
            nuevoEmpleadoDTO.setId(empleado.getId());
            nuevoEmpleadoDTO.setNombre(empleado.getNombre());
            nuevoEmpleadoDTO.setApellidoP(empleado.getApellidoP());
            nuevoEmpleadoDTO.setApellidoM(empleado.getApellidoM());
            nuevoEmpleadoDTO.setCorreo(empleado.getCorreo());
            nuevoEmpleadoDTO.setUsuario(empleado.getUsuario());

            return new respuestaDTO<empleadoCreacionDTO>(201, "Empleado actualizado correctamente.", nuevoEmpleadoDTO);
        } catch (errorCreacion e) {
            throw new errorCreacion("Datos invalidos.");
        } catch (datosDucplicados e){
            throw new datosDucplicados("El usuario ya existe.");
        } catch (Exception e){
            throw new errorServidor("Error interno del servidor.");
        }
    }


    /**
     * Obtiene un usuario a partir de su ID.
     * 
     * Primero valida que el campo ID no este vacío, si está vacío, se lanza una
     * excepción.
     * 
     * @param id Objeto {@link UserDTO} que contiene los datos del usuario a crear.
     * @return Un objeto UserDTO con los datos del usuario.
     * @throws recursoNoEncontrado Si el campo id está vacío.
     */
    
    

}
