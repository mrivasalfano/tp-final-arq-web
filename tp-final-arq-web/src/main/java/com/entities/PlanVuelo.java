package com.entities;

import java.sql.Timestamp;

public class PlanVuelo extends Plan{

	private int numVuelo;
	private String compania;
	private int tiempoEscalaMin;
	private String tipoAvion;
	private String aeropuestoSalida;
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
