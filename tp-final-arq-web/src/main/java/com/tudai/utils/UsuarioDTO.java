package com.tudai.utils;

import lombok.Data;

@Data
public class UsuarioDTO {
	int id;
	String nombre;
	
	public UsuarioDTO(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
}
