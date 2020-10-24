package com.tudai.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tudai.entities.Plan;
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
    public Iterable<Plan> getPlan() {
    	return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Plan> getPlan(@PathVariable int id) {
    	return repository.findById(id);
    }
        
    @PostMapping("/")
    public Plan newPlan(@RequestBody Plan u) {
        return repository.save(u);
    }
    
    @PutMapping("/{id}")
    Plan replacePlan(@RequestBody Plan newPlan, @PathVariable Integer id,HttpServletResponse response) {

    	Optional<Plan> plan = repository.findById(id);
//    	if( plan.isPresent() ) {
//        		plan.get().setNombre(newPlan.getNombre());
//        		plan.get().setFechaInicio(newPlan.getFechaInicio());
//        		plan.get().setFechaFin(newPlan.getFechaFin());
//        		plan.get().setCodigoReserva(newPlan.getCodigoReserva());
//        		return repository.save(plan.get());        		
//        }    	
//        this.responseStatus(404,response);    	
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
