package com.example.prueba.servicios.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.prueba.dtos.*;
import com.example.prueba.dtos.clientes.*;
import com.example.prueba.excepciones.*;
import com.example.prueba.modelos.Clientes;
import com.example.prueba.repositorio.ClienteRepositorio;
import com.example.prueba.servicios.ClienteServices;

/**
 * Clase encargada de los servicios de los clientes.
 */
@Service
@RequiredArgsConstructor
public class ClienteServiciosImpl implements ClienteServices{
    
    private final ClienteRepositorio clienteRepositorio;

    /**
     * Servicio que crea el cliente.
     */
    @Override
    public RespuestaDTO<ClienteCreacionDTO> creaCliente(CreaClienteDTO clienteDTO){
        try {
            if (clienteDTO.getNombre() == null
                    || clienteDTO.getApellidoP() == null
                    || clienteDTO.getNacimiento() == null
                    || clienteDTO.getRfc() == null
                    || clienteDTO.getCurp() == null
                    || clienteDTO.getCorreo() == null) {
                throw new ErrorCreacion("Datos invalidos.");
            }

            if(clienteRepositorio.findByCorreo(clienteDTO.getCorreo()).isPresent()){
                throw new DatosDucplicados("El correo ya existe.");
            }

            Clientes cliente= new Clientes();
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setApellidoP(clienteDTO.getApellidoP());
            cliente.setApellidoM(clienteDTO.getApellidoM());
            cliente.setNacimiento(clienteDTO.getNacimiento());
            cliente.setRfc(clienteDTO.getRfc());
            cliente.setCurp(clienteDTO.getCurp());
            cliente.setTelefono(clienteDTO.getTelefono());
            cliente.setCorreo(clienteDTO.getCorreo());
            cliente.setEliminado(false);
            cliente.setCp(clienteDTO.getCp());
            cliente.setEstado(clienteDTO.getEstado());
            cliente.setCiudad(clienteDTO.getCiudad());
            cliente.setColonia(clienteDTO.getColonia());
            cliente.setCalle(clienteDTO.getCalle());
            cliente.setExt(clienteDTO.getExt());
            cliente.setInterior(clienteDTO.getInterior());
            cliente.setAuto(clienteDTO.getAuto());
            cliente.setInicio_credito(clienteDTO.getInicio_credito());
            cliente.setFin_credito(clienteDTO.getFin_credito());
            cliente.setMensualidad(clienteDTO.getMensualidad());

            cliente = clienteRepositorio.save(cliente);

            ClienteCreacionDTO nuevoClienteDTO = new ClienteCreacionDTO();

            nuevoClienteDTO.setId(cliente.getId());
            nuevoClienteDTO.setNombre(cliente.getNombre());
            nuevoClienteDTO.setApellidoP(cliente.getApellidoP());
            nuevoClienteDTO.setApellidoM(cliente.getApellidoM());
            nuevoClienteDTO.setCorreo(cliente.getCorreo());

            return new RespuestaDTO<ClienteCreacionDTO>(201, "Cliente creado exitosamenete.", nuevoClienteDTO);
            

        } catch (ErrorCreacion e) {
            throw new ErrorCreacion("Datos invalidos.");
        } catch (DatosDucplicados e){
            throw new DatosDucplicados("El correo ya existe.");
        } catch (Exception e){
            throw new ErrorServidor("Error interno del servidor.");
        }
    }

    /**
     * Servicio que obtiene la lista de un cliente.
     */
    @Override
    public RespuestaDTO<ListaDTO> obtenerClientes(int page, int limit){

        try {
            page = Math.max(page, 1);
            Page<Clientes> userPage = clienteRepositorio.findAll(PageRequest.of(page -1, limit));
            List<ClienteDTO> clientes = userPage.getContent().stream()
                 .map(user -> {
                    ClienteDTO clienteDTO = new ClienteDTO();

                    clienteDTO.setId(user.getId());
                    clienteDTO.setNombre(user.getNombre());
                    clienteDTO.setApellidoP(user.getApellidoP());
                    clienteDTO.setApellidoM(user.getApellidoM());
                    clienteDTO.setNacimiento(user.getNacimiento());
                    clienteDTO.setRfc(user.getRfc());
                    clienteDTO.setCurp(user.getCurp());
                    clienteDTO.setTelefono(user.getTelefono());
                    clienteDTO.setCorreo(user.getCorreo());
                    clienteDTO.setEliminado(user.getEliminado());
                    clienteDTO.setCp(user.getCp());
                    clienteDTO.setEstado(user.getEstado());
                    clienteDTO.setCiudad(user.getCiudad());
                    clienteDTO.setColonia(user.getColonia());
                    clienteDTO.setCalle(user.getCalle());
                    clienteDTO.setExt(user.getExt());
                    clienteDTO.setInterior(user.getInterior());
                    clienteDTO.setAuto(user.getAuto());
                    clienteDTO.setInicio_credito(user.getInicio_credito());
                    clienteDTO.setFin_credito(user.getFin_credito());
                    clienteDTO.setMensualidad(user.getMensualidad());
                    return clienteDTO;
                 }).collect(Collectors.toList());
                 
            ListaDTO listaDTO = new ListaDTO((int) userPage.getTotalElements(), page, limit, clientes);

            return new RespuestaDTO<>(200, "Clientes obtenidos correctamenete.", listaDTO);

        } catch (Exception e) {
            throw new ErrorServidor("Error interno del servidor.");
        }
    }

    /**
     * SErviio que actualiza un cliente parcialmente.
     */
    @Override
    public RespuestaDTO<ClienteCreacionDTO> actualizaClienteParcial(UUID id, ClienteDTO clienteDTO){

        try {
            return clienteRepositorio.findById(id).map(user ->{
                if (clienteDTO.getNombre() != null) {
                    user.setNombre(clienteDTO.getNombre());
                }
                if (clienteDTO.getApellidoP() != null) {
                    user.setApellidoP(clienteDTO.getApellidoP());
                }
                if (clienteDTO.getApellidoM() != null) {
                    user.setApellidoM(clienteDTO.getApellidoM());
                }
                if (clienteDTO.getNacimiento() != null) {
                    user.setNacimiento(clienteDTO.getNacimiento());
                }
                if (clienteDTO.getRfc() != null) {
                    user.setRfc(clienteDTO.getRfc());
                }
                if (clienteDTO.getCurp() != null) {
                    user.setCurp(clienteDTO.getCurp());
                }
                if (clienteDTO.getTelefono() != null) {
                    user.setTelefono(clienteDTO.getTelefono());
                }
                if (clienteDTO.getCorreo() != null) {
                    user.setCorreo(clienteDTO.getCorreo());
                }
                if (clienteDTO.getEliminado() != null) {
                    user.setEliminado(clienteDTO.getEliminado());
                }
                if (clienteDTO.getCp() != null) {
                    user.setCp(clienteDTO.getCp());
                }
                if (clienteDTO.getEstado() != null) {
                    user.setEstado(clienteDTO.getEstado());
                }
                if (clienteDTO.getCiudad() != null) {
                    user.setCiudad(clienteDTO.getCiudad());
                }
                if (clienteDTO.getColonia() != null) {
                    user.setColonia(clienteDTO.getColonia());
                }
                if (clienteDTO.getCalle() != null) {
                    user.setCalle(clienteDTO.getCalle());
                }
                if (clienteDTO.getExt() != null) {
                    user.setExt(clienteDTO.getExt());
                }
                if (clienteDTO.getInterior() != null) {
                    user.setInterior(clienteDTO.getInterior());
                }
                if (clienteDTO.getAuto() != null) {
                    user.setAuto(clienteDTO.getAuto());
                }
                if (clienteDTO.getInicio_credito() != null) {
                    user.setInicio_credito(clienteDTO.getInicio_credito());
                }
                if (clienteDTO.getFin_credito() != null) {
                    user.setFin_credito(clienteDTO.getFin_credito());
                }
                if (clienteDTO.getMensualidad() != null) {
                    user.setMensualidad(clienteDTO.getMensualidad());
                }

                user = clienteRepositorio.save(user);

                return new RespuestaDTO<ClienteCreacionDTO>(201,"Cliente Actualizado.", null);


            }).orElseThrow(() -> new RecursoNoEncontrado("Cliente no encontrado."));
        } catch (Exception e) {
           throw new ErrorServidor("Error interno del servidor.");
        }
    }

    /**
     * SErvicio que elimina un cliente.
     */
    @Override
        public RespuestaDTO<EliminarClienteDTO> eliminarCliente(UUID id) {
        try {
            Clientes cliente = clienteRepositorio.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontrado("Usuario no Encontrado"));

            cliente.setEliminado(true);

            cliente = clienteRepositorio.save(cliente);

            EliminarClienteDTO eliminado = new EliminarClienteDTO();
            eliminado.setEliminado(cliente.getEliminado());

            return new RespuestaDTO<EliminarClienteDTO>(200, "Usuario eliminado", eliminado);
        } catch (RecursoNoEncontrado e) {
            throw new RecursoNoEncontrado("Usuario no encontrado");
        } catch (Exception e) {
            throw new ErrorServidor("Error interno del servidor.");
        }
    }
    
}
