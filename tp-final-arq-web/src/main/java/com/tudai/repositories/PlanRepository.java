package com.tudai.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tudai.entities.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

	@Query("SELECT p "
			+ "FROM Plan p "
			+ "WHERE p.fechaInicio > CURRENT_DATE "
			+ "ORDER BY p.fechaInicio")
	List<Plan> getAllPendiente();
	
	@Query("SELECT p "
			+ "FROM Plan p "
			+ "WHERE p.fechaFin <= CURRENT_DATE "
			+ "ORDER BY p.fechaInicio")
	List<Plan> getAllRealizado();
	
	@Query("SELECT p "
			+ "FROM Plan p "
			+ "WHERE p.fechaInicio BETWEEN :fechaIni AND :fechaFin "
			+ "ORDER BY p.fechaInicio")
	List<Plan> getAllRangoFechas(Date fechaIni, Date fechaFin);

	@Query("SELECT p FROM Plan p WHERE p.viaje.destino = :zona")
	List<Plan> getAllPlanesZona(String zona);
	

}
