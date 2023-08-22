package com.hzn.reactive.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	@Value("${allowed.origins}")
	private List<String> allowedOrigins;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain (ServerHttpSecurity http) {
		http.csrf (ServerHttpSecurity.CsrfSpec::disable)
				.cors (corsSpec -> {
					UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource ();
					Map<String, CorsConfiguration> configMap = new HashMap<> ();
					CorsConfiguration corsConfiguration = new CorsConfiguration ();
					corsConfiguration.setAllowedOrigins (allowedOrigins);
					corsConfiguration.setAllowedHeaders (List.of ("*"));
					corsConfiguration.setAllowedMethods (List.of ("*"));
					configMap.put ("/**", corsConfiguration);
					corsConfigurationSource.setCorsConfigurations (configMap);
					corsSpec.configurationSource (corsConfigurationSource);
				})
				.formLogin (ServerHttpSecurity.FormLoginSpec::disable)
				.httpBasic (ServerHttpSecurity.HttpBasicSpec::disable)
				;
		return http.build ();
	}
}
