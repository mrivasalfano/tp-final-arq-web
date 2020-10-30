package com.tudai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tudai.entities.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
//	@Query("SELECT v, COUNT(usuarios) FROM Viaje v GROUP BY v.usuario_id AS usuarios")
//	public void getReporteConMasViajes();
}