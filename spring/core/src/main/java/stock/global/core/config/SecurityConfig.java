package stock.global.core.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost"));
		configuration.addAllowedHeader("*");
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH","PUT" ,"DELETE"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> corsConfigurationSource())
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.anyRequest()
					.permitAll()
			);
		http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);


		return http.build();
	}
}