package com.tudai.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tudai.entities.Plan;
import com.tudai.entities.PlanVuelo;
import com.tudai.entities.Usuario;
import com.tudai.entities.Viaje;
import com.tudai.repositories.PlanRepository;
import com.tudai.repositories.UsuarioRepository;
import com.tudai.repositories.ViajeRepository;
import com.tudai.utils.DateToMilli;

@SpringBootTest
class PlanTest {

	@Autowired
	ViajeRepository viajeRepo;
	
	@Autowired
	UsuarioRepository userRepo;
	
	@Autowired
	PlanRepository planRepo;
	
	public Usuario uRobert;
	public Viaje v1;
	public Viaje vs;
	public Plan pVuelo;
	public Plan planComun;
	public Plan pVuelo2;
	public Plan planComun2;
	
    @BeforeEach
    public void init() {
        System.out.println("---------------------BEFORE");
    	uRobert = new Usuario("Robert", "$2a$10$96aLmAt8Mlhmjw7HBLilGezX1Gw3/w8kIpmLicK/8xnPgG0hTg9qW");		
    	userRepo.save(uRobert);
		
		v1 = new Viaje("Mis 40 en miami", "Miami",
	           new Date(DateToMilli.getDate("2020/11/01")),
	           new Date(DateToMilli.getDate("2020/11/12")), 
			"vacaciones en la costa"
		);
		
		v1.setUsuario(uRobert);
			
		vs = viajeRepo.save(v1);
	
		pVuelo = new PlanVuelo("Plan vuelo 1 realizado", 2154, "Despegar", 
           new Date(System.currentTimeMillis()+(24L*60*60*1000*1)), 
		   new Date(System.currentTimeMillis()+(24L*60*60*1000*2)), 
       "5578", 0, "Volador", "Aeropuerto 1", "Aeropuerto 2", vs);

		planComun = new Plan("Plan Comun 1 pendiente", 
	        new Date(System.currentTimeMillis()-(24L*60*60*1000*2)), 
	        new Date(System.currentTimeMillis()-(24L*60*60*1000*4)), 
        "1234", vs);

		pVuelo2 = new PlanVuelo("Plan vuelo 2 pendiente", 1234, "Despegar", 
           new Date(System.currentTimeMillis()-(24L*60*60*1000*4)), 
           new Date(System.currentTimeMillis()-(24L*60*60*1000*5)), 
       "1234", 0, "Volador 2", "Aeropuerto 2", "Aeropuerto 1", vs);

		planComun2 = new Plan("Plan Comun 2 pendiente", 
	               new Date(System.currentTimeMillis()-(24L*60*60*1000*10)), 
	               new Date(System.currentTimeMillis()), 
	               "1234", vs);
		
		planRepo.save(pVuelo);
    	planRepo.save(pVuelo2);
    	planRepo.save(planComun);
    	planRepo.save(planComun2);
    }
    
	@AfterEach
    public void finalize() {
        System.out.println("---------------------AFTER");
        viajeRepo.delete(v1);
        userRepo.delete(uRobert);
        planRepo.delete(pVuelo);
        planRepo.delete(pVuelo);
        planRepo.delete(pVuelo);
        planRepo.delete(pVuelo);
	}
	
	@Test
	public void testGetAllPendiente() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllRealizado() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllRangoFechas() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllPlanesZona() {
		fail("Not yet implemented");
	}

}
