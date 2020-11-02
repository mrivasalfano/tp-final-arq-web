package com.tudai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tudai.entities.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
	
//	@Query("SELECT v.destino, u.nombre, COUNT(*) AS cantidad "
//			+ "FROM Viaje v JOIN Usuario u "
//			+ "GROUP BY v.usuario_id ")
//	SELECT v.destino, v.usuario_id, COUNT(*) AS total FROM viaje v GROUP BY v.usuario_id
//	public List<Object[]> getReporteConMasViajes();
}