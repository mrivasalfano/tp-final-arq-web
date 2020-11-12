package com.tudai.repositories;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;

import lombok.extern.slf4j.Slf4j;

/**
 * Necesaria para implementar consultas más específicas
 * haciendo uso del EntityManager de JPA
 */
@Repository
@Slf4j
public class ViajeRepositoryImpl implements ViajeRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ReporteUsuMasViajes> getReporteConMasViajes() {
		//Lista sql
		List<Object[]> listadoClientesVentas = em.createQuery(""
				+ "SELECT v.destino, v.usuario.nombre, COUNT(*) AS cantidad "
				+ "FROM Viaje v GROUP BY v.usuario.id ORDER BY cantidad DESC").getResultList();
//		Liste reporte
		List<ReporteUsuMasViajes> reporte = new ArrayList<ReporteUsuMasViajes>();
		
		ReporteUsuMasViajes reporteTemp;
		for (Object[] item : listadoClientesVentas) {
			reporteTemp = new ReporteUsuMasViajes((String) item[1], ((Long)item[2]));
			reporte.add(reporteTemp);			
		}
		return reporte;
	}
	

	
	@Override
	@Transactional
	public List<ReporteConMasZonas> getReporteConZona() {
		//Lista sql
		List<Object[]> listadoClientesVentas = em.createQuery("SELECT v.destino, COUNT(*) AS cantidad FROM Viaje v GROUP BY v.destino ORDER BY cantidad DESC").getResultList();
//		Liste reporte
		List<ReporteConMasZonas> reporte = new ArrayList<ReporteConMasZonas>();
		
		ReporteConMasZonas reporteTemp;
		for (Object[] item : listadoClientesVentas) {
			reporteTemp = new ReporteConMasZonas((String) item[0], (Long) item[1]);
			reporte.add(reporteTemp);			
		}
		return reporte;
	}
	
}
