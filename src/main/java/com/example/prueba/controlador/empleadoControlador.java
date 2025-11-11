package com.example.prueba.controlador;

import lombok.AllArgsConstructor;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.prueba.dtos.*;
import com.example.prueba.dtos.empleados.*;
import com.example.prueba.excepciones.*;
import com.example.prueba.servicios.empleadoServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@AllArgsConstructor
@RestController
@RequestMapping("api/profile")
public class empleadoControlador {
    empleadoServices empleadoServices;

    @PostMapping
    public ResponseEntity<respuestaDTO<empleadoCreacionDTO>> creaEmpleado(@RequestBody creaEmpleadoDTO creaEmpleadoDTO){
        respuestaDTO<empleadoCreacionDTO> nuevoEmpleado = empleadoServices.creaEmpleado(creaEmpleadoDTO);
        return new ResponseEntity<>(nuevoEmpleado,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<respuestaDTO<empleadoCreacionDTO>> actualizaCompleto(@PathVariable UUID id, @RequestBody empleadoDTO empleadoDTO){
        respuestaDTO<empleadoCreacionDTO> actualizaEmpleado = empleadoServices.actualizaCompleto(id, empleadoDTO);
        return new ResponseEntity<>(actualizaEmpleado, HttpStatus.OK);
    }
    
}
