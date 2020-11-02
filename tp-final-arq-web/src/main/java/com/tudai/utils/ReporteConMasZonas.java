package com.tudai.utils;

import lombok.Data;

@Data
public class ReporteConMasZonas {

	private String zonaGeografica;
	private Long total;
	
	public ReporteConMasZonas(String zonaGeografica, Long item) {
		super();
		this.zonaGeografica = zonaGeografica;
		this.total = item;
	}
}
