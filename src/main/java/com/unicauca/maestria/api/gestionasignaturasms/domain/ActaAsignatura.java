package com.unicauca.maestria.api.gestionasignaturasms.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicauca.maestria.api.gestionasignaturasms.domain.archivos.Acta;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ACTA_ASIGNATURA", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ID_ACTA", "ID_ASIGNATURA"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActaAsignatura {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ACTA")
    private Acta acta;

    @ManyToOne
    @JoinColumn(name = "ID_ASIGNATURA")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "actasAsignaturas" })
    private Asignatura asignatura;


    @Column(name = "IS_ACTA_ASIGNATURA")
    private Boolean isActaAsignatura;

}
