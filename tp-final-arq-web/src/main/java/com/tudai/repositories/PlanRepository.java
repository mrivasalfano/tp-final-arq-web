package com.tudai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tudai.entities.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

}
