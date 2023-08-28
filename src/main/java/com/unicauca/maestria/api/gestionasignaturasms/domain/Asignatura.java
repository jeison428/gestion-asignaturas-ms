package com.unicauca.maestria.api.gestionasignaturasms.domain;

import com.unicauca.maestria.api.gestionasignaturasms.domain.archivos.Acta;
import com.unicauca.maestria.api.gestionasignaturasms.domain.archivos.Oficio;
import com.unicauca.maestria.api.gestionasignaturasms.domain.archivos.OtroDoc;
import com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente.Docente;
import com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente.LineaInvestigacion;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @JoinColumn(name = "oficio_facultad")
    @ManyToOne(cascade = CascadeType.ALL)
    private Oficio oficioFacultad;//archivo oficio

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

    @JoinColumn(name = "contenido_programatico")
    @ManyToOne(cascade = CascadeType.ALL)
    private OtroDoc contenidoProgramatico;//aarchivo pdf

    @JoinColumn(name = "microcurriculo")
    @ManyToOne(cascade = CascadeType.ALL)
    private OtroDoc microcurriculo;//archivo pdf

    @Column(name = "horas_presencial")
    private Integer horasPresencial;

    @Column(name = "horas_no_presencial")
    private Integer horasNoPresencial;

    @Column(name = "horas_total")
    private Integer horasTotal;

    @Transient
    private List<Docente> listaDocentes;

    @Transient
    private List<Acta> listaActas;

    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocenteAsignatura> docentesAsignaturas;

    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActaAsignatura> actasAsignaturas;
}
