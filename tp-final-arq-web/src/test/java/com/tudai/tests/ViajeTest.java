package com.tudai.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tudai.entities.Usuario;
import com.tudai.entities.Viaje;
import com.tudai.repositories.UsuarioRepository;
import com.tudai.repositories.ViajeRepository;
import com.tudai.utils.DateToMilli;
import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;

/**
 * Pruebas del correcto funcionamiento de viaje
 * @author  teamBolivar
 * @version v1.0
 * @since   2020-11-27
 */
@SpringBootTest
class ViajeTest {
	
	@Autowired
	ViajeRepository viajeRepo;
	
	@Autowired
	UsuarioRepository userRepo;
	
	public Usuario uSeba;
	public Usuario uLucho;
	public Usuario uTest;
	public Viaje v1;
	public Viaje v2;
	public Viaje v3;
	public Viaje v4;
	public Viaje v5;
	
    @BeforeEach
    public void init() {
        System.out.println("---------------------BEFORE");
        uSeba = new Usuario("Seba", "$2a$10$96aLmAt8Mlhmjw7HBLilGezX1Gw3/w8kIpmLicK/8xnPgG0hTg9qW"); //pass: 1234
		uLucho = new Usuario("Lucho", "$2a$10$96aLmAt8Mlhmjw7HBLilGezX1Gw3/w8kIpmLicK/8xnPgG0hTg9qW"); //pass: 1234
		uTest = new Usuario("testing", "$2a$10$96aLmAt8Mlhmjw7HBLilGezX1Gw3/w8kIpmLicK/8xnPgG0hTg9qW"); //pass: 1234
		
		userRepo.save(uTest);
		userRepo.save(uSeba);
		userRepo.save(uLucho);
		userRepo.save(uTest);
		
		v1 = new Viaje("Mis 29 en Londres", "Londres",
				new Date(DateToMilli.getDate("2021/01/18")),
				new Date(DateToMilli.getDate("2021/01/31")),
				"vacaciones en Londres"
			);
		v2 = new Viaje("Mis 46 en Carnarias", "Canarias",
				new Date(DateToMilli.getDate("2021/02/01")),
				new Date(DateToMilli.getDate("2021/02/20")),
				"vacaciones en Canarias"
			);
		v3 = new Viaje("Mis locas vacas en Colombia", "Colombia",
				new Date(DateToMilli.getDate("2021/03/01")),
				new Date(DateToMilli.getDate("2021/03/17")),
				"vacaciones en Colombia"
			);
		v4 = new Viaje("En Londres", "Londres",
				new Date(DateToMilli.getDate("2021/04/01")),
				new Date(DateToMilli.getDate("2021/04/15")),
				"vacaciones en Londres"
		);
		
		v5 = new Viaje("Viaje desde test", "Testing", 
		           new Date(DateToMilli.getDate("2020/11/01")),
		           new Date(DateToMilli.getDate("2020/12/28")),  
				"JUnit"
			);
		
		v1.setUsuario(uSeba);
		v2.setUsuario(uLucho);
		v3.setUsuario(uSeba);
		v4.setUsuario(uSeba);
		v5.setUsuario(uTest);
		
		viajeRepo.save(v1);
		viajeRepo.save(v2);
		viajeRepo.save(v3);
		viajeRepo.save(v4);
		
    }

	@AfterEach
    public void finalize() {
        System.out.println("---------------------AFTER");
		
        viajeRepo.delete(v1);
        viajeRepo.delete(v2);
        viajeRepo.delete(v3);
        viajeRepo.delete(v4);
		userRepo.delete(uLucho);
		userRepo.delete(uSeba);
    }

	@Test
	void testGetReporteConMasViajes() {
		System.out.println("------------------------------step: 1");
		
		List<ReporteUsuMasViajes>  reporteMasViajes= viajeRepo.getReporteConMasViajes();

		assertEquals(3, reporteMasViajes.get(0).getTotal());
	}

	@Test
	void testGetReporteConZona() {
		System.out.println("------------------------------step: 2");
		
		List<ReporteConMasZonas>  reporteMasZona= viajeRepo.getReporteConZona();

		assertEquals(2, reporteMasZona.get(0).getTotal());
		
	}
	
	@Test
	public void crearViajeTest() {
		System.out.println("---------------------step: 3");
		Viaje v = new Viaje("Viaje desde test", "Testing", 
		           new Date(DateToMilli.getDate("2020/11/01")),
		           new Date(DateToMilli.getDate("2020/12/28")),
				"JUnit"
			);
		
		Viaje vSave = viajeRepo.save(v);
		
		assertNotNull(vSave);
	}
	
	@Test
	public void getViajeRangoFecha() {
		System.out.println("---------------------step: 4");
		
		Viaje vSave = viajeRepo.save(v5);
		Viaje vFecha = viajeRepo.getViajeByFecha(new Date(DateToMilli.getDate("2020/11/05")), 
				                                  new Date(DateToMilli.getDate("2020/11/05")), 
				                                  vSave.getUsuario().getId());
		assertEquals(v5.getNombre(), vFecha.getNombre());
	}
	
	@Test
	public void getViajeRangoFechaInvalido() {
		System.out.println("---------------------step: 5");
		
		Viaje vSave = viajeRepo.save(v5);
		Viaje vFecha = viajeRepo.getViajeByFecha(new Date(DateToMilli.getDate("2020/12/29")), 
				                                  new Date(DateToMilli.getDate("2020/12/29")), 
				                                  vSave.getUsuario().getId());
		assertNull(vFecha);
	}

}
