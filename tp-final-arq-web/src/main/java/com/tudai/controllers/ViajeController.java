package com.tudai.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.css.ViewCSS;

import com.fasterxml.jackson.annotation.JsonView;
import com.tudai.entities.Viaje;
import com.tudai.repositories.ViajeRepository;
import com.tudai.utils.Views;

@RestController
@RequestMapping("/viajes")
public class ViajeController extends Controller {
	@Qualifier("viajeRepository")
    @Autowired
    private final ViajeRepository repository;

    public ViajeController(@Qualifier("viajeRepository") ViajeRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("/")
//    @JsonView(Views.ViajeConPlan.class)
    public Iterable<Viaje> getViajes() {
    	return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Viaje> getViaje(@PathVariable int id) {
    	return repository.findById(id);
    }
    
    @PostMapping("/")
    public Viaje newViaje(@RequestBody Viaje v) {
        return repository.save(v);
    }
    
       
    @PutMapping("/viajes/{id}")
    Viaje replaceViaje(@RequestBody Viaje newViaje, @PathVariable Integer id,HttpServletResponse response) {
    	Optional<Viaje> viajeTempOpt = repository.findById(id);
    	
    	if (viajeTempOpt.isPresent()) {
    		Viaje viajeTemp = (Viaje) viajeTempOpt.get();
    		viajeTemp.setNombre(newViaje.getNombre());
    		viajeTemp.setDestino(newViaje.getDestino());
    		viajeTemp.setFechaFin(newViaje.getFechaInicio());
    		viajeTemp.setFechaFin(newViaje.getFechaFin());
    		viajeTemp.setDescripcionBreve(newViaje.getDescripcionBreve());
    		viajeTemp.setUsuario(newViaje.getUsuario());
       		repository.saveAndFlush(viajeTemp);
    		this.responseStatus(200, response);
    		return viajeTemp;
    	}
    	
    	return null;
    }

    @DeleteMapping("/{id}")
    void deleteViaje(@PathVariable Integer id,HttpServletResponse response) {
    	Optional<Viaje> viaje = repository.findById(id);
    	if( viaje.isPresent() ) {
    		repository.deleteById(id);  
    		this.responseStatus(200,response);
    	}
    	else {
    		this.responseStatus(404,response);    		
    	}
    }
}
