package com.unicauca.maestria.api.gestionasignaturasms.repositories;

import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    public boolean existsByNombreAsignatura(String nombre);
    public boolean existsByCodigoAsignatura(Long codigoAsignatura);
}
