package com.unicauca.maestria.api.gestionasignaturasms.domain.archivos;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DOCUMENTO_MAESTRIA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentoMaestria {

    @Id
    @Column(name = "ID_DOC_MAESTRIA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocMaestria;

    @Column(name = "LINK_DOCUMENTO")
    private String linkDocumento;

    @Column(name = "ESTADO")
    private Boolean estado;

}
