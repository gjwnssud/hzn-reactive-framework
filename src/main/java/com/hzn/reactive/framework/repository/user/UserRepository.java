package com.hzn.reactive.framework.repository.user;

import com.hzn.reactive.framework.document.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
	Mono<Boolean> existsByUsername (String username);

	Mono<User> findByUsername (String username);
}
