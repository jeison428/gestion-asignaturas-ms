package com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "linea_investigacion")
public class LineaInvestigacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    private String titulo;

    private String categoria;

    @OneToMany(mappedBy = "lineaInvestigacionAsignatura", fetch = FetchType.LAZY)
    private List<Asignatura> asignaturas;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LineaInvestigacion other = (LineaInvestigacion) obj;
        return Objects.equals(id, other.id);
    }


}
