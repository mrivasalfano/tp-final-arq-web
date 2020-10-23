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

import com.tudai.entities.Usuario;
import com.tudai.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends Controller {

    @Qualifier("usuarioRepository")
    @Autowired
    private final UsuarioRepository repository;

    public UsuarioController(@Qualifier("usuarioRepository") UsuarioRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("/")
    public Iterable<Usuario> getUsuario() {
    	return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Usuario> getUsuario(@PathVariable int id) {
    	return repository.findById(id);
    }
        
    @PostMapping("/")
    public Usuario newUsuario(@RequestBody Usuario u) {
        return repository.save(u);
    }
    
    @PutMapping("/{id}")
    Usuario replacePerson(@RequestBody Usuario newUsuario, @PathVariable Integer id,HttpServletResponse response) {

    	Optional<Usuario> usuario = repository.findById(id);
    	if( usuario.isPresent() ) {
    		usuario.get().setNombre(newUsuario.getNombre());
    		return repository.save(usuario.get());    		
    	}
    	
    	this.responseStatus(404,response);
    	
        return null;
    }
    

    @DeleteMapping("/{id}")
    void deleteUsuario(@PathVariable Integer id,HttpServletResponse response) {
    	Optional<Usuario> usuario = repository.findById(id);
    	if( usuario.isPresent() ) {
    		repository.deleteById(id);  
    		this.responseStatus(200,response);
    	}
    	else {
    		this.responseStatus(404,response);    		
    	}
    }
}
