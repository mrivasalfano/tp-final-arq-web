package com.tudai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class LoginConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			//.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/").permitAll()
			.antMatchers(HttpMethod.GET, "/css/**").permitAll()
			.antMatchers(HttpMethod.GET, "/js/**").permitAll()
			.antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
			.antMatchers(HttpMethod.GET, "/usuarios/").permitAll()
			.antMatchers(HttpMethod.POST, "/usuarios/").permitAll()
			.antMatchers(HttpMethod.DELETE, "/usuarios/**").permitAll()
			.antMatchers(HttpMethod.POST, "/usuarios/authentication/").permitAll()
			.anyRequest().authenticated();
	}
}