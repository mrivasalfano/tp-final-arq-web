package com.tudai.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String nombre;
	@Column
	private String clave; 
	@Column
	private boolean isAdmin;
	
	public Usuario() {
		
	}
	
	public Usuario(String nombre, String clave){
		this.nombre = nombre;
		this.clave = clave;
		this.isAdmin = false;
	}
	
	public void makeAdmin() {
		this.isAdmin = true;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
	
}
