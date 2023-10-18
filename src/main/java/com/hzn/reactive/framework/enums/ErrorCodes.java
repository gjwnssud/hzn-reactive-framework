package com.hzn.reactive.framework.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
@SuppressWarnings ({"all"})
public enum ErrorCodes {
	UNKNOWN_ERROR_OCCURRED (9999, "An unknown error occurred.");

	@Setter
	private int code;
	@Setter
	private String message;

	ErrorCodes (int code, String message) {
		this.code = code;
		this.message = message;
	}
}
