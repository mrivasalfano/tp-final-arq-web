package com.tudai.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.ToString;

/**
 * Es un tipo de plan porque incluye sus datos pero agrega
 * otros que corresponden a un vuelo.
 */
@Entity
@Data
@ToString(callSuper = true)
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
			int tiempoEscalaMin, String tipoAvion, String aeropuertoSalida, String aeropuertoLlegada, Viaje idViaje) {
		super(nombre, fechaInicio, fechaFin, codigoReserva, idViaje);
		this.numVuelo = numVuelo;
		this.compania = compania;
		this.tiempoEscalaMin = tiempoEscalaMin;
		this.tipoAvion = tipoAvion;
		this.aeropuertoSalida = aeropuertoSalida;
		this.aeropuertoLlegada = aeropuertoLlegada;
	}

//	@Override
//	public String toString() {
//		return "PlanVuelo [numVuelo=" + numVuelo + ", compania=" + compania + ", tiempoEscalaMin=" + tiempoEscalaMin
//				+ ", tipoAvion=" + tipoAvion + ", aeropuertoSalida=" + aeropuertoSalida + ", aeropuertoLlegada="
//				+ aeropuertoLlegada + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
//				+ ", codigoReserva=" + codigoReserva + ", idViaje=" + idViaje + "]";
//	}
	
	
	
	
}
