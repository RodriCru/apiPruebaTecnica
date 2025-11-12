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

    /**
     * Lista de vehiculos y que puede aplicarse filtros.
     * @param page número de pagina.
     * @param limite limite por página.
     * @param sort atributo por el que se ordena, en este caso el modelo.
     * @param order orden en el que se muestram, en este caso por asc.
     * @param estatus si el carro es Nuevo o Usado.
     * @param vendido Si esta vendido o no (true o false)
     * @param eliminado Si el vehiculo ah sido eliminado de la base de datos.
     * @return regresa la lista de vehiculos.
     */
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

    /**
     * busca un vehiculo por su identificador
     * @param id idntificador unico del auto
     * @return los datos del vehiculo.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO<DatosVehiculo>> obtenerVehiculoPorID(@PathVariable UUID id){
        RespuestaDTO<DatosVehiculo> respuesta = vehiculoServices.obtenerVehiculoPorID(id);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }

    /**
     * Registrar un nuevo vehiculo
     * @param vehiculoDTO Parámetros que lleva el vehiculo.
     * @return regresa lso datos del vehiculo o un mensaje de error en caso de fallo.
     */
    @PostMapping
    public ResponseEntity<RespuestaDTO<VehiculoCreacion>> registrarVehiculo(@RequestBody VehiculoDTO vehiculoDTO){
        RespuestaDTO<VehiculoCreacion> nuevoVehiculo = vehiculoServices.registrarVehiculo(vehiculoDTO);
        return new ResponseEntity<>(nuevoVehiculo,HttpStatus.CREATED);
    }

    /**
     * Actauliza parcialememnte los datos de un vehiculo.
     * @param id idetificador unico del vehiculo.
     * @param datosVehiculo parámetro a actualizar.
     * @return mensaje de exito y un codigo 200.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<RespuestaDTO<VehiculoCreacion>> actualizaVehiculoParcial(@PathVariable UUID id, @RequestBody DatosVehiculo datosVehiculo){
        RespuestaDTO<VehiculoCreacion> actualizaVehiculo = vehiculoServices.actualizaVehiculoParcial(id, datosVehiculo);
        return new ResponseEntity<>(actualizaVehiculo, HttpStatus.OK);
    }

    /**
     * Actuliza completamente un auto
     * @param id idetificador unico del vehiculo.
     * @param vehiculoDTO parámetros a actualizar.
     * @return mensaje de exito y un codigo 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO<VehiculoCreacion>> actualizaVehiculoCompleto(@PathVariable UUID id, @RequestBody VehiculoDTO vehiculoDTO){
        RespuestaDTO<VehiculoCreacion> actualizaVehiculo = vehiculoServices.actualizaVehiculoCompleto(id, vehiculoDTO);
        return new ResponseEntity<>(actualizaVehiculo, HttpStatus.OK);
    }

    /**
     * Elimina lógicamente un automovil
     * @param id idetificador unico del vehiculo.
     * @return mensaje de exito.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO<VehiculoEliminadoDTO>> eliminaVehiculo(@PathVariable UUID id){
        RespuestaDTO<VehiculoEliminadoDTO> eliminar = vehiculoServices.eliminaVehiculo(id);
        return new ResponseEntity<>(eliminar, HttpStatus.OK);
    }

    /**
     * Reataura en vehiculo previamente elimindo
     * @param id idetificador unico del vehiculo.
     * @return mensaje de exito.
     */
    @PatchMapping("/{id}/restaura")
    public ResponseEntity<RespuestaDTO<VehiculoEliminadoDTO>> restaurarVehiculo(@PathVariable UUID id){
        RespuestaDTO<VehiculoEliminadoDTO> restaurar = vehiculoServices.restaurarVehiculo(id);
        return new ResponseEntity<>(restaurar, HttpStatus.OK);
    }
    
}
