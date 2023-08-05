package com.unicauca.maestria.api.gestionasignaturasms.dtos;

import com.unicauca.maestria.api.gestionasignaturasms.common.enums.msestudiantedocente.TipoAsignatura;
import com.unicauca.maestria.api.gestionasignaturasms.domain.AreaFormacion;
import com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente.LineaInvestigacion;
import lombok.*;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignaturaCrearDto {

    @NotNull
    @Positive
    private Long codigoAsignatura;

    @NotNull
    @Size(min = 1, max = 50)
    private String nombreAsignatura;

    @NotNull
    private Boolean estadoAsignatura;

    @NotNull
    private Date fechaAprobacion;

    @NotNull
    @Size(min = 1)
    private String oficioFacultad;//archivo oficio

    @NotNull
    @Valid
    private AreaFormacion areaFormacion;

    @NotNull
    @Valid
    private LineaInvestigacion lineaInvestigacionAsignatura;

    @NotNull
    private TipoAsignatura tipoAsignatura;

    @NotNull
    @Positive
    private Integer creditos;

    @NotNull
    @Size(min = 1)
    private String objetivoAsignaturaa;

    @NotNull
    @Size(min = 1)
    private String contenidoAsignatura;

    @NotNull
    @Size(min = 1)
    private String contenidoProgramatico;//aarchivo pdf

    @NotNull
    @Size(min = 1)
    private String microcurriculo;//archivo pdf

    @NotNull
    @PositiveOrZero
    private Integer horasPresencial;

    @NotNull
    @PositiveOrZero
    private Integer horasNoPresencial;

    @NotNull
    @Positive
    private Integer horasTotal;
}
