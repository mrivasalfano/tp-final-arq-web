package com.tudai.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.tudai.entities.Plan;
import com.tudai.entities.PlanVuelo;
import com.tudai.entities.Usuario;
import com.tudai.entities.Viaje;
import com.tudai.repositories.PlanRepository;
import com.tudai.repositories.UsuarioRepository;
import com.tudai.repositories.ViajeRepository;
import com.tudai.utils.ReporteConMasZonas;
import com.tudai.utils.ReporteUsuMasViajes;

@RestController
@RequestMapping("/viajes")
public class ViajeController extends Controller {
	@Qualifier("viajeRepository")
    @Autowired
    private final ViajeRepository repository;
	@Qualifier("usuarioRepository")
    @Autowired
    private final UsuarioRepository usuRepository;
	
	@Qualifier("planRepository")
	@Autowired
    private final PlanRepository planRepository;

    public ViajeController(@Qualifier("viajeRepository") ViajeRepository repository,
    			           @Qualifier("planRepository") PlanRepository planRepository,
    			           @Qualifier("planRepository") UsuarioRepository usuRepository) {
        this.repository = repository;
        this.planRepository = planRepository;
        this.usuRepository = usuRepository;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ReporteUsuMasViajes> getUsuariosConMasViajes() {
    	return repository.getReporteConMasViajes();
    }
    
    
    @GetMapping("/reporte-zona")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ReporteConMasZonas> getViajeConMasZonas() {
    	return repository.getReporteConZona();
    }
    
    @GetMapping("/reporte-estado/{estado}")
    public List<Viaje> getViajeEstado(@PathVariable String estado) {
    	if (estado.equals("pendiente")) {
    		return repository.getReportePendiente();
    	}
    	if(estado.equals("realizado")) {
    		return repository.getReporteRealizado();
    	}
    	return null;
    	
    }
    
    @PostMapping("/reporte-periodo")
	public List<Viaje> getPlanesRangoFecha(@RequestBody JsonNode json) {
		Date fechaIni = Date.valueOf(json.get("fechaIni").asText());
		Date fechaFin = Date.valueOf(json.get("fechaFin").asText());
		if(fechaIni != null && fechaFin != null)
			return repository.getAllRangoFechas(fechaIni, fechaFin);
		else
			return null;
	}
    
    
    @PostMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public Viaje newViaje(@RequestBody Viaje v) {
		int idUsu = (Integer) SecurityContextHolder.getContext().getAuthentication().getDetails();
		Optional<Usuario> u = usuRepository.findById(idUsu);
		if(u.isPresent()) {
			v.setUsuario(u.get());
			return repository.save(v);
		}
		return null;
    }
    
    @PostMapping("/upload-file")
    public Viaje newViaje(@RequestParam("file") MultipartFile file, HttpServletResponse response) { 
    	Viaje viaje = null;
        String content = null;
        
		try {
			content = new String(file.getBytes());
			JSONObject jsonContent = new JSONObject(content);
			Date f_inicio = Date.valueOf(jsonContent.getString("fechaInicio"));
			Date f_fin = Date.valueOf(jsonContent.getString("fechaFin"));
			System.out.println("1) upload-file-viaje: " + jsonContent.getString("fechaInicio") + jsonContent.getString("fechaFin") );
			System.out.println("2) upload-file-viaje: " + f_inicio + f_fin);
			String nom = jsonContent.getString("nombre");
			String destino = jsonContent.getString("destino");
			String descrip = jsonContent.getString("descripcionBreve");			
			int idUsu = (Integer) SecurityContextHolder.getContext().getAuthentication().getDetails();
			Optional<Usuario> u = usuRepository.findById(idUsu);
			if(u.isPresent()) {
				System.out.println("idUsu: --------------------------" + u.get());				
				Viaje vi = new Viaje(nom, destino,f_inicio, f_fin, descrip);
				vi.setUsuario(u.get());
				viaje = repository.save(vi);				
			
				JSONObject planVuelo = jsonContent.getJSONObject("planVuelo");
				nom = planVuelo.getString("nombre");
				f_inicio = Date.valueOf(planVuelo.getString("fechaInicio"));
				f_fin = Date.valueOf(planVuelo.getString("fechaFin"));
				String codReserva = planVuelo.getString("codigoReserva");
				int numVuelo = planVuelo.getInt("numVuelo");
				String compania = planVuelo.getString("compania");
				int tiempoEscalaMin = planVuelo.getInt("tiempoEscalaMin");
				String tipoAvion = planVuelo.getString("tipoAvion");
				String aeropuertoSalida = planVuelo.getString("aeropuertoSalida");
				String aeropuertoLlegada = planVuelo.getString("aeropuertoLlegada");
				
				Plan p = new PlanVuelo(nom, numVuelo, compania, f_inicio, f_fin, codReserva,
									   tiempoEscalaMin, tipoAvion, aeropuertoSalida, 
									   aeropuertoLlegada, viaje);
				
				viaje.addPlan(p);
				planRepository.save(p);
	    		this.responseStatus(200, response);
			} else {
//				response.sendRedirect("/");
	    		this.responseStatus(404, response);
			}
			
		} catch (IOException e) {
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
