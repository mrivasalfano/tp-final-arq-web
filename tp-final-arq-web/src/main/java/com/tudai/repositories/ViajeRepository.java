package com.tudai.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tudai.entities.Plan;
import com.tudai.entities.Viaje;
import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
	
	public List<ReporteUsuMasViajes> getReporteConMasViajes();
	
	public List<ReporteConMasZonas> getReporteConZona();
	

	@Query("SELECT v "
			+ "FROM Viaje v "
			+ "WHERE v.fechaInicio <= :fechaIni AND v.fechaFin >= :fechaFin AND v.usuario.id = :usuarioId ")
	Viaje getViajeByFecha(Date fechaIni, Date fechaFin, int usuarioId);
	
}