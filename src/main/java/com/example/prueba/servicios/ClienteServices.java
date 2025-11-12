package com.example.prueba.servicios;

import com.example.prueba.dtos.clientes.*;
import com.example.prueba.dtos.*;
import java.util.UUID;

/**
 * Definición de los servicios que tendran los clientes
 */
public interface ClienteServices {
    RespuestaDTO<ClienteCreacionDTO> creaCliente(CreaClienteDTO creaClienteDTO);

    RespuestaDTO<ListaDTO> obtenerClientes(int page, int limit);

    RespuestaDTO<ClienteCreacionDTO> actualizaClienteParcial(UUID id, ClienteDTO clienteDTO);

    RespuestaDTO<EliminarClienteDTO> eliminarCliente(UUID id);
}