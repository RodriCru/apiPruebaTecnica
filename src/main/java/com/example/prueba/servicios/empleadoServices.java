package com.example.prueba.servicios;

import com.example.prueba.dtos.empleados.*;
import com.example.prueba.dtos.*;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Definición de los servicios que tendran los empleados
 */
public interface EmpleadoServices {
    /**
     * Crea un empleado
     * @param creaEmpleadoDTO
     * @return mensaje de exito
     */
    RespuestaDTO<EmpleadoCreacionDTO> creaEmpleado(CreaEmpleadoDTO creaEmpleadoDTO);

    /**
     * Muestra los datos del usuario logueado.
     * @return datos del usuario.
     */
    RespuestaDTO<EmpleadoCreacionDTO> obtenerPerfilActual();

    RespuestaDTO<EmpleadoCreacionDTO>actualizaCompleto(UUID id, EmpleadoDTO empleadoDTO);

    /**
     * Este es el metodo que usan los tokens
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

     /**
     * Perimte hacer el login para obtener los tokens
     * @param request Requiere un email y una contraseña existente en la base de datos
     * @return un access token y un refresh token
     */
    TokenDTO login(LoginDTO request);

    /**
     * Genera un nuevo access token despues de que este halla vencido
     * @param request Refresh token obtenido al hacer login
     * @return un nuevo access token
     */
    TokenDTO refreshToken(RefreshTokenDTO request);
    
    /**
     * El access token y refresh token son revocados para ya no ser usados
     * @param token access token que se obtiene al iniciar sesión o al generar uno nuevo
     */
    void logout(String token);
}
