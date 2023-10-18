package com.hzn.reactive.framework.api.v1.controller.user;

import com.hzn.reactive.framework.api.v1.service.user.UserService;
import com.hzn.reactive.framework.config.PBKDF2Encoder;
import com.hzn.reactive.framework.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * <p></p>
 *
 * @author hzn
 * @date 2023-10-18
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserService userService;
	private final PBKDF2Encoder pbkdf2Encoder;

	@PostMapping("/signin")
	public Mono<AuthResponse> signin () {
		return null;
	}
}
