package com.tudai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tudai.entities.Plan;
import com.tudai.entities.PlanHotel;
import com.tudai.entities.PlanVuelo;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
	@Query("SELECT pv FROM PlanVuelo pv")
	public List<PlanVuelo> getAllPlanVuelo();
	
	@Query("SELECT ph FROM PlanHotel ph")
	public List<PlanHotel> getAllPlanHotel();
}
