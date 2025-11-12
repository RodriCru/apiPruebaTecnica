package com.example.prueba.servicios.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.prueba.dtos.RespuestaDTO;
import com.example.prueba.excepciones.ErrorCreacion;
import com.example.prueba.excepciones.ErrorServidor;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

/**
 * Clase encargada de la carga de archivos
 */
@Service
@Slf4j
public class ArchivoService {

    /**
     * Se carga un pdf o excel y debe de extraer un pdf o el rfc
     * @param file archivo excel o pdf
     * @return la palabra extraida.
     */
    public RespuestaDTO<String> procesarArchivo(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new ErrorCreacion("El archivo está vacío.");
            }

            String nombre = file.getOriginalFilename();
            String tipo = file.getContentType();

            // Validar tipo de archivo
            if (tipo == null || (!tipo.equals("application/pdf") &&
                                !tipo.equals("application/vnd.ms-excel") &&
                                !tipo.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
                throw new ErrorCreacion("Tipo de archivo no soportado. Solo se permiten PDF o Excel.");
            }

            String datoExtraido;

            if (tipo.equals("application/pdf")) {
                datoExtraido = extraerDatoDePdf(file);
            } else {
                datoExtraido = extraerDatoDeExcel(file);
            }

            return new RespuestaDTO<>(200, "Archivo procesado correctamente.", datoExtraido);

        } catch (ErrorCreacion e) {
            throw e;
        } catch (Exception e) {
            log.error("Error procesando archivo", e);
            throw new ErrorServidor("Error interno al procesar el archivo.");
        }
    }

    private String extraerDatoDePdf(MultipartFile file) throws IOException {
        // Ejemplo usando Apache PDFBox
        try (org.apache.pdfbox.pdmodel.PDDocument documento = org.apache.pdfbox.pdmodel.PDDocument.load(file.getInputStream())) {
            String texto = new org.apache.pdfbox.text.PDFTextStripper().getText(documento);

            // Buscar CURP o RFC en el texto
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\b[A-Z]{4}\\d{6}[A-Z0-9]{8}\\b").matcher(texto);
            if (matcher.find()) {
                return "CURP/RFC detectado: " + matcher.group();
            }
            return "No se encontró un CURP o RFC en el archivo PDF.";
        }
    }

    private String extraerDatoDeExcel(MultipartFile file) throws IOException {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(file.getInputStream())) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            org.apache.poi.ss.usermodel.Row row = sheet.getRow(0);
            org.apache.poi.ss.usermodel.Cell cell = row.getCell(0);
            String valor = cell.getStringCellValue();

            return "Dato extraído del Excel: " + valor;
        }
    }
}

