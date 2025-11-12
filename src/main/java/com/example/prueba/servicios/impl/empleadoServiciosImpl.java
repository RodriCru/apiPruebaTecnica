package com.example.prueba.servicios.impl;

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
import com.example.prueba.dtos.*;
import com.example.prueba.dtos.empleados.*;
import com.example.prueba.excepciones.*;
import com.example.prueba.modelos.Empleado;
import com.example.prueba.repositorio.EmpleadoRepositorio;
import com.example.prueba.servicios.EmpleadoServices;
import com.example.prueba.jwt.*;

@Service
@RequiredArgsConstructor
public class EmpleadoServiciosImpl implements EmpleadoServices {
    private final EmpleadoRepositorio empleadoRepositorio;
    private final AuthenticationManager authenticationManager;
    private final ServicioJwt jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklist tokenBlacklist;

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
    public RespuestaDTO<EmpleadoCreacionDTO> creaEmpleado(CreaEmpleadoDTO creaEmpleadoDTO){
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
                throw new ErrorCreacion("Datos invalidos.");
            }

            if (empleadoRepositorio.findByCorreo(creaEmpleadoDTO.getCorreo()).isPresent()){
                throw new DatosDucplicados("El correo ya existe.");
            }

            if(empleadoRepositorio.findByUsuario(creaEmpleadoDTO.getUsuario()).isPresent()){
                throw new DatosDucplicados("El usuario ya existe.");
            }

            Empleado empleado = new Empleado();
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

            EmpleadoCreacionDTO nuevoEmpleadoDTO = new EmpleadoCreacionDTO();
            nuevoEmpleadoDTO.setId(empleado.getId());
            nuevoEmpleadoDTO.setNombre(empleado.getNombre());
            nuevoEmpleadoDTO.setApellidoP(empleado.getApellidoP());
            nuevoEmpleadoDTO.setApellidoM(empleado.getApellidoM());
            nuevoEmpleadoDTO.setCorreo(empleado.getCorreo());
            nuevoEmpleadoDTO.setUsuario(empleado.getUsuario());

            return new RespuestaDTO<EmpleadoCreacionDTO>(201, "Empleado creado correctamente.", nuevoEmpleadoDTO);
        } catch (ErrorCreacion e) {
            throw new ErrorCreacion("Datos invalidos.");
        } catch (DatosDucplicados e){
            throw new DatosDucplicados("El usuario ya existe.");
        } catch (Exception e){
            throw new ErrorServidor("Error interno del servidor.");
        }
    }

    @Override
    public RespuestaDTO<EmpleadoCreacionDTO> obtenerPerfilActual() {
        try {
            // 1. Obtener el usuario autenticado del contexto de seguridad
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            Empleado empleado = empleadoRepositorio.findByUsuario(username)
                    .orElseThrow(() -> new RecursoNoEncontrado("Usuario autenticado no encontrado."));

            // 2. Mapear al DTO de respuesta
            EmpleadoCreacionDTO perfil = new EmpleadoCreacionDTO();
            perfil.setId(empleado.getId());
            perfil.setNombre(empleado.getNombre());
            perfil.setApellidoP(empleado.getApellidoP());
            perfil.setApellidoM(empleado.getApellidoM());
            perfil.setCorreo(empleado.getCorreo());
            perfil.setUsuario(empleado.getUsuario());

            return new RespuestaDTO<>(200, "Perfil obtenido correctamente.", perfil);

        } catch (Exception e) {
            throw new ErrorServidor("Error al obtener el perfil del usuario.");
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
    public RespuestaDTO<EmpleadoCreacionDTO> actualizaCompleto(UUID id, EmpleadoDTO empleadoDTO){
        try {
             Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Usuario no encontrado"));
            if(empleadoDTO.getNombre() == null
                || empleadoDTO.getApellidoP() == null
                || empleadoDTO.getNacimiento() == null
                || empleadoDTO.getRfc() == null
                || empleadoDTO.getCurp() == null
                || empleadoDTO.getCorreo() == null
                || empleadoDTO.getUsuario() == null
                || empleadoDTO.getContrasena() == null
            ){
                throw new ErrorCreacion("Datos invalidos.");
            }

            Optional<Empleado> empleadoConEmail = empleadoRepositorio.findByCorreo(empleadoDTO.getCorreo());
            if (empleadoConEmail.isPresent() && !empleadoConEmail.get().getId().equals(id)) {
                throw new DatosDucplicados("El correo ya existe.");
            }

            Optional<Empleado> empleadoConUsuario = empleadoRepositorio.findByUsuario(empleadoDTO.getUsuario());
            if (empleadoConUsuario.isPresent() && !empleadoConUsuario.get().getId().equals(id)) {
                throw new DatosDucplicados("El usuario ya existe.");
            }

            empleado.setNombre(empleadoDTO.getNombre());
            empleado.setApellidoP(empleadoDTO.getApellidoP());
            empleado.setApellidoM(empleadoDTO.getApellidoM());
            empleado.setNacimiento(empleadoDTO.getNacimiento());
            empleado.setRfc(empleadoDTO.getRfc());
            empleado.setCurp(empleadoDTO.getCurp());
            empleado.setTelefono(empleadoDTO.getTelefono());
            empleado.setCorreo(empleadoDTO.getCorreo());
            empleado.setUsuario(empleadoDTO.getUsuario());
            if (empleadoDTO.getContrasena() != null && !empleadoDTO.getContrasena().trim().isEmpty()) {
                empleado.setContrasena(passwordEncoder.encode(empleadoDTO.getContrasena()));
            }
            empleado.setEliminado(empleadoDTO.getEliminado());
            empleado.setCp(empleadoDTO.getCp());
            empleado.setEstado(empleadoDTO.getEstado());
            empleado.setCiudad(empleadoDTO.getCiudad());
            empleado.setColonia(empleadoDTO.getColonia());
            empleado.setCalle(empleadoDTO.getCalle());
            empleado.setExt(empleadoDTO.getExt());
            empleado.setInterior(empleadoDTO.getInterior());

            empleado = empleadoRepositorio.save(empleado);

            EmpleadoCreacionDTO nuevoEmpleadoDTO = new EmpleadoCreacionDTO();
            nuevoEmpleadoDTO.setId(empleado.getId());
            nuevoEmpleadoDTO.setNombre(empleado.getNombre());
            nuevoEmpleadoDTO.setApellidoP(empleado.getApellidoP());
            nuevoEmpleadoDTO.setApellidoM(empleado.getApellidoM());
            nuevoEmpleadoDTO.setCorreo(empleado.getCorreo());
            nuevoEmpleadoDTO.setUsuario(empleado.getUsuario());

            return new RespuestaDTO<EmpleadoCreacionDTO>(200, "Empleado actualizado correctamente.", nuevoEmpleadoDTO);
        } catch (ErrorCreacion e) {
            throw new ErrorCreacion("Datos invalidos.");
        } catch (DatosDucplicados e){
            throw new DatosDucplicados("El usuario ya existe.");
        } catch (Exception e){
            throw new ErrorServidor("Error interno del servidor.");
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
    
    
    @Override
    public TokenDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getContrasena()));

        Optional<Empleado> userOptional = empleadoRepositorio.findByUsuario(request.getUsuario());
        if (userOptional.isEmpty()) {
            throw new RecursoNoEncontrado("Credenciales inválidas");
        }

        String accessToken = jwtService.generateAccessToken(request.getUsuario());
        String refreshToken = jwtService.generateRefreshToken(request.getUsuario());

        return new TokenDTO(accessToken, refreshToken);
    }

    @Override
    public TokenDTO refreshToken(RefreshTokenDTO request) {
        String refreshToken = request.getRefreshToken();
        String username = jwtService.extractUsername(refreshToken);
        String jti = jwtService.extractClaim(refreshToken, Claims::getId);

        Empleado user = empleadoRepositorio.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (!jwtService.isTokenValid(refreshToken, user.getUsuario())) {
            throw new BadCredentialsException("Refresh token inválido o expirado");
        }

        String newAccessToken = jwtService.generateAccessToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);

        return TokenDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Override
    public void logout(String token) {
        String jti = jwtService.extractClaim(token, Claims::getId);
        String username = jwtService.extractUsername(token);
        
        // Agregar token a lista negra
        tokenBlacklist.addToBlacklist(jti, jwtService.extractExpiration(token));
        
        SecurityContextHolder.clearContext();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado user = empleadoRepositorio.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // ¡Obligatorio! Spring Security requiere al menos una autoridad.
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER") // Prefijo "ROLE_" requerido para .hasRole()
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUsuario(),
                user.getContrasena(),
                authorities);
    }
}
