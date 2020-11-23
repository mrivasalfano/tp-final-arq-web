package com.tudai.controllers;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

/**
 * Rest Controller de usuarios. Se encarga de recibir
 * peticiones HTTP del cliente, realizar procesos y responder.
 * @author Team-Bolivar
 * @version v1.0
 * @since   2020-11-24
 */
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
    
    /**
     * Recibe un usuario y verifica sus credenciales. En caso
     * de que estén correctas genera un Token
     * @param u usuario que se quiere logear
     * @return string con Token, nombre de usuario y estado
     */
    @PostMapping("/authentication")
    public String authentication(@RequestBody Usuario u){
    	Usuario usu = repository.findByName(u.getNombre());
    	String secretKey = "mySecretKey";
    	String roles = "";
    	
    	if(usu.isAdmin()) {
    		roles = "ADMIN";
    	}
    	else {
    		roles = "USER";
    	}
    	
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(roles);    		
    	
		JSONObject resp = new JSONObject();
		resp.put("status", "Error");
		if(usu != null) {
			if(this.passwordEncoder.matches(u.getClave(), usu.getClave())) {
				String token = Jwts.builder()
						.setSubject("1234567890")
						.claim("authorities",
								grantedAuthorities.stream()
										.map(GrantedAuthority::getAuthority)
										.collect(Collectors.toList()))
						.setId("7edd87d7-9ee7-4b81-ba11-b45bd4278b07")
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + 60 * 10000 * 6)) //esta de 1hs
						.claim("userId", usu.getId())
						.claim("name", usu.getNombre())
						.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
						.compact();
				resp.put("name", usu.getNombre());
				resp.put("admin", usu.isAdmin());
				resp.put("token", "Bearer " + token);
				resp.put("status", "Success");
			}
		}
		return resp.toString();
	}
    
}
