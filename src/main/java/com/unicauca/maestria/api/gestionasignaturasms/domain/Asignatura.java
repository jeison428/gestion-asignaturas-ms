package com.unicauca.maestria.api.gestionasignaturasms.domain;

import com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente.Docente;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ASIGNATURAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Asignatura {

    @Id
    @Column(name = "ID_ASIGNATURA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignatura;

//    private DocenteAsignaatura idDocente;

//    private Long idCurso;

    @Column(name = "CODIGO_ASIGNATURA")
    private Long codigoAsignatura;

    @Column(name = "NOMBRE_ASIGNATURA")
    private String nombreAsignatura;

    @Column(name = "ESTADO_ASIGNATURA")
    private String estadoAsignatura;

    @Column(name = "FECHA_APROBACION")
    private Date fechaAprobacion;

    @Column(name = "ID_ASIGNATURA")
    private Long oficioFacultad;

    @Column(name = "ID_ASIGNATURA")
    private Long areaFormacion;

    @Column(name = "ID_ASIGNATURA")
    private String tipoAsignatura;

    @Column(name = "ID_ASIGNATURA")
    private Long lineaInvestigacionAsignatura;

    @Column(name = "ID_ASIGNATURA")
    private Long creditos;

    @Column(name = "ID_ASIGNATURA")
    private String objetivoAsignaturaa;

    @Column(name = "ID_ASIGNATURA")
    private String contenidoAsignatura;

    @Column(name = "ID_ASIGNATURA")
    private String contenidoProgramatico;

    @Column(name = "ID_ASIGNATURA")
    private Long microcurriculo;

    @Column(name = "ID_ASIGNATURA")
    private Long horasPresencial;

    @Column(name = "ID_ASIGNATURA")
    private Long horasNoPresencial;

    @Column(name = "ID_ASIGNATURA")
    private Long horasTotal;
}
