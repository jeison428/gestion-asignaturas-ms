package com.unicauca.maestria.api.gestionasignaturasms.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CamposUnicosDto {

    private String nombreAsignatura;
    private Long codigoAsignatura;
}

