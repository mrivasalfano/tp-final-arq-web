package com.tudai.controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;


import com.fasterxml.jackson.databind.JsonNode;
import com.tudai.entities.Plan;
import com.tudai.entities.PlanVuelo;
import com.tudai.entities.Viaje;
import com.tudai.repositories.PlanRepository;
import com.tudai.repositories.ViajeRepository;

/**
 * Rest Controller de Planes. Se encarga de recibir
 * peticiones HTTP del cliente, realizar procesos y responder.
 * @author Team-Bolivar
 * @version v1.0
 * @since   2020-11-24
 */
@RestController
@RequestMapping("/planes")
public class PlanController extends Controller {

	@Qualifier("planRepository")
	@Autowired
	private final PlanRepository repository;
	
	@Qualifier("viajeRepository")
	@Autowired
	private final ViajeRepository vrepository;

	public PlanController(@Qualifier("planRepository") PlanRepository repository,@Qualifier("viajeRepository") ViajeRepository vrepository) {
		this.repository = repository;
		this.vrepository = vrepository;
		
	}

	@GetMapping("/")
	public Iterable<Plan> getPlanes() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Plan> getPlan(@PathVariable int id) {
		return repository.findById(id);
	}

	/**
	 * Retorna los planes pendientes, es decir cuya fecha
	 * sea mayor a la actual
	 * @return lista de planes
	 */
	@GetMapping("/pendientes")
	public List<Plan> getPlanesPendiente() {
		return repository.getAllPendiente();
	}

	/**
	 * Retorna los planes realizados, es decir cuya fecha
	 * sea menor a la actual
	 * @return lista de planes
	 */
	@GetMapping("/realizados")
	public List<Plan> getPlanesRealizados() {
		return repository.getAllRealizado();
	}

	/**
	 * Retorna los planes que estén dentro del rango
	 * de fecha inicio y fecha fin
	 * @param json con fecha inicia y fecha fin
	 * @return lista de planes
	 */
	@PostMapping("/periodo")
	public List<Plan> getPlanesRangoFecha(@RequestBody JsonNode json) {
		Date fechaIni = Date.valueOf(json.get("fechaIni").asText());
		Date fechaFin = Date.valueOf(json.get("fechaFin").asText());
		if(fechaIni != null && fechaFin != null)
			return repository.getAllRangoFechas(fechaIni, fechaFin);
		else
			return null;
	}

	/**
	 * Retorna los planes cuyo viaje esté en el destino especifidado
	 * @param json con la zona geográfica
	 * @return lista de planes
	 */
	@PostMapping("/zona")
	public List<Plan> getPlanesZonaGeografica(@RequestBody JsonNode json) {
		String zona = json.get("zonaGeografica").asText();
		if(zona != null)
			return repository.getAllPlanesZona(zona);
		else
			return null;
	}

	@PostMapping("/")
	public Plan newPlan(@RequestBody Plan p) {
		Optional<Viaje> v = vrepository.findById(p.getIdViaje());
		
		if (v.isPresent()) {
			p.setViaje(v.get());
			return repository.save(p);
		}
		
		return null;
	}

	@PostMapping("/vuelos")
	public Plan newPlanVuelo(@RequestBody PlanVuelo pv) {
		Optional<Viaje> v = vrepository.findById(pv.getIdViaje());
		
		if (v.isPresent()) {
			pv.setViaje(v.get());
			return repository.save(pv);
		}
		
		return null;
	}

	@PutMapping("/vuelos/{id}")
	Plan replacePlanVuelo(@RequestBody PlanVuelo newPlan, @PathVariable Integer id,HttpServletResponse response) {
		Optional<Plan> planTempOpt = repository.findById(id);

		if (planTempOpt.isPresent()) {
			PlanVuelo planTemp = (PlanVuelo) planTempOpt.get();
			planTemp.setNombre(newPlan.getNombre());
			planTemp.setFechaInicio(newPlan.getFechaInicio());
			planTemp.setFechaFin(newPlan.getFechaFin());
			planTemp.setCodigoReserva(newPlan.getCodigoReserva());
			planTemp.setViaje(newPlan.getViaje());
			planTemp.setNumVuelo(newPlan.getNumVuelo());
			planTemp.setCompania(newPlan.getCompania());
			planTemp.setTiempoEscalaMin(newPlan.getTiempoEscalaMin());
			planTemp.setTipoAvion(newPlan.getTipoAvion());
			planTemp.setAeropuertoSalida(newPlan.getAeropuertoSalida());
			planTemp.setAeropuertoLlegada(newPlan.getAeropuertoLlegada());
			repository.saveAndFlush(planTemp);
			this.responseStatus(200, response);
			return planTemp;
		}

		return null;
	}

	@DeleteMapping("/{id}")
	void deletePlan(@PathVariable Integer id,HttpServletResponse response) {
		Optional<Plan> plan = repository.findById(id);
		if( plan.isPresent() ) {
			repository.deleteById(id);  
			this.responseStatus(200,response);
		}
		else {
			this.responseStatus(404,response);    		
		}
	}

	/**
	 * Guarda el plan común o plan vuelo según los datos
	 * que contenga el file
	 * @param file archivo .json con datos del plan a crear
	 * @param response HTTP con status
	 * @return plan creado
	 */
	@PostMapping("/upload-plan")	
	public Plan uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
		String content = null;
		System.out.println("ANTES");
		try {
			System.out.println("TRY");
			content = new String(file.getBytes());
			JSONObject jsonContent = new JSONObject(content);
			Date f_inicio = Date.valueOf(jsonContent.getString("fechaInicio"));
			Date f_fin = Date.valueOf(jsonContent.getString("fechaFin"));
			// buscar viaje por fecha
			System.out.println("getViajeByFecha: " + f_inicio+" "+f_fin+" "+(Integer) SecurityContextHolder.getContext().getAuthentication().getDetails());
			Viaje v = vrepository.getViajeByFecha(f_inicio, f_fin, (Integer) SecurityContextHolder.getContext().getAuthentication().getDetails());
			System.out.println("viaje response: " + v);
			if(v != null){
				System.out.println("Encontro viaje");
				Plan p = null;
				if(jsonContent.has("compania")){
					// plan vuelo
					p = new PlanVuelo(jsonContent.getString("nombre"), jsonContent.getInt("numVuelo"), jsonContent.getString("compania"), 
							f_inicio, f_fin, jsonContent.getString("codigoReserva"), jsonContent.getInt("tiempoEscalaMin"), 
							jsonContent.getString("tipoAvion"), jsonContent.getString("aeropuertoSalida"), 
							jsonContent.getString("aeropuertoLlegada"), v);
					repository.save(p);
				}
				else {
					// plan general
					p = new Plan(jsonContent.getString("nombre"), f_inicio, f_fin, jsonContent.getString("codigoReserva"), v);
					repository.save(p);					
				}
				
				return p;
			}
			else {
				this.responseStatus(406, response);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
