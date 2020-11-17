package com.tudai.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tudai.entities.Usuario;
import com.tudai.entities.Viaje;
import com.tudai.repositories.UsuarioRepository;
import com.tudai.repositories.ViajeRepository;

@SpringBootTest
public class ViajeTest {
	@Autowired
	ViajeRepository repository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Test
	public void crearUsuarioTest() {
		Usuario u = new Usuario("testing", "1234");
		usuarioRepository.save(u);
		
		assertNotNull(u);
	}
	
	@Test
	public void crearViajeTest() {
		Viaje v = new Viaje("Viaje desde test", "Testing", 
		           new Date(1604199600000L), //01/11/2020
		           new Date(1606532400000L), //28/12/2020 
				"JUnit"
			);
		
		Viaje vSave = repository.save(v);
		
		assertNotNull(vSave);
	}
	
	@Test
	public void getViajeRangoFecha() {
		Usuario u = new Usuario("testing", "1234");
		usuarioRepository.save(u);
		
		Viaje v = new Viaje("Viaje desde test", "Testing", 
		           new Date(1604199600000L), //01/11/2020
		           new Date(1606532400000L), //28/12/2020 
				"JUnit"
			);
		v.setUsuario(u);
		
		Viaje vSave = repository.save(v);
		
		Viaje vFecha = repository.getViajeByFecha(new Date(1604545200000L), 
				                                  new Date(1604545200000L), 
				                                  vSave.getUsuario().getId());
		
		assertNotNull(vFecha);
	}
	
	@Test
	public void getViajeRangoFechaInvalido() {
		Usuario u = new Usuario("testing", "1234");
		usuarioRepository.save(u);
		
		Viaje v = new Viaje("Viaje desde test", "Testing", 
		           new Date(1604199600000L), //01/11/2020
		           new Date(1606532400000L), //28/12/2020 
				"JUnit"
			);
		v.setUsuario(u);
		
		Viaje vSave = repository.save(v);
		
		Viaje vFecha = repository.getViajeByFecha(new Date(1609210800000L), 
                								  new Date(1609210800000L), 
                								  vSave.getUsuario().getId());
		assertNull(vFecha);
	}
	
}
