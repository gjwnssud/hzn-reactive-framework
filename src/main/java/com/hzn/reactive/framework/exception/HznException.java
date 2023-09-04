package com.hzn.reactive.framework.exception;

import com.hzn.reactive.framework.enums.ErrorCodes;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HznException extends RuntimeException {
	private ErrorCodes errorCodes = ErrorCodes.UNKNOWN_ERROR_OCCURRED;

	public HznException () {
	}

	public HznException (ErrorCodes errorCodes) {
		this.errorCodes = errorCodes;
	}

	public HznException (int code, String message) {
		errorCodes.setCode (code);
		errorCodes.setMessage (message);
	}
}
