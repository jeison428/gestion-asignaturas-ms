package com.unicauca.maestria.api.gestionasignaturasms.domain.archivos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "OFICIOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Oficio {
    @Id
    @Column(name = "ID_OFICIO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOficio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_DOC_MAESTRIA")
    private DocumentoMaestria idDocMaestria;

    @Column(name = "NUMOFICIO")
    private Long numeroOficio;

    @Column(name = "FECHAOFICIO")
    private Date fechaOficio;

    @Column(name = "ASUNTOOFI")
    private String asuntoOfi;

}
