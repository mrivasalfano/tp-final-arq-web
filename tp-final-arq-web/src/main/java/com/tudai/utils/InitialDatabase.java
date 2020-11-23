package com.tudai.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tudai.entities.Plan;
import com.tudai.entities.PlanVuelo;
import com.tudai.entities.Usuario;
import com.tudai.entities.Viaje;
import com.tudai.repositories.PlanRepository;
import com.tudai.repositories.UsuarioRepository;
import com.tudai.repositories.ViajeRepository;

/**
 * Inicializa datos de prueba
 * @author Team-Bolivar
 * @version v1.0
 * @since   2020-11-24
 */
@Component
@Slf4j
@Order(value = 1)
class InitialDatabase implements CommandLineRunner{
	
	@Autowired
	private PlanRepository planRepo;
	@Autowired
	private ViajeRepository viajeRepo;
	@Autowired
	private UsuarioRepository userRepo;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		planRepo.deleteAll();
		viajeRepo.deleteAll();
		userRepo.deleteAll();
		
		Usuario u = new Usuario("Manu", "$2a$10$96aLmAt8Mlhmjw7HBLilGezX1Gw3/w8kIpmLicK/8xnPgG0hTg9qW");		
    	log.info("Preloading User " + userRepo.save(u));
    	
    	Usuario uAdmin = new Usuario("admin", "$2a$10$96aLmAt8Mlhmjw7HBLilGezX1Gw3/w8kIpmLicK/8xnPgG0hTg9qW");
    	uAdmin.makeAdmin();
    	log.info("Preloading User " + userRepo.save(uAdmin));
		
		Viaje v1 = new Viaje("Mis 40 en miami", "Miami",
		           new Date(DateToMilli.getDate("2020/11/01")), 
		           new Date(DateToMilli.getDate("2020/11/28")), 
		           "vacaciones en la costa"
		);
		
		Viaje v2 = new Viaje("JODA realizada", "Hawai",
		           new Date(DateToMilli.getDate("2020/01/10")), 
		           new Date(DateToMilli.getDate("2020/01/25")),  
		           "vacaciones"
			);
		
		v1.setUsuario(u);
		v2.setUsuario(u);

		
		Viaje vs = viajeRepo.save(v1);
		Viaje vs2 = viajeRepo.save(v2);
	
		
		Plan pVuelo = new PlanVuelo("Plan vuelo 1", 2154, "Despegar", 
           new Date(DateToMilli.getDate("2020/11/01")), 
		   new Date(DateToMilli.getDate("2020/11/02")), 
       "5578", 0, "Volador", "Aeropuerto 1", "Aeropuerto 2", vs);

		Plan planComun = new Plan("Plan Comun 1", 
	        new Date(DateToMilli.getDate("2020/11/12")), 
	        new Date(DateToMilli.getDate("2020/11/15")), 
        "1234", vs);

		Plan pVuelo2 = new PlanVuelo("Plan vuelo 2", 1234, "Despegar", 
           new Date(DateToMilli.getDate("2020/01/10")), 
           new Date(DateToMilli.getDate("2020/01/12")), 
       "1234", 0, "Volador 2", "Aeropuerto 2", "Aeropuerto 1", vs2);

		Plan planComun2 = new Plan("Plan Comun 2", 
	               new Date(DateToMilli.getDate("2020/01/15")), 
	               new Date(DateToMilli.getDate("2020/01/18")), 
	               "1234", vs2);
		
		log.info("Preloading " + vs);
		
    	log.info("Preloading " + planRepo.save(pVuelo));
    	log.info("Preloading " + planRepo.save(pVuelo2));
    	log.info("Preloading " + planRepo.save(planComun));
    	
    	log.info("Preloading " + planRepo.save(planComun2));
    	
    	
		
		System.out.println("Paso 1 - InicialDatabase - Iniciar los primeros datos");
	}
    
}

