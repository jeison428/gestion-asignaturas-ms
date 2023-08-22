package com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente;

import com.unicauca.maestria.api.gestionasignaturasms.common.enums.msestudiantedocente.EscalafonDocente;
import com.unicauca.maestria.api.gestionasignaturasms.common.enums.msestudiantedocente.EstadoPersona;
import com.unicauca.maestria.api.gestionasignaturasms.common.enums.msestudiantedocente.TipoVinculacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data   @AllArgsConstructor
@Entity @Table(name = "docentes")
public class Docente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_persona")
	private	Persona persona;
	
	@Column(unique = true)
	private String codigo;
	
	private String facultad;
	
	private String departamento;
	
	@Enumerated(EnumType.STRING)
	private TipoVinculacion tipoVinculacion;
	
	@Enumerated(EnumType.STRING)
	private EscalafonDocente escalafon;
	
	private String observacion;
	
	@Enumerated(EnumType.STRING)
	private EstadoPersona estado;
	
	
	@OneToMany(mappedBy = "docente",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Titulo> titulos;
	
	public Docente() {
		titulos = new ArrayList<>();
		estado = EstadoPersona.ACTIVO;
	}
	public void agregarTitulo(Titulo titulo) {
		titulo.setDocente(this);
		titulos.add(titulo);
	}
		
	
	public void setTitulos(List<Titulo> titulos) {
		this.titulos.clear();
		titulos.forEach(this::agregarTitulo);
	}

}
