package com.example.prueba.controlador;

import lombok.AllArgsConstructor;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.prueba.dtos.*;
import com.example.prueba.dtos.clientes.*;
import com.example.prueba.servicios.ClienteServices;

/**
 * Clase que define los endpoints para el api de los clientes, es un crud muy básico.
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/clientes")
public class ClienteControlador {
    
    private final ClienteServices clienteServices;

    /**
     * Crea un nuevo cliente
     * @param creaClienteDTO parámetros que debe de llevar un cliente nuevo
     * @return un mesaje de exito.
     */
    @PostMapping("/crear")
    public ResponseEntity<RespuestaDTO<ClienteCreacionDTO>> creaCliente(@RequestBody CreaClienteDTO creaClienteDTO){
        RespuestaDTO<ClienteCreacionDTO> nuevoCliente = clienteServices.creaCliente(creaClienteDTO);
        return new ResponseEntity<>(nuevoCliente,HttpStatus.CREATED);
    }

    /**
     * Lista clientes
     * @param page pagina de visualización de la lista de clientes.
     * @param limit limite de la lista de clientes.
     * @return lista de clientes con sus datos.
     */
    @GetMapping
    public ResponseEntity<RespuestaDTO<ListaDTO>> obtenerClientes(
        @RequestParam(defaultValue = "1")int page,
        @RequestParam(defaultValue = "100")int limit){
            RespuestaDTO<ListaDTO> respuesta= clienteServices.obtenerClientes(page, limit);
            return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualiza parcialmente un Cliente.
     * @param id identificador unico del cliente.
     * @param clienteDTO parámetro a editar del cliente.
     * @return mensaje de exito con un código 200.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<RespuestaDTO<ClienteCreacionDTO>> actualizaUserParcial(@PathVariable UUID id, @RequestBody ClienteDTO clienteDTO){
        RespuestaDTO<ClienteCreacionDTO> respuesta = clienteServices.actualizaClienteParcial(id, clienteDTO);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Elimina lógicamente un cliente
     * @param id identificador unico del cliente
     * @return mensaje de exito y un código 200.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO<EliminarClienteDTO>> eliminarCliente(@PathVariable UUID id){
        RespuestaDTO<EliminarClienteDTO> eliminarCliente = clienteServices.eliminarCliente(id);
        return new ResponseEntity<>(eliminarCliente,HttpStatus.OK);
    }

    
}
