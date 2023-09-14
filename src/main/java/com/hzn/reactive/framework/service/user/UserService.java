package com.hzn.reactive.framework.service.user;

import com.hzn.reactive.framework.document.user.User;
import com.hzn.reactive.framework.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public Mono<User> findByUsername (String username) {
		return userRepository.findByUsername (username);
	}

	public Mono<User> save (User user) {
		return userRepository.save (user);
	}
}
