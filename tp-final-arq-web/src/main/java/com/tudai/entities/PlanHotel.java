package com.tudai.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class PlanHotel extends Plan{

	@Column
	private String direccion;
	@Column
	private int piso;
	@Column
	private String habitacion;
	
	public PlanHotel() {
		super();
	}

	public PlanHotel(String nombre, Timestamp fechaInicio, Timestamp fechaFin, String codigoReserva, String direccion,
			int piso, String habitacion) {
		super(nombre, fechaInicio, fechaFin, codigoReserva);
		this.direccion = direccion;
		this.piso = piso;
		this.habitacion = habitacion;
	}
	
}
