package com.tudai.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

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

/**
 * Pruebas del correcto funcionamiento de Plan
 * @author Team-Bolivar
 * @version v1.0
 * @since   2020-11-24
 */
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
    	planRepo.deleteAll();
    	viajeRepo.deleteAll();
    	userRepo.deleteAll();	
    	
        System.out.println("\n---------------------BEFORE");
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
           new Date(System.currentTimeMillis()-(24L*60*60*1000*2)), 
		   new Date(System.currentTimeMillis()-(24L*60*60*1000*1)), 
       "5578", 0, "Volador", "Aeropuerto 1", "Aeropuerto 2", vs);

		planComun = new Plan("Plan Comun 1 pendiente", 
	        new Date(System.currentTimeMillis()+(24L*60*60*1000*2)), 
	        new Date(System.currentTimeMillis()+(24L*60*60*1000*4)), 
        "1234", vs);

		pVuelo2 = new PlanVuelo("Plan vuelo 2 pendiente", 1234, "Despegar", 
           new Date(System.currentTimeMillis()+(24L*60*60*1000*4)), 
           new Date(System.currentTimeMillis()+(24L*60*60*1000*5)), 
       "1234", 0, "Volador 2", "Aeropuerto 2", "Aeropuerto 1", vs);

		planComun2 = new Plan("Plan Comun 2 pendiente", 
	               new Date(System.currentTimeMillis()+(24L*60*60*1000*10)), 
	               new Date(System.currentTimeMillis()+(24L*60*60*1000*11)), 
	               "1234", vs);
		
		planRepo.save(pVuelo);
    	planRepo.save(pVuelo2);
    	planRepo.save(planComun);
    	planRepo.save(planComun2);
    }
    
	@AfterEach
    public void finalize() {
        System.out.println("---------------------AFTER");

    	planRepo.deleteAll();
    	viajeRepo.deleteAll();
    	userRepo.deleteAll();	
	}
	
	@Test
	public void testGetAllPendiente() {
        System.out.println("Test 1 --------------------- testGetAllPendiente()");
		List<Plan> planes = planRepo.getAllPendiente();

		for (Plan plan : planes) {
			System.out.println(plan);
		}
		
		assertEquals(3, planes.size());
	}

	@Test
	public void testGetAllRealizado() {
        System.out.println("Test 2 --------------------- testGetAllRealizado()");
		List<Plan> planes = planRepo.getAllRealizado();
		
		for (Plan plan : planes) {
			System.out.println(plan);
		}

		assertEquals(1, planes.size());
	}

	@Test
	public void testGetAllRangoFechas() {
        System.out.println("Test 3 --------------------- testGetAllRangoFechas()");
		List<Plan> planes =  planRepo.getAllRangoFechas(new Date(DateToMilli.getDate("2020/11/21")), 
									new Date(DateToMilli.getDate("2020/11/24")));
		for (Plan plan : planes) {
			System.out.println(plan.getNombre() + " - F_Ini: " + plan.getFechaInicio() + " - F_fin: " + plan.getFechaFin());
		}

//		assertEquals(2, planes.size());
		assert(planes.size() > 0);
	}

	@Test
	public void testGetAllPlanesZona() {
        System.out.println("Test 4 --------------------- testGetAllPlanesZona()");
		String zona = "Miami";
		List<Plan> planes = planRepo.getAllPlanesZona(zona);
		
		for (Plan plan : planes) {
			System.out.println(plan.getNombre() + " - F_Ini: " + plan.getFechaInicio() + " - F_fin: " + plan.getFechaFin());
		}
		
		assertEquals(4, planes.size());
	}

}
