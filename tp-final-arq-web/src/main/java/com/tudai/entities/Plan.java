package com.tudai.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.tudai.utils.Views;

import lombok.Data;
import lombok.ToString;

@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@Data
public class Plan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonView(Views.PlanConIdViaje.class)
	protected int id;
	@Column
//    @JsonView(Views.PlanConIdViaje.class)
	protected String nombre;
	@Column
//    @JsonView(Views.PlanConIdViaje.class)
	protected Date fechaInicio;
	@Column
//    @JsonView(Views.PlanConIdViaje.class)
	protected Date fechaFin;
	@Column
//    @JsonView(Views.PlanConIdViaje.class)
	protected String codigoReserva;
	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JsonIgnore
//    @JsonView(Views.PlanConIdViaje.class)
	protected Viaje viaje;
	
	@Column
	protected int idViaje;
	
	public Plan() {
		super();
	}
	
	public Plan( String nombre, Date fechaInicio, Date fechaFin, String codigoReserva, Viaje v) {
		super();
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.codigoReserva = codigoReserva;
		this.viaje = v;
		this.idViaje = v.getId();
	}


}
