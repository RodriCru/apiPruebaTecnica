package com.example.prueba.servicios.impl;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.prueba.dtos.*;
import com.example.prueba.dtos.vehiculos.*;
import com.example.prueba.excepciones.*;
import com.example.prueba.modelos.Vehiculos;
import com.example.prueba.repositorio.VehiculosRepository;
import com.example.prueba.servicios.VehiculoServices;

@Service
@RequiredArgsConstructor
public class VehiculoServiciosImpl implements VehiculoServices {

    private final VehiculosRepository vehiculosRepository;

    @Override
    public RespuestaDTO<ListaVehiculosDTO> obtenerVehiculos(
            int page,
            int limite,
            String sort,
            String order,
            String estatus,
            Boolean vendido,
            Boolean eliminado) {
        try {
            Sort.Direction orden = Sort.Direction.fromString(order);
            Sort sortBy = Sort.by(orden, sort);
            Pageable pageable = PageRequest.of(page - 1, limite, sortBy);
            Page<Vehiculos> vehiculoPage;

            // Aplicamos filtros dinámicos según los parámetros recibidos
            if (estatus != null && vendido != null && eliminado != null) {
                vehiculoPage = vehiculosRepository.findByEstatusAndVendidoAndEliminado(
                        estatus, vendido, eliminado, pageable);
            } else if (estatus != null && vendido != null) {
                vehiculoPage = vehiculosRepository.findByEstatusAndVendido(
                        estatus, vendido, pageable);
            } else if (estatus != null && eliminado != null) {
                vehiculoPage = vehiculosRepository.findByEstatusAndEliminado(
                        estatus, eliminado, pageable);
            } else if (vendido != null && eliminado != null) {
                vehiculoPage = vehiculosRepository.findByVendidoAndEliminado(
                        vendido, eliminado, pageable);
            } else if (estatus != null) {
                vehiculoPage = vehiculosRepository.findByEstatus(estatus, pageable);
            } else if (vendido != null) {
                vehiculoPage = vehiculosRepository.findByVendido(vendido, pageable);
            } else if (eliminado != null) {
                vehiculoPage = vehiculosRepository.findByEliminado(eliminado, pageable);
            } else {
                vehiculoPage = vehiculosRepository.findAll(pageable);
            }

            // Validaciones de resultados vacíos
            if (estatus != null && vehiculoPage.isEmpty()) {
                throw new RecursoNoEncontrado("No se encontraron vehículos con el estatus especificado.");
            }

            if (vendido != null && vehiculoPage.isEmpty()) {
                throw new RecursoNoEncontrado("No se encontraron vehículos con el estado de venta especificado.");
            }

            if (eliminado != null && vehiculoPage.isEmpty()) {
                throw new RecursoNoEncontrado("No se encontraron vehículos con el estado de eliminación especificado.");
            }

            // Convertimos las entidades a DTOs
            List<VehiculoDTO> vehiculos = vehiculoPage.getContent().stream()
                    .map(v -> {
                        VehiculoDTO dto = new VehiculoDTO();
                        dto.setId(v.getId());
                        dto.setMarca(v.getMarca());
                        dto.setModelo(v.getModelo());
                        dto.setEstatus(v.getEstatus());
                        dto.setVendido(v.getVendido());
                        dto.setEliminado(v.getEliminado());
                        return dto;
                    }).collect(Collectors.toList());

            ListaVehiculosDTO listaDTO = new ListaVehiculosDTO(
                    (int) vehiculoPage.getTotalElements(),
                    page,
                    limite,
                    vehiculos);

            return new RespuestaDTO<>(200, "Vehículos obtenidos exitosamente.", listaDTO);

        } catch (RecursoNoEncontrado e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorServidor("Error interno del servidor.");
        }
    }

    @Override
    public RespuestaDTO<DatosVehiculo> obtenerVehiculoPorID(UUID id){
        try {
            Vehiculos vehiculos = vehiculosRepository.findById(id)
               .orElseThrow(() -> new RecursoNoEncontrado("El vehiculo no existe o fue eliminado"));

            DatosVehiculo datosVehiculo = new DatosVehiculo();
            datosVehiculo.setMarca(vehiculos.getMarca());
            datosVehiculo.setModelo(vehiculos.getModelo());
            datosVehiculo.setEstatus(vehiculos.getEstatus());
            datosVehiculo.setVendido(vehiculos.getVendido());
            datosVehiculo.setEliminado(vehiculos.getEliminado());

            return new RespuestaDTO<DatosVehiculo>(200, "Vehiculo encontrado", datosVehiculo);
        } catch (RecursoNoEncontrado e) {
            throw new RecursoNoEncontrado("Producto no encontrado.");
        } catch(Exception e){
            throw new ErrorServidor("Error interno del servidor.");
        }
    }

    @Override
    public RespuestaDTO<VehiculoCreacion> registrarVehiculo(VehiculoDTO vehiculoDTO){
        try {
            if(vehiculoDTO.getMarca() == null
                || vehiculoDTO.getModelo() == null
                || vehiculoDTO.getEstatus() == null){
                throw new ErrorCreacion("Datos invalidos o vacios.");
            }

            Vehiculos vehiculo = new Vehiculos();
            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setEstatus(vehiculoDTO.getEstatus());
            vehiculo.setVendido(vehiculoDTO.getVendido());
            vehiculo.setEliminado(vehiculoDTO.getEliminado());

            vehiculo = vehiculosRepository.save(vehiculo);

            VehiculoCreacion nuevoVehiculo = new VehiculoCreacion();

            nuevoVehiculo.setId(vehiculo.getId());
            nuevoVehiculo.setMarca(vehiculo.getMarca());
            nuevoVehiculo.setModelo(vehiculo.getModelo());

            return new RespuestaDTO<VehiculoCreacion>(201, "Vehiculo registrado exitosamente",nuevoVehiculo);

        } catch (ErrorCreacion e) {
            throw new ErrorCreacion("Datos invalidos.");
        } catch (Exception e){
            throw new ErrorServidor("Error interno del servidor.");
        }
    }

    public RespuestaDTO<VehiculoCreacion> actualizaVehiculoParcial(UUID id, DatosVehiculo datosVehiculo){
        try {
            return vehiculosRepository.findById(id).map(vehiculo ->{
            if (datosVehiculo.getMarca() != null) {
                 vehiculo.setMarca(datosVehiculo.getMarca());
            }

            if (datosVehiculo.getModelo() != null) {
                vehiculo.setModelo(datosVehiculo.getModelo());
            }

            if (datosVehiculo.getEstatus() != null) {
                vehiculo.setEstatus(datosVehiculo.getEstatus());
            }

            if (datosVehiculo.getVendido() != null) {
                vehiculo.setVendido(datosVehiculo.getVendido());
            }

            if (datosVehiculo.getEliminado() != null) {
                vehiculo.setEliminado(datosVehiculo.getEliminado());
            }

            vehiculo = vehiculosRepository.save(vehiculo);

            return new RespuestaDTO<VehiculoCreacion>(200, "Vehiculo actualizado.", null);
            }).orElseThrow(() -> new RecursoNoEncontrado("No se encontró el recurso."));
        } catch (Exception e) {
            throw new ErrorServidor("Error interno del servidor al actualizar el vehículo.");
        }
    }
    
    @Override
    public RespuestaDTO<VehiculoCreacion> actualizaVehiculoCompleto(UUID id, VehiculoDTO vehiculoDTO){
        try {
            Vehiculos vehiculo = vehiculosRepository.findById(id)
               .orElseThrow(() -> new RecursoNoEncontrado("Vehiculo no encontrado."));
            if(vehiculoDTO.getMarca() == null
                || vehiculoDTO.getModelo() == null
                || vehiculoDTO.getEstatus() == null){
                throw new ErrorCreacion("Datos invalidos o vacios.");
            }

            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setEstatus(vehiculoDTO.getEstatus());
            vehiculo.setVendido(vehiculoDTO.getVendido());
            vehiculo.setEliminado(vehiculoDTO.getEliminado());

            vehiculo = vehiculosRepository.save(vehiculo);

            VehiculoCreacion nuevoVehiculo = new VehiculoCreacion();

            nuevoVehiculo.setId(vehiculo.getId());
            nuevoVehiculo.setMarca(vehiculo.getMarca());
            nuevoVehiculo.setModelo(vehiculo.getModelo());

            return new RespuestaDTO<VehiculoCreacion>(200, "Vehiculo actualizado exitosamente",nuevoVehiculo);

        } catch (ErrorCreacion e) {
            throw new ErrorCreacion("Datos invalidos.");
        } catch (Exception e){
            throw new ErrorServidor("Error interno del servidor.");
        }
    }

    @Override
    public RespuestaDTO<VehiculoEliminadoDTO> eliminaVehiculo(UUID id){
        try {
            Vehiculos vehiculo = vehiculosRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Vehiculo no encontrado."));

                vehiculo.setEliminado(true);

                vehiculo = vehiculosRepository.save(vehiculo);

                VehiculoEliminadoDTO eliminado = new VehiculoEliminadoDTO();
                eliminado.setMarca(vehiculo.getMarca());
                eliminado.setModelo(vehiculo.getModelo());
                eliminado.setEliminado(vehiculo.getEliminado());

                return new RespuestaDTO<VehiculoEliminadoDTO>(200, "Auto eliminado correctamente", eliminado);
        } catch (RecursoNoEncontrado e) {
            throw new RecursoNoEncontrado("Usuario no encontrado");
        } catch (Exception e) {
            throw new ErrorServidor("Error interno del servidor.");
        }
    }


    @Override
    public RespuestaDTO<VehiculoEliminadoDTO> restaurarVehiculo(UUID id){
        try {
            Vehiculos vehiculo = vehiculosRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Vehiculo no encontrado."));

                vehiculo.setEliminado(false);

                vehiculo = vehiculosRepository.save(vehiculo);

                VehiculoEliminadoDTO eliminado = new VehiculoEliminadoDTO();
                eliminado.setMarca(vehiculo.getMarca());
                eliminado.setModelo(vehiculo.getModelo());
                eliminado.setEliminado(vehiculo.getEliminado());

                return new RespuestaDTO<VehiculoEliminadoDTO>(200, "Auto restaurado correctamente", eliminado);
        } catch (RecursoNoEncontrado e) {
            throw new RecursoNoEncontrado("Usuario no encontrado");
        } catch (Exception e) {
            throw new ErrorServidor("Error interno del servidor.");
        }
    }
}
