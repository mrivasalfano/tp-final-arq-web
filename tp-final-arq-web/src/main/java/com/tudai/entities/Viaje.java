package com.tudai.entities;

import java.lang.annotation.Repeatable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.tudai.utils.Views;

import lombok.Data;

@Entity
@Data
public class Viaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@JsonView(Views.PlanConIdViaje.class)
	private int id;
	@Column
//	@JsonView(Views.PlanConIdViaje.class)
	private String nombre;
	@Column
//	@JsonView(Views.SinPlanes.class)
	private String destino;
	@Column
//	@JsonView(Views.SinPlanes.class)
	private Date fechaInicio;
	@Column
//	@JsonView(Views.SinPlanes.class)
	private Date fechaFin;
	@Column
//	@JsonView(Views.SinPlanes.class)
	private String descripcionBreve;
//	@OneToMany(mappedBy = "viaje", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@OneToMany()
	@OneToMany(mappedBy = "viaje", fetch = FetchType.LAZY)
	private List<Plan> planes;
	@ManyToOne
//	@JsonView(Views.SinPlanes.class)
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
	
	public void addPlan(Plan p) {
		this.planes.add(p);
	}
	
//	public boolean addPlan(Plan p) {
//		//check en el repo si ya existe
//		if((p.getFechaInicio().compareTo(this.getFechaInicio()) >= 0) &&
//			(p.getFechaFin().compareTo(this.getFechaFin()) <=0) ) {
//				p.setViaje(this);
//				return this.planes.add(p);
//		}
//		return false;
//	}
	
}
