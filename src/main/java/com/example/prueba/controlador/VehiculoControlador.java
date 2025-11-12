package com.example.prueba.controlador;

import lombok.AllArgsConstructor;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.prueba.dtos.*;
import com.example.prueba.dtos.vehiculos.*;
import com.example.prueba.servicios.VehiculoServices;



@AllArgsConstructor
@RestController
@RequestMapping("api/vehiculos")
public class VehiculoControlador {
    VehiculoServices vehiculoServices;

    @GetMapping
    public ResponseEntity<RespuestaDTO<ListaVehiculosDTO>> obtenerVehiculos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limite,
            @RequestParam(defaultValue = "modelo") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String estatus,
            @RequestParam(required = false) Boolean vendido,
            @RequestParam(required = false) Boolean eliminado) {
        RespuestaDTO<ListaVehiculosDTO> respuesta = vehiculoServices.obtenerVehiculos(
                page, limite, sort, order, estatus, vendido, eliminado);
        return ResponseEntity.ok(respuesta);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO<DatosVehiculo>> obtenerVehiculoPorID(@PathVariable UUID id){
        RespuestaDTO<DatosVehiculo> respuesta = vehiculoServices.obtenerVehiculoPorID(id);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO<VehiculoCreacion>> registrarVehiculo(@RequestBody VehiculoDTO vehiculoDTO){
        RespuestaDTO<VehiculoCreacion> nuevoVehiculo = vehiculoServices.registrarVehiculo(vehiculoDTO);
        return new ResponseEntity<>(nuevoVehiculo,HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RespuestaDTO<VehiculoCreacion>> actualizaVehiculoParcial(@PathVariable UUID id, @RequestBody DatosVehiculo datosVehiculo){
        RespuestaDTO<VehiculoCreacion> actualizaVehiculo = vehiculoServices.actualizaVehiculoParcial(id, datosVehiculo);
        return new ResponseEntity<>(actualizaVehiculo, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO<VehiculoCreacion>> actualizaVehiculoCompleto(@PathVariable UUID id, @RequestBody VehiculoDTO vehiculoDTO){
        RespuestaDTO<VehiculoCreacion> actualizaVehiculo = vehiculoServices.actualizaVehiculoCompleto(id, vehiculoDTO);
        return new ResponseEntity<>(actualizaVehiculo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO<VehiculoEliminadoDTO>> eliminaVehiculo(@PathVariable UUID id){
        RespuestaDTO<VehiculoEliminadoDTO> eliminar = vehiculoServices.eliminaVehiculo(id);
        return new ResponseEntity<>(eliminar, HttpStatus.OK);
    }

    @PatchMapping("/{id}/restaura")
    public ResponseEntity<RespuestaDTO<VehiculoEliminadoDTO>> restaurarVehiculo(@PathVariable UUID id){
        RespuestaDTO<VehiculoEliminadoDTO> restaurar = vehiculoServices.restaurarVehiculo(id);
        return new ResponseEntity<>(restaurar, HttpStatus.OK);
    }
    
}
