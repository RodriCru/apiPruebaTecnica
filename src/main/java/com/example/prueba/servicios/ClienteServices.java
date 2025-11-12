package com.example.prueba.servicios;

import com.example.prueba.dtos.clientes.*;
import com.example.prueba.dtos.*;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ClienteServices {
    RespuestaDTO<ClienteCreacionDTO> creaCliente(CreaClienteDTO creaClienteDTO);

    RespuestaDTO<ListaDTO> obtenerClientes(int page, int limit);

    RespuestaDTO<ClienteCreacionDTO> actualizaClienteParcial(UUID id, ClienteDTO clienteDTO);

    RespuestaDTO<EliminarClienteDTO> eliminarCliente(UUID id);
}