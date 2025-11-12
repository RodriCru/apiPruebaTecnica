package com.example.prueba.servicios;

import com.example.prueba.dtos.*;
import com.example.prueba.dtos.vehiculos.*;
import java.util.UUID;

public interface VehiculoServices {

    RespuestaDTO<ListaVehiculosDTO> obtenerVehiculos(int page, int limite, String sort, String order, String estatus, Boolean vendido, Boolean eliminado);
    
    RespuestaDTO<DatosVehiculo> obtenerVehiculoPorID(UUID id);

    RespuestaDTO<VehiculoCreacion> registrarVehiculo(VehiculoDTO vehiculoDTO);

    RespuestaDTO<VehiculoCreacion> actualizaVehiculoParcial(UUID id, DatosVehiculo datosVehiculo);

    RespuestaDTO<VehiculoCreacion> actualizaVehiculoCompleto(UUID id, VehiculoDTO vehiculoDTO);

    RespuestaDTO<VehiculoEliminadoDTO> eliminaVehiculo(UUID id);

    RespuestaDTO<VehiculoEliminadoDTO> restaurarVehiculo(UUID id);

} 
