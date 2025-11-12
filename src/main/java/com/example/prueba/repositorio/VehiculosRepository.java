package com.example.prueba.repositorio;

import com.example.prueba.modelos.Vehiculos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculos, UUID> {

    /**
     * Busca vehículos aplicando filtros por marca, modelo, estatus, vendido y eliminado.
     */
    @Query("SELECT v FROM Vehiculos v WHERE " +
           "(LOWER(v.marca) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(v.modelo) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
           "(:estatus IS NULL OR v.estatus = :estatus) AND " +
           "(:vendido IS NULL OR v.vendido = :vendido) AND " +
           "(:eliminado IS NULL OR v.eliminado = :eliminado)")
    Page<Vehiculos> searchVehiculos(
        @Param("q") String query,
        @Param("estatus") String estatus,
        @Param("vendido") Boolean vendido,
        @Param("eliminado") Boolean eliminado,
        Pageable pageable
    );

    /**
     * Busca los vehículos que han sido eliminados lógicamente.
     */
    List<Vehiculos> findByEliminado(boolean eliminado);

    /**
     * Filtros combinados comunes.
     */
    Page<Vehiculos> findByEstatusAndVendidoAndEliminado(
        String estatus, boolean vendido, boolean eliminado, Pageable pageable
    );

    Page<Vehiculos> findByEstatusAndEliminado(
        String estatus, boolean eliminado, Pageable pageable
    );

    Page<Vehiculos> findByVendidoAndEliminado(
        boolean vendido, boolean eliminado, Pageable pageable
    );

    Page<Vehiculos> findByEstatusAndVendido(
        String estatus, boolean vendido, Pageable pageable
    );

    Page<Vehiculos> findByEstatus(
        String estatus, Pageable pageable
    );

    Page<Vehiculos> findByVendido(
        boolean vendido, Pageable pageable
    );

    Page<Vehiculos> findByEliminado(
        boolean eliminado, Pageable pageable
    );
}

