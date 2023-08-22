package com.unicauca.maestria.api.gestionasignaturasms.domain.archivos;

import com.unicauca.maestria.api.gestionasignaturasms.domain.ActaAsignatura;
import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ACTAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Acta {

    @Id
    @Column(name = "ID_ACTA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_DOC_MAESTRIA")
    private DocumentoMaestria idDocMaestria;

    @Column(name = "NUMERO_ACTA")
    private Long numeroActa;

    @Column(name = "FECHA_ACTA")
    private Date fechaActa;

}
