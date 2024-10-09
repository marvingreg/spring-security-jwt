package com.security.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.application.enums.Permission;
import com.security.application.enums.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	//any class that implements this UserDetailsService
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//From this
		//why disable? make http stateless so we won't worry about it, customizer is a functional interface
		//we may implement using anonymous class or just through lambda
//		http.csrf(customizer -> customizer.disable());
//		http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//		//browser
//		http.formLogin(Customizer.withDefaults());
//		//http requests
//		http.httpBasic(Customizer.withDefaults());
//		//Session id would change for every request so we won't have to worry about csrf 
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		return http.build();
		
		//to this - builder pattern
		//What we did here is we disabled csrf token, since we made the http stateless so 
		//we will create a new session id for every request
		
		return http.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request -> 
							request.requestMatchers("register","login") //open links
								   		.permitAll()
				                   .requestMatchers("users")
				                   		.hasAnyRole(
													Role.ADMIN.name(), 
													Role.USER.name()
													) //Role Authentication, 
				                   									  //this one is being identified in UsernamePasswordAuthenticationToken
				                   .requestMatchers(HttpMethod.GET, "*/users")
				                   		.hasAnyAuthority(
				                   				Permission.ADMIN_READ.name(), 
				                   				Permission.USER_READ.name()
				                   				)
								   .requestMatchers(HttpMethod.PUT, "*/users")
					           			.hasAnyAuthority(
					           				Permission.ADMIN_UPDATE.name(),
					           				Permission.USER_READ.name()
					           				)
					           			)				//closed resources
				.httpBasic(Customizer.withDefaults())
			    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)	
			    .build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		// if we want an encoder for the password. 
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		
		return provider;
	}
	
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		
		return config.getAuthenticationManager();
		
	}
	
//	// for testing only of validation of authentication
//	@Bean
//	public UserDetailsService userDetailService() {
//		
//		UserDetails user1 = User
//					.withDefaultPasswordEncoder()
//					.username("marvin")
//					.password("12345")
//					.roles("user")
//					.build();
//		
//		UserDetails user2 = User
//				.withDefaultPasswordEncoder()
//				.username("mary")
//				.password("12345")
//				.roles("admin")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1, user2);
//		
//	}

}
