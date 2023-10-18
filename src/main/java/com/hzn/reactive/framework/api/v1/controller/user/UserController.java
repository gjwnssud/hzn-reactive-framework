package com.hzn.reactive.framework.api.v1.controller.user;

import com.hzn.reactive.framework.api.v1.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @author hzn
 * @date 2023-10-18
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

}
