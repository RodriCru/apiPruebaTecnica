package com.example.prueba.servicios;

import com.example.prueba.dtos.respuestaDTO;
import com.example.prueba.dtos.empleados.*;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface empleadoServices {
    respuestaDTO<empleadoCreacionDTO> creaEmpleado(creaEmpleadoDTO creaEmpleadoDTO);

    respuestaDTO<empleadoCreacionDTO>actualizaCompleto(UUID id, empleadoDTO empleadoDTO);

    //respuestaDTO<datosEmpleadoDTO> misdatos(String user);
}
