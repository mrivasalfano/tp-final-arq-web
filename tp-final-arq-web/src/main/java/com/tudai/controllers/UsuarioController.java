package com.tudai.controllers;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends Controller {
	
    @Qualifier("usuarioRepository")
    @Autowired
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioController(@Qualifier("usuarioRepository") UsuarioRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    @GetMapping("/")
    public Iterable<Usuario> getUsuario() {
    	return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Usuario> getUsuario(@PathVariable int id) {
    	return repository.findById(id);
    }

    //queda ver la clase de Juan de como implementarlo
//    @RequestMapping(value="/login",
//            method=RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	public String createRole(@RequestBody MultiValueMap<String, String> formData){
//		String nombre = formData.getFirst("usuario");
//		String contraseña = formData.getFirst("contraseña");
//		System.out.println(formData);
//		Usuario usu = repository.findByName(nombre);
//		if(usu != null) {
//			System.out.println(usu.getContraseña().equals(contraseña));
//		}
//		System.out.println("USUARIO syso: " + usu);
//    	return null;
//	}
    
        
    @PostMapping("/")
    public Usuario newUsuario(@RequestBody Usuario u) {
    	Usuario usu = repository.findByName(u.getNombre());
    	if(usu != null) {
    		return null;
    	}
    	u.setClave(this.passwordEncoder.encode(u.getClave()));
    	return repository.save(u);    		
    }
    
    @PutMapping("/{id}")
    Usuario replaceUsuario(@RequestBody Usuario newUsuario, @PathVariable Integer id,HttpServletResponse response) {

    	Optional<Usuario> usuario = repository.findById(id);
    	if( usuario.isPresent() ) {
    		usuario.get().setNombre(newUsuario.getNombre());
    		usuario.get().setClave(newUsuario.getClave());
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
    
    @PostMapping("/authentication")
    public String authentication(@RequestBody Usuario u){
		Usuario usu = repository.findByName(u.getNombre());
		JSONObject resp = new JSONObject();
		if(usu != null) {
			if(this.passwordEncoder.matches(u.getClave(), usu.getClave())) {
				System.out.println("Entro");
				String token = Jwts.builder()
//						.setSubject("1234567890")
//						.setId("7edd87d7-9ee7-4b81-ba11-b45bd4278b07")
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + 60 * 10000))
						.claim("id", usu.getId())
						.claim("name", usu.getNombre())
						.signWith(SignatureAlgorithm.HS512, "secret".getBytes())
						.compact();
				resp.put("name", u.getNombre());
				resp.put("token", "Bearer " + token);
				resp.put("status", "Success");
			}
			else {
				resp.put("status", "Error");
			}
		}
		else {
			resp.put("status", "Error");			
		}
		return resp.toString();
	}
    
}
