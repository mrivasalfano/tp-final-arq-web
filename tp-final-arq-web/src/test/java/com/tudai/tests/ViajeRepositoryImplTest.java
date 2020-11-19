package com.tudai.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tudai.entities.Usuario;
import com.tudai.entities.Viaje;
import com.tudai.repositories.UsuarioRepository;
import com.tudai.repositories.ViajeRepository;
import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ViajeRepositoryImplTest {
	
	@Autowired
	ViajeRepository viajeRepo;
	
	@Autowired
	UsuarioRepository userRepo;
	
	public static Usuario uSeba;
	public static Usuario uLucho;
	public static Viaje v1;
	public static Viaje v2;
	public static Viaje v3;
	public static Viaje v4;
	
    @BeforeEach
    public void init() {
        System.out.println("---------------------BEFORE");
        uSeba = new Usuario("Seba", "$2a$10$96aLmAt8Mlhmjw7HBLilGezX1Gw3/w8kIpmLicK/8xnPgG0hTg9qW"); //pass: 1234
		uLucho = new Usuario("Lucho", "$2a$10$96aLmAt8Mlhmjw7HBLilGezX1Gw3/w8kIpmLicK/8xnPgG0hTg9qW"); //pass: 1234
    
		userRepo.save(uSeba);
		userRepo.save(uLucho);
		
		v1 = new Viaje("Mis 29 en Londres", "Londres",
				new Date(getDate("2021/01/18")),
				new Date(getDate("2021/01/31")),
				"vacaciones en Londres"
			);
		v2 = new Viaje("Mis 46 en Carnarias", "Canarias",
				new Date(getDate("2021/02/01")),
				new Date(getDate("2021/02/20")),
				"vacaciones en Canarias"
			);
		v3 = new Viaje("Mis locas vacas en Colombia", "Colombia",
				new Date(getDate("2021/03/01")),
				new Date(getDate("2021/03/17")),
				"vacaciones en Colombia"
			);
		v4 = new Viaje("En Londres", "Londres",
				new Date(getDate("2021/04/01")),
				new Date(getDate("2021/04/15")),
				"vacaciones en Londres"
		);
		
		v1.setUsuario(uSeba);
		v2.setUsuario(uLucho);
		v3.setUsuario(uSeba);
		v4.setUsuario(uSeba);
		
		viajeRepo.save(v1);
		viajeRepo.save(v2);
		viajeRepo.save(v3);
		viajeRepo.save(v4);
		
    }
    
    private long getDate(String myDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(myDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long millis = date.getTime();
		return millis;
	}

	@AfterEach
    public void finalize() {
        System.out.println("---------------------AFETR");
		
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
	

}
