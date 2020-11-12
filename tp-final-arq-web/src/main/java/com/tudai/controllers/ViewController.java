package com.tudai.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {
	
	@GetMapping("/home")
	public String greeting( String name, Model model) {
		model.addAttribute("name", "hola hola hola");
		return "home";
	}

}
