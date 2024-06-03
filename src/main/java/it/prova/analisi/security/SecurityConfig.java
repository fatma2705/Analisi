package it.prova.analisi.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JWTFilter jwtFilter;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JWTAuthEntryPoint unauthorizedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	 @Bean // Indicates that this method returns a Spring bean.
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	                .csrf(csrf -> csrf.disable()) // Disables CSRF protection, common in stateless REST APIs.
	                .cors(cors -> cors.disable())
	                .authorizeHttpRequests(authorize -> authorize
	                        .anyRequest().authenticated() // Ensures all requests are authenticated.
	                )
	                .httpBasic(withDefaults()) // Enables HTTP Basic Authentication with default settings.
	                .sessionManagement(session -> session
	                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Configures session management to be stateless.
	        return http.build(); // Builds and returns the SecurityFilterChain.
	    }

	
	}
