package com.unicauca.maestria.api.gestionasignaturasms.domain;

import com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente.Docente;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DOCENTE_ASIGNATURA", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ID_DOCENTE", "ID_ASIGNATURA"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocenteAsignatura {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_DOCENTE")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "ID_ASIGNATURA")
    private Asignatura asignatura;

    private String tipo;
}
