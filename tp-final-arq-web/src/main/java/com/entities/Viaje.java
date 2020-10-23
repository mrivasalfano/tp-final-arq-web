package com.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Viaje {

	private int id;
	private String nombre;
	private String destino;
	private Date fechaInicio;
	private Date fechaFin;
	private String descripcionBreve;
	private List<Plan> planes;
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
	
	
}
