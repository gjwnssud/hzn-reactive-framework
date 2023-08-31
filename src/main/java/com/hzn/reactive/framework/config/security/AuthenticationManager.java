package com.hzn.reactive.framework.config.security;

import com.hzn.reactive.framework.config.Jwt;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
	private final Jwt jwt;

	@Override
	@SuppressWarnings ("unchecked")
	public Mono<Authentication> authenticate (Authentication authentication) {
		String token = authentication.getCredentials ().toString ();
		String username = jwt.getUsernameFromToken (token);
		return Mono.just (jwt.validateToken (token)).filter (valid -> valid).switchIfEmpty (Mono.empty ()).map (valid -> {
			Claims claims = jwt.getAllClaimsFromToken (token);
			List<String> roles = claims.get ("role", List.class);
			return new UsernamePasswordAuthenticationToken (username, null,
															roles.stream ().map (SimpleGrantedAuthority::new).collect (Collectors.toList ()));
		});
	}
}
