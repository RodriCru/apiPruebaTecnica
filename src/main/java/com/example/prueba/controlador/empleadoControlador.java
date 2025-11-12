package com.example.prueba.controlador;

import lombok.AllArgsConstructor;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.prueba.dtos.*;
import com.example.prueba.dtos.empleados.*;
import com.example.prueba.servicios.EmpleadoServices;



@AllArgsConstructor
@RestController
@RequestMapping("api/profile")
public class EmpleadoControlador {
    private final EmpleadoServices empleadoServices;

    /**
     * Crea un nuevo empleado
     * @param creaEmpleadoDTO parámetros que debe de llevar un empleado nuevo
     * @return un mesaje de exito.
     */
    @PostMapping("/crear")
    public ResponseEntity<RespuestaDTO<EmpleadoCreacionDTO>> creaEmpleado(@RequestBody CreaEmpleadoDTO creaEmpleadoDTO){
        RespuestaDTO<EmpleadoCreacionDTO> nuevoEmpleado = empleadoServices.creaEmpleado(creaEmpleadoDTO);
        return new ResponseEntity<>(nuevoEmpleado,HttpStatus.CREATED);
    }

    /**
     * Obtiene los datos del mismo usuario logueado.
     * @return parámetros del empelado.
     */
    @GetMapping
    public ResponseEntity<RespuestaDTO<EmpleadoCreacionDTO>> obtenerPerfil() {
        RespuestaDTO<EmpleadoCreacionDTO> respuesta = empleadoServices.obtenerPerfilActual();
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualiza completamente un Empleado.
     * @param id identificador unico del empleado.
     * @param empleadoDTO parámetros a editar del empleado.
     * @return mensaje de exito con un código 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO<EmpleadoCreacionDTO>> actualizaCompleto(@PathVariable UUID id, @RequestBody EmpleadoDTO empleadoDTO){
        RespuestaDTO<EmpleadoCreacionDTO> actualizaEmpleado = empleadoServices.actualizaCompleto(id, empleadoDTO);
        return new ResponseEntity<>(actualizaEmpleado, HttpStatus.OK);
    }
    

    /**
     * Inicio de sesión
     * @param request usurio y contraseña y que existan en la base de datos.
     * @return acces token y un refresh token.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request) {
        TokenDTO tokenDTO = empleadoServices.login(request);
        return ResponseEntity.ok(tokenDTO);
    }

    /**
     * Si el accessToken expira, solicita uno nuevo
     * @param request refreshToken
     * @return un AccessToken
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody RefreshTokenDTO request) {
        TokenDTO tokenDTO = empleadoServices.refreshToken(request);
        return ResponseEntity.ok(tokenDTO);
    }

    /**
     * Cierre de sesión para el empledo
     * @param authHeader
     * @return una vez terminada la sesión el token se invalida.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String token = authHeader.substring(7);
        empleadoServices.logout(token);
        return ResponseEntity.ok().build();
    }
}
