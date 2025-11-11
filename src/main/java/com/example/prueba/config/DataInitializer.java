package com.example.prueba.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.prueba.modelos.Empleado;
import com.example.prueba.repositorio.EmpleadoRepositorio;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EmpleadoRepositorio empleadoRepositorio;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        inicializarUsuarioAdmin();
    }

    private void inicializarUsuarioAdmin() {
        // Verificar si ya existe el usuario admin
        Optional<Empleado> adminExistente = empleadoRepositorio.findByUsuario("admin");
        
        if (adminExistente.isEmpty()) {
            log.info("Creando usuario administrador por defecto...");
            
            Empleado admin = new Empleado();
            admin.setNombre("Admin");
            admin.setApellidoP("Sistema");
            admin.setApellidoM("Principal");
            admin.setNacimiento(LocalDate.of(1990, 1, 1));
            admin.setRfc("ADMI900101XXX");
            admin.setCurp("ADMI900101HDFXXX00");
            admin.setTelefono("5512345678");
            admin.setCorreo("admin@empresa.com");
            admin.setUsuario("admin");
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setEliminado(false);
            admin.setCp("01000");
            admin.setEstado("Ciudad de México");
            admin.setCiudad("CDMX");
            admin.setColonia("Centro");
            admin.setCalle("Av Principal");
            admin.setExt("123");
            admin.setInterior("A");

            empleadoRepositorio.save(admin);
            log.info("Usuario administrador creado exitosamente");
        } else {
            log.info("Usuario administrador ya existe");
        }
    }
}
