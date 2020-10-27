package com.tudai.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tudai.entities.Plan;
import com.tudai.entities.PlanVuelo;
import com.tudai.entities.Usuario;
import com.tudai.repositories.PlanRepository;
import com.tudai.repositories.UsuarioRepository;

/**
 * Inicializa datos esenciales que van a ser usados luego en LoadDatabase
 * @author tudai bolivar
 */
@Component
@Slf4j
@Order(value = 1)
class InitialDatabase implements CommandLineRunner{
	
	@Autowired
	private PlanRepository planRepo;
	@Autowired
	private UsuarioRepository userRepo;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		Plan pVuelo = new PlanVuelo("Plan vuelo 1", 2154, "Despegar", 
//				               new Timestamp(System.currentTimeMillis()), 
//				               new Timestamp(System.currentTimeMillis()), 
//				               "5578", 0, "Volador", "Aeropuerto 1", "Aeropuerto 2");
//		Plan pHotel = new PlanHotel("Plan hotel 1", 
//							new Timestamp(System.currentTimeMillis()),
//							new Timestamp(System.currentTimeMillis()), 
//							"546fsa", "Direccion", 10, "2A");
//		
//    	log.info("Preloading " + planRepo.save(pVuelo));
//    	log.info("Preloading " + planRepo.save(pHotel));
//		Usuario u = new Usuario("Manu", "1234");
//    	log.info("Preloading User " + userRepo.save(u));
		System.out.println("Paso 1 - InicialDatabase - Iniciar los primeros datos");
	}
    
}

