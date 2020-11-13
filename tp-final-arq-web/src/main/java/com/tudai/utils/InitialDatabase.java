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
 * Inicializa datos de testing
 * @author tudai bolivar
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
//			new Date(System.currentTimeMillis()+(24L*60*60*1000*1)),
//			new Date(System.currentTimeMillis()+(24L*60*60*1000*5)),
	           new Date(1604199600000L), //01/11/2020
	           new Date(1606532400000L), //28/12/2020 
			"vacaciones en la costa"
		);
		
		Viaje v2 = new Viaje("JODA realizada", "Hawai", 
//				new Date(System.currentTimeMillis()+(24L*60*60*1000*1)),
//				new Date(System.currentTimeMillis()+(24L*60*60*1000*5)),
		           new Date(System.currentTimeMillis()-(24L*60*60*1000*30)), 
		           new Date(System.currentTimeMillis()-(24L*60*60*1000*20)),  
				"vacaciones"
			);
		

		
		v1.setUsuario(u);
		v2.setUsuario(u);

		
		Viaje vs = viajeRepo.save(v1);
		Viaje vs2 = viajeRepo.save(v2);
	
		
		Plan pVuelo = new PlanVuelo("Plan vuelo 1", 2154, "Despegar", 
           new Date(System.currentTimeMillis()+(24L*60*60*1000*1)), 
		   new Date(System.currentTimeMillis()+(24L*60*60*1000*2)), 
       "5578", 0, "Volador", "Aeropuerto 1", "Aeropuerto 2", vs);

		Plan planComun = new Plan("Plan Comun 1", 
	        new Date(System.currentTimeMillis()+(24L*60*60*1000*2)), 
	        new Date(System.currentTimeMillis()+(24L*60*60*1000*4)), 
        "1234", vs);

		Plan pVuelo2 = new PlanVuelo("Plan vuelo 2", 1234, "Despegar", 
           new Date(System.currentTimeMillis()+(24L*60*60*1000*4)), 
           new Date(System.currentTimeMillis()+(24L*60*60*1000*5)), 
       "1234", 0, "Volador 2", "Aeropuerto 2", "Aeropuerto 1", vs);

		Plan planComun2 = new Plan("Plan Comun 2", 
	               new Date(System.currentTimeMillis()-(24L*60*60*1000*10)), 
	               new Date(System.currentTimeMillis()), 
	               "1234", vs);
		
		log.info("Preloading " + vs);
		
    	log.info("Preloading " + planRepo.save(pVuelo));
    	log.info("Preloading " + planRepo.save(pVuelo2));
    	log.info("Preloading " + planRepo.save(planComun));
    	
    	log.info("Preloading " + planRepo.save(planComun2));
    	
    	
		
		System.out.println("Paso 1 - InicialDatabase - Iniciar los primeros datos");
	}
    
}

