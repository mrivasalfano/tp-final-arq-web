package com.tudai.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Viaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String nombre;
	@Column
	private String destino;
	@Column
	private Date fechaInicio;
	@Column
	private Date fechaFin;
	@Column
	private String descripcionBreve;
	@OneToMany(mappedBy = "viaje")
	private List<Plan> planes;
	@ManyToOne
	private Usuario usuario;
	
	public Viaje() {
		super();
		this.planes = new ArrayList<Plan>();
	}
	
	public Viaje(String nombre, String destino, Date fechaInicio, Date fechaFin, String descripcionBreve) {
		this();
		this.nombre = nombre;
		this.destino = destino;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.descripcionBreve = descripcionBreve;
	}
	
	
}
