package com.tudai.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tudai.entities.Plan;
import com.tudai.entities.Viaje;
import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;

public interface ViajeRepository extends JpaRepository<Viaje, Integer>, ViajeRepositoryCustom {

	@Query("SELECT v "
			+ "FROM Viaje v "
			+ "WHERE v.fechaInicio <= :fechaIni AND v.fechaFin >= :fechaFin AND v.usuario.id = :usuarioId ")
	public Viaje getViajeByFecha(Date fechaIni, Date fechaFin, int usuarioId);

	@Query("SELECT v "
			+ "FROM Viaje v "
			+ "WHERE v.fechaInicio > current_date()")
	public List<Viaje> getReportePendiente();

	@Query("SELECT v "
			+ "FROM Viaje v "
			+ "WHERE v.fechaFin < current_date()")
	public List<Viaje> getReporteRealizado();

	@Query("SELECT v "
			+ "FROM Viaje v "
			+ "WHERE v.fechaInicio BETWEEN :fechaIni AND :fechaFin "
			+ "ORDER BY v.fechaInicio")
	public List<Viaje> getAllRangoFechas(Date fechaIni, Date fechaFin);
	
}