package com.tudai.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import lombok.Data;

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
	protected Timestamp fechaInicio;
	@Column
	protected Timestamp fechaFin;
	@Column
	protected String codigoReserva;
	@ManyToOne
	protected Viaje viaje;
	
	public Plan() {
		super();
	}
	
	public Plan( String nombre, Timestamp fechaInicio, Timestamp fechaFin, String codigoReserva) {
		super();
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.codigoReserva = codigoReserva;
	}

}
