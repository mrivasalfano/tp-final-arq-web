package com.tudai.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	protected int id;
	@Column
	protected String nombre;
	@Column
	protected Date fechaInicio;
	@Column
	protected Date fechaFin;
	@Column
	protected String codigoReserva;
	@ManyToOne
//	@ToString.Exclude
//	@JsonIgnore
	protected Viaje viaje;
	
	public Plan() {
		super();
	}
	
	public Plan( String nombre, Date fechaInicio, Date fechaFin, String codigoReserva) {
		super();
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.codigoReserva = codigoReserva;
	}

}
