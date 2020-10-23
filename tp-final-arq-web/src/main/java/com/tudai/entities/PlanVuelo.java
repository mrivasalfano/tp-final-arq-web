package com.tudai.entities;

import java.sql.Timestamp;

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
	private String aeropuestoSalida;
	@Column
	private String aeropuestoLlegada;
	
	public PlanVuelo() {
		super();
	}
	
	public PlanVuelo(String nombre, int numVuelo, String compania, Timestamp fechaInicio, Timestamp fechaFin, String codigoReserva,
			int tiempoEscalaMin, String tipoAvion, String aeropuestoSalida, String aeropuestoLlegada) {
		super(nombre, fechaInicio, fechaFin, codigoReserva);
		this.numVuelo = numVuelo;
		this.compania = compania;
		this.tiempoEscalaMin = tiempoEscalaMin;
		this.tipoAvion = tipoAvion;
		this.aeropuestoSalida = aeropuestoSalida;
		this.aeropuestoLlegada = aeropuestoLlegada;
	}
	
	
	
}
