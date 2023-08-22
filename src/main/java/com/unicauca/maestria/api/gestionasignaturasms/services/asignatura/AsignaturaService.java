package com.unicauca.maestria.api.gestionasignaturasms.services.asignatura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.AsignaturaCrearDto;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.AsignaturaListarDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface AsignaturaService {

    public AsignaturaListarDto crear(AsignaturaCrearDto asignatura, BindingResult result) throws JsonProcessingException;

    public AsignaturaListarDto editar(Long id, AsignaturaCrearDto asignatura, BindingResult result);

    public AsignaturaListarDto buscarPorId(Long id);

    public AsignaturaCrearDto buscarPorIdCompleto(Long id);

    public List<AsignaturaListarDto> buscarTodo();

}
