package com.tudai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Se encarga de habilitar el filtro por TOKEN y a su vez
 * dejar algunas rutas libre de autorización
 * @author Team-Bolivar
 * @version v1.0
 * @since   2020-11-17
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LoginConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/").permitAll()
			.antMatchers(HttpMethod.GET, "/css/**").permitAll()
			.antMatchers(HttpMethod.GET, "/js/**").permitAll()
			.antMatchers(HttpMethod.GET, "/usuarios/").permitAll()
//			.antMatchers(HttpMethod.GET, "/home/").permitAll()
//			.antMatchers(HttpMethod.GET, "/home2.html").permitAll()
			.antMatchers(HttpMethod.POST, "/usuarios/").permitAll()
			.antMatchers(HttpMethod.DELETE, "/usuarios/**").permitAll()
			.antMatchers(HttpMethod.POST, "/usuarios/authentication/").permitAll()
//			.antMatchers(HttpMethod.GET,"/v2/api-docs").permitAll()
//			.antMatchers(HttpMethod.GET,"/configuration/ui").permitAll()
//			.antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
//			.antMatchers(HttpMethod.GET,"/configuration/security").permitAll()
//			.antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
//			.antMatchers(HttpMethod.GET,"/webjars/**").permitAll()
//			.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
	        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
            .permitAll()
			.anyRequest().authenticated();
		
	     
	}
}