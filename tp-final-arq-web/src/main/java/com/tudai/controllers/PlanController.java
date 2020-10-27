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

import com.tudai.entities.Plan;
import com.tudai.entities.PlanVuelo;
import com.tudai.repositories.PlanRepository;

@RestController
@RequestMapping("/planes")
public class PlanController extends Controller {

    @Qualifier("planRepository")
    @Autowired
    private final PlanRepository repository;

    public PlanController(@Qualifier("planRepository") PlanRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("/")
    public Iterable<Plan> getPlanes() {
    	return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Plan> getPlan(@PathVariable int id) {
    	return repository.findById(id);
    }
    
    @PostMapping("/")
    public Plan newPlan(@RequestBody Plan p) {
        return repository.save(p);
    }
    
    @PostMapping("/vuelos")
    public Plan newPlanVuelo(@RequestBody PlanVuelo pv) {
        return repository.save(pv);
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
}
