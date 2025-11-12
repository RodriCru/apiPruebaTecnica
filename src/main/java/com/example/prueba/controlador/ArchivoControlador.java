package com.example.prueba.controlador;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.prueba.dtos.RespuestaDTO;
import com.example.prueba.servicios.impl.ArchivoService;

import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

/**
 * Clase que define los endpoits para la carga de archivos.
 */
@RestController
@RequestMapping("/api/archivos")
@RequiredArgsConstructor
public class ArchivoControlador {
    
    private final ArchivoService archivoService;

    /**
     * Recibe un archivo excel o pdf y extrae la curp o el RFC
     * @param file archivo excel o PDF
     * @return Si lo encuentra regresa la palabra, en caso contraio arroja error.
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RespuestaDTO<String>> subirArchivo(@RequestParam("file") MultipartFile file) {
        RespuestaDTO<String> respuesta = archivoService.procesarArchivo(file);
        return ResponseEntity.status(respuesta.getCodigo()).body(respuesta);
    }
}
