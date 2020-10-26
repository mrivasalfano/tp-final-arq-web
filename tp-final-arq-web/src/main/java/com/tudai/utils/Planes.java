package com.tudai.utils;

import java.util.List;

import com.tudai.entities.PlanHotel;
import com.tudai.entities.PlanVuelo;

import lombok.Data;

@Data
public class Planes {
	private List<PlanVuelo> vuelos;
	private List<PlanHotel> hoteles;
	
	public Planes(List<PlanVuelo> vuelos, List<PlanHotel> hoteles) {
		this.vuelos = vuelos;
		this.hoteles = hoteles;
	}
	
}
