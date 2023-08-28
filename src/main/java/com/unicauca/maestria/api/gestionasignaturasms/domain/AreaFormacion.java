package com.unicauca.maestria.api.gestionasignaturasms.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "areas_formacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaFormacion {

    @Id
    @Column(name = "ID_AREA_FORMACION")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAreaFormacion;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

//    @OneToMany(mappedBy = "areaFormacion", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "areaFormacion" })
//    private List<Asignatura> asignaturas;
}
