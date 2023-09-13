package com.unicauca.maestria.api.gestionasignaturasms.repositories;

import com.unicauca.maestria.api.gestionasignaturasms.domain.ActaAsignatura;
import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActaAsignaturaRepository extends JpaRepository<ActaAsignatura, Long> {

    @Query(
            value = "SELECT * " +
                    "FROM ACTA_ASIGNATURA AA " +
                    "WHERE AA.IS_ACTA_ASIGNATURA = :estado",
            nativeQuery = true
    )
    public List<ActaAsignatura> findByEstado(@Param("estado") Boolean estado);
}
