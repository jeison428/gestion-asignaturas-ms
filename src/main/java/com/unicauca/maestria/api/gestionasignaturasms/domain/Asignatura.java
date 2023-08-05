package com.unicauca.maestria.api.gestionasignaturasms.domain;

import com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente.Docente;
import com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente.LineaInvestigacion;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asignaturas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Asignatura {

    @Id
    @Column(name = "id_asignatura")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignatura;

    @Column(name = "codigo_asignatura", unique = true)
    private Long codigoAsignatura;

    @Column(name = "nombre_asignatura", unique = true)
    private String nombreAsignatura;

    @Column(name = "estado_asignatura")
    private Boolean estadoAsignatura;

    @Column(name = "fecha_aprobacion")
    private Date fechaAprobacion;

    @Column(name = "oficio_facultad")
    private String oficioFacultad;//archivo oficio

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_formacion")
    private AreaFormacion areaFormacion;

    @Column(name = "tipo_asignatura")
    private String tipoAsignatura;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "linea_investigacion")
    private LineaInvestigacion lineaInvestigacionAsignatura;

    @Column(name = "creditos")
    private Integer creditos;

    @Column(name = "objetivo_asignatura")
    private String objetivoAsignatura;

    @Column(name = "contenido_asignatura")
    private String contenidoAsignatura;

    @Column(name = "contenido_programatico")
    private String contenidoProgramatico;//aarchivo pdf

    @Column(name = "microcurriculo")
    private String microcurriculo;//archivo pdf

    @Column(name = "horas_presencial")
    private Integer horasPresencial;

    @Column(name = "horas_no_presencial")
    private Integer horasNoPresencial;

    @Column(name = "horas_total")
    private Integer horasTotal;
}
