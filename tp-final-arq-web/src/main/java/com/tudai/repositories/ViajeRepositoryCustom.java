package com.tudai.repositories;

import java.util.List;

import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;

public interface ViajeRepositoryCustom {
	
	public List<ReporteUsuMasViajes> getReporteConMasViajes();

	public List<ReporteConMasZonas> getReporteConZona();

}
