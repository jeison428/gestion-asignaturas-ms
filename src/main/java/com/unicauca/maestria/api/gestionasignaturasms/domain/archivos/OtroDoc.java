package com.unicauca.maestria.api.gestionasignaturasms.domain.archivos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "OTROS_DOC")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OtroDoc {

    @Id
    @Column(name = "IDOTRO_DOC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOtroDoc;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_DOC_MAESTRIA")
    private DocumentoMaestria idDocMaestria;

    @Column(name = "NOMBREDOCUMENTO")
    private String nombreDocumento;

    @Column(name = "VERSIONDOC")
    private Long versionDoc;

    @Column(name = "DESCRIPCION_DOCUMENTO")
    private String descripcionDocumento;

//    @OneToMany(mappedBy = "contenidoProgramatico", cascade = CascadeType.ALL)
////    @JsonBackReference
//    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "contenidoProgramatico" })
//    private List<Asignatura> asignaturasContenidoProgramatico;
//
//    @OneToMany(mappedBy = "microcurriculo", cascade = CascadeType.ALL)
////    @JsonBackReference
//    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "microcurriculo" })
//    private List<Asignatura> asignaturasMicrocurriculo;

}
