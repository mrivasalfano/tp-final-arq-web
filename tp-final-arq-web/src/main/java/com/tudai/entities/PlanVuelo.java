package com.tudai.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class PlanVuelo extends Plan{

	@Column
	private int numVuelo;
	@Column
	private String compania;
	@Column
	private int tiempoEscalaMin;
	@Column
	private String tipoAvion;
	@Column
	private String aeropuertoSalida;
	@Column
	private String aeropuertoLlegada;
	
	public PlanVuelo() {
		super();
	}
	
	public PlanVuelo(String nombre, int numVuelo, String compania, Date fechaInicio, Date fechaFin, String codigoReserva,
			int tiempoEscalaMin, String tipoAvion, String aeropuestoSalida, String aeropuestoLlegada, Viaje idViaje) {
		super(nombre, fechaInicio, fechaFin, codigoReserva, idViaje);
		this.numVuelo = numVuelo;
		this.compania = compania;
		this.tiempoEscalaMin = tiempoEscalaMin;
		this.tipoAvion = tipoAvion;
		this.aeropuertoSalida = aeropuestoSalida;
		this.aeropuertoLlegada = aeropuestoLlegada;
	}
	
	
	
}
