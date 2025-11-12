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

    @PostMapping("/crear")
    public ResponseEntity<RespuestaDTO<EmpleadoCreacionDTO>> creaEmpleado(@RequestBody CreaEmpleadoDTO creaEmpleadoDTO){
        RespuestaDTO<EmpleadoCreacionDTO> nuevoEmpleado = empleadoServices.creaEmpleado(creaEmpleadoDTO);
        return new ResponseEntity<>(nuevoEmpleado,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<RespuestaDTO<EmpleadoCreacionDTO>> obtenerPerfil() {
        RespuestaDTO<EmpleadoCreacionDTO> respuesta = empleadoServices.obtenerPerfilActual();
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO<EmpleadoCreacionDTO>> actualizaCompleto(@PathVariable UUID id, @RequestBody EmpleadoDTO empleadoDTO){
        RespuestaDTO<EmpleadoCreacionDTO> actualizaEmpleado = empleadoServices.actualizaCompleto(id, empleadoDTO);
        return new ResponseEntity<>(actualizaEmpleado, HttpStatus.OK);
    }
    

    /**
     * Este es el login para obtener los tokens
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request) {
        TokenDTO tokenDTO = empleadoServices.login(request);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody RefreshTokenDTO request) {
        TokenDTO tokenDTO = empleadoServices.refreshToken(request);
        return ResponseEntity.ok(tokenDTO);
    }

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
