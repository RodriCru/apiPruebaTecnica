package com.example.prueba.controlador;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.prueba.dtos.RespuestaDTO;
import com.example.prueba.servicios.impl.ArchivoService;

import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/archivos")
@RequiredArgsConstructor
public class ArchivoControlador {
    
    private final ArchivoService archivoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RespuestaDTO<String>> subirArchivo(@RequestParam("file") MultipartFile file) {
        RespuestaDTO<String> respuesta = archivoService.procesarArchivo(file);
        return ResponseEntity.status(respuesta.getCodigo()).body(respuesta);
    }
}
