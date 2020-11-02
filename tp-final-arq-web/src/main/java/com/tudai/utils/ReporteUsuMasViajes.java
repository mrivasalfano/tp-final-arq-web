package com.tudai.utils;

import lombok.Data;

@Data
public class ReporteUsuMasViajes {

	private String nomUsu;
	private Long total;
	
	public ReporteUsuMasViajes(String nomUsu, Long item) {
		super();
		this.nomUsu = nomUsu;
		this.total = item;
	}
}
