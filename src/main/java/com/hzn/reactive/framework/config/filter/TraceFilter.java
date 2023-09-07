package com.hzn.reactive.framework.config.filter;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order (0)
@Slf4j
public class TraceFilter implements WebFilter {
	private final String TRACE_ID = "traceId";

	@Override
	public Mono<Void> filter (ServerWebExchange exchange, WebFilterChain chain) {
		return chain.filter (exchange).contextWrite (context -> context.put (TRACE_ID, UUID.randomUUID ().toString ().substring (0, 8)));
	}
}
