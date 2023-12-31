package com.hzn.reactive.framework.config.security;

import com.hzn.reactive.framework.enums.Roles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Value ("${allowed.origins}")
	private List<String> allowedOrigins;
	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository securityContextRepository;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain (ServerHttpSecurity http) {
		http.csrf (ServerHttpSecurity.CsrfSpec::disable).cors (corsSpec -> corsSpec.configurationSource (corsConfigurationSource ()))
			.formLogin (ServerHttpSecurity.FormLoginSpec::disable).httpBasic (ServerHttpSecurity.HttpBasicSpec::disable).exceptionHandling (
					exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint (
																		  ((exchange, denied) -> Mono.fromRunnable (() -> exchange.getResponse ().setStatusCode (HttpStatus.UNAUTHORIZED))))
																  .accessDeniedHandler ((exchange, denied) -> Mono.fromRunnable (
																		  () -> exchange.getResponse ().setStatusCode (HttpStatus.FORBIDDEN))))
			.authenticationManager (authenticationManager).securityContextRepository (securityContextRepository).authorizeExchange (
					authorizeExchangeSpec -> authorizeExchangeSpec.matchers (permitAllMatchers ()).permitAll ().matchers (roleAdminMatchers ())
																  .hasRole (Roles.ROLE_ADMIN.name ()).anyExchange ().authenticated ());
		return http.build ();
	}

	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource () {
		UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource ();
		Map<String, CorsConfiguration> configMap = new HashMap<> ();
		CorsConfiguration corsConfiguration = new CorsConfiguration ();
		corsConfiguration.setAllowedOrigins (allowedOrigins);
		corsConfiguration.setAllowedHeaders (List.of ("*"));
		corsConfiguration.setAllowedMethods (List.of ("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
		configMap.put ("/**", corsConfiguration);
		corsConfigurationSource.setCorsConfigurations (configMap);
		return corsConfigurationSource;
	}

	@Bean
	public ServerWebExchangeMatcher[] permitAllMatchers () {
		List<ServerWebExchangeMatcher> matchers = new ArrayList<> ();
		matchers.add (new PathPatternParserServerWebExchangeMatcher ("/", HttpMethod.GET));
		matchers.add (new PathPatternParserServerWebExchangeMatcher ("/favicon.ico", HttpMethod.GET));
		matchers.add (new PathPatternParserServerWebExchangeMatcher ("/login", HttpMethod.GET));
		ServerWebExchangeMatcher[] patterns = new PathPatternParserServerWebExchangeMatcher[matchers.size ()];
		return matchers.toArray (patterns);
	}

	@Bean
	public ServerWebExchangeMatcher[] roleAdminMatchers () {
		List<ServerWebExchangeMatcher> matchers = new ArrayList<> ();
		matchers.add (new PathPatternParserServerWebExchangeMatcher ("/admin/**"));
		ServerWebExchangeMatcher[] patterns = new PathPatternParserServerWebExchangeMatcher[matchers.size ()];
		return matchers.toArray (patterns);
	}
}
