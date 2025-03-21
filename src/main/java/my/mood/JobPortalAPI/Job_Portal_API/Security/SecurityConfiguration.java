package my.mood.JobPortalAPI.Job_Portal_API.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf(csrf -> csrf.disable())
		
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/jobs/**").permitAll()
				.requestMatchers("/register/**").permitAll()
				.requestMatchers("/applications/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/employer/**").hasRole("EMPLOYER")
				.requestMatchers("/jobseeker/**").hasRole("JOB_SEEKER")
				.anyRequest().authenticated()
				)
			
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)
			
			.httpBasic(httpBasic -> {});
		
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		
		UserDetails admin = User.builder()
				.username("Admin")
				.password(passwordEncoder.encode("Admin123"))
				.roles("ADMIN")
				.build();
		
		UserDetails employer = User.builder()
				.username("Employer")
				.password(passwordEncoder.encode("Employer123"))
				.roles("EMPLOYER")
				.build();
		
		UserDetails jobSeeker = User.builder()
				.username("JobSeeker")
				.password(passwordEncoder.encode("JobSeeker123"))
				.roles("JOB_SEEKER")
				.build();
		
		return new InMemoryUserDetailsManager(admin, employer, jobSeeker);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
