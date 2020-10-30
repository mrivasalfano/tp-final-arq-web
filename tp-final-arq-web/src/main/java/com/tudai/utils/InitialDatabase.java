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
	private ViajeRepository viajeRepo;
	@Autowired
	private UsuarioRepository userRepo;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		Viaje v1 = new Viaje("Mis 40 en miami", "Miami", 
//			new Date(System.currentTimeMillis()+(24L*60*60*1000*1)),
//			new Date(System.currentTimeMillis()+(24L*60*60*1000*5)),
//			"vacaciones en la costa"
//		);
//		
//		Plan pVuelo = new PlanVuelo("Plan vuelo 1", 2154, "Despegar", 
//           new Date(System.currentTimeMillis()+(24L*60*60*1000*1)), 
//           new Date(System.currentTimeMillis()+(24L*60*60*1000*2)), 
//       "5578", 0, "Volador", "Aeropuerto 1", "Aeropuerto 2");
//
//		Plan planComun = new Plan("Plan Comun 1", 
//	        new Date(System.currentTimeMillis()+(24L*60*60*1000*2)), 
//	        new Date(System.currentTimeMillis()+(24L*60*60*1000*4)), 
//        "1234");
//
//		Plan pVuelo2 = new PlanVuelo("Plan vuelo 2", 1234, "Despegar", 
//           new Date(System.currentTimeMillis()+(24L*60*60*1000*4)), 
//           new Date(System.currentTimeMillis()+(24L*60*60*1000*5)), 
//       "1234", 0, "Volador 2", "Aeropuerto 2", "Aeropuerto 1");
//
//		Plan planComun2 = new Plan("Plan Comun 2", 
//	               new Date(System.currentTimeMillis()-(24L*60*60*1000*10)), 
//	               new Date(System.currentTimeMillis()), 
//	               "1234");
//		
//		v1.addPlan(pVuelo);
//		v1.addPlan(planComun);
//		v1.addPlan(pVuelo2);
//		
//		log.info("Preloading " + viajeRepo.save(v1));
		
//    	log.info("Preloading " + planRepo.save(pVuelo));
//    	log.info("Preloading " + planRepo.save(pVuelo2));
//    	log.info("Preloading " + planRepo.save(planComun));
    	
//    	log.info("Preloading " + planRepo.save(planComun2));
//		Usuario u = new Usuario("Manu", "1234");
//    	log.info("Preloading User " + userRepo.save(u));
		
		System.out.println("Paso 1 - InicialDatabase - Iniciar los primeros datos");
	}
    
}

