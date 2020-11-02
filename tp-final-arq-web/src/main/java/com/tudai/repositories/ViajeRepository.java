package com.tudai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tudai.entities.Viaje;
import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
	
	public List<ReporteUsuMasViajes> getReporteConMasViajes();
	
	public List<ReporteConMasZonas> getReporteConZona();
	
}