package com.tudai.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.css.ViewCSS;

import com.fasterxml.jackson.annotation.JsonView;
import com.tudai.entities.Plan;
import com.tudai.entities.PlanVuelo;
import com.tudai.entities.Viaje;
import com.tudai.repositories.PlanRepository;
import com.tudai.repositories.ViajeRepository;
import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;
import com.tudai.utils.Views;

@RestController
@RequestMapping("/viajes")
public class ViajeController extends Controller {
	@Qualifier("viajeRepository")
    @Autowired
    private final ViajeRepository repository;
	
	@Qualifier("planRepository")
	@Autowired
    private final PlanRepository planRepository;

    public ViajeController(@Qualifier("viajeRepository") ViajeRepository repository,
    			           @Qualifier("planRepository") PlanRepository planRepository) {
        this.repository = repository;
        this.planRepository = planRepository;
    }
    
    @GetMapping("/")
    public Iterable<Viaje> getViajes() {
    	System.out.println("Viajes entro");
    	return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Viaje> getViaje(@PathVariable int id) {
    	return repository.findById(id);
    }
    
    @GetMapping("/reporte")
    public List<ReporteUsuMasViajes> getViajeConMasViajes() {
    	return repository.getReporteConMasViajes();
    }
    @GetMapping("/reporte-zona")
    public List<ReporteConMasZonas> getViajeConMasZonas() {
    	return repository.getReporteConZona();
    }
    
    @PostMapping("/")
    public Viaje newViaje(@RequestBody Viaje v) {
        return repository.save(v);
    }
    
    @PostMapping("/file")
    public Viaje newViaje(@RequestParam("viaje") Viaje v, @RequestParam("file") MultipartFile file) {
    	//PARSEAR A MANO EL VIAJE PARA GUARDARLO
    	System.out.println(v);
        Viaje viaje = repository.save(v);
        
        String content = null;
        
		try {
			content = new String(file.getBytes());
			JSONObject jsonContent = new JSONObject(content);
			
			Date f_inicio = Date.valueOf(jsonContent.getString("fechaInicio"));
			Date f_fin = Date.valueOf(jsonContent.getString("fechaFin"));
			
			Plan p = new PlanVuelo(jsonContent.getString("nombre"), jsonContent.getInt("numVuelo"), jsonContent.getString("compania"), 
					f_inicio, f_fin, jsonContent.getString("codigoReserva"), jsonContent.getInt("tiempoEscalaMin"), 
					jsonContent.getString("tipoAvion"), jsonContent.getString("aeropuertoSalida"), 
					jsonContent.getString("aeropuertoLlegada"), viaje);
			planRepository.save(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return viaje;
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
