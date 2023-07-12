package com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString   @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "linea_investigacion")
public class LineaInvestigacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    private String titulo;

    private String categoria;


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
