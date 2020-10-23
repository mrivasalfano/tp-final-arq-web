package com.entities;

import java.sql.Timestamp;

public abstract class Plan {
	
	protected int id;
	protected String nombre;
	protected Timestamp fechaInicio;
	protected Timestamp fechaFin;
	protected String codigoReserva;
	
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
