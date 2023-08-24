package com.hzn.reactive.framework.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {
	private final AuthenticationManager authenticationManager;

	@Override
	public Mono<Void> save (ServerWebExchange exchange, SecurityContext context) {
		throw new UnsupportedOperationException ("Not supported yet.");
	}

	@Override
	public Mono<SecurityContext> load (ServerWebExchange exchange) {
		return Mono.justOrEmpty (exchange.getRequest ().getHeaders ().getFirst (HttpHeaders.AUTHORIZATION))
				.filter (header -> header.startsWith ("Bearer "))
				.flatMap (header -> {
					String token = header.substring (7);
					Authentication authentication = new UsernamePasswordAuthenticationToken (token, token);
					return authenticationManager.authenticate (authentication).map (SecurityContextImpl::new);
				});
	}
}
