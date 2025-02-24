package com.dpkprojects.app.photoapp.api.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.security.PermitAll;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //spring boot automatically creates object for this and puts in application context
@EnableWebSecurity
public class WebSecurity {
	@Bean //spring boot will calls this method automatically whenever needed
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST,"/users").permitAll()
		.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().frameOptions().disable();

		return http.build();
	}
}
