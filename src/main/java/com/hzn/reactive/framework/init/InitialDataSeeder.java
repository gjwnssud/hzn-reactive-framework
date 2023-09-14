package com.hzn.reactive.framework.init;

import com.hzn.reactive.framework.service.user.UserService;
import com.hzn.reactive.framework.config.PBKDF2Encoder;
import com.hzn.reactive.framework.document.user.User;
import com.hzn.reactive.framework.enums.Roles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialDataSeeder implements ApplicationListener<ApplicationStartedEvent> {
	private final UserService userService;
	private final PBKDF2Encoder passwordEncoder;

	@Override
	public void onApplicationEvent (ApplicationStartedEvent event) {
		userService.findByUsername ("admin").switchIfEmpty (createAdminUser ()).subscribe ();
	}

	private Mono<User> createAdminUser () {
		User user = User.builder ().id (UUID.randomUUID ().toString ()).username ("admin").password (passwordEncoder.encode ("admin"))
						.roles (List.of (Roles.ROLE_ADMIN)).build ();
		return userService.save (user).doOnNext (u -> log.info ("Admin user created successfully"));
	}
}
