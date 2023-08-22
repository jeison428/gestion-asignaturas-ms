package com.unicauca.maestria.api.gestionasignaturasms.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.AsignaturaCrearDto;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.AsignaturaListarDto;
import com.unicauca.maestria.api.gestionasignaturasms.services.asignatura.AsignaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    private final AsignaturaService service;

    @PostMapping
    public ResponseEntity<AsignaturaListarDto> crear(@Valid @RequestBody AsignaturaCrearDto asignatura, BindingResult result) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(asignatura, result));
    }

    @GetMapping
    public ResponseEntity<List<AsignaturaListarDto>> listarTodo(){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaListarDto> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @GetMapping("/completa/{id}")
    public ResponseEntity<AsignaturaCrearDto> buscarPorIdCompleta(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorIdCompleto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignaturaListarDto> editarAsignatura(@PathVariable Long id, @Valid @RequestBody AsignaturaCrearDto asignatura, BindingResult result){
        return ResponseEntity.status(HttpStatus.OK).body(service.editar(id, asignatura, result));
    }
}
