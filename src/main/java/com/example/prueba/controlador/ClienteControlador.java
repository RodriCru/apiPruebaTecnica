package com.example.prueba.controlador;

import lombok.AllArgsConstructor;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.prueba.dtos.*;
import com.example.prueba.dtos.clientes.*;
import com.example.prueba.excepciones.*;
import com.example.prueba.servicios.ClienteServices;


@AllArgsConstructor
@RestController
@RequestMapping("api/clientes")
public class ClienteControlador {
    
    private final ClienteServices clienteServices;

    @PostMapping("/crear")
    public ResponseEntity<RespuestaDTO<ClienteCreacionDTO>> creaCliente(@RequestBody CreaClienteDTO creaClienteDTO){
        RespuestaDTO<ClienteCreacionDTO> nuevoCliente = clienteServices.creaCliente(creaClienteDTO);
        return new ResponseEntity<>(nuevoCliente,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<RespuestaDTO<ListaDTO>> obtenerClientes(
        @RequestParam(defaultValue = "1")int page,
        @RequestParam(defaultValue = "100")int limit){
            RespuestaDTO<ListaDTO> respuesta= clienteServices.obtenerClientes(page, limit);
            return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RespuestaDTO<ClienteCreacionDTO>> actualizaUserParcial(@PathVariable UUID id, @RequestBody ClienteDTO clienteDTO){
        RespuestaDTO<ClienteCreacionDTO> respuesta = clienteServices.actualizaClienteParcial(id, clienteDTO);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO<EliminarClienteDTO>> eliminarCliente(@PathVariable UUID id){
        RespuestaDTO<EliminarClienteDTO> eliminarCliente = clienteServices.eliminarCliente(id);
        return new ResponseEntity<>(eliminarCliente,HttpStatus.OK);
    }

    
}
