package com.hzn.reactive.framework.exception;

import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
	@Override
	public Map<String, Object> getErrorAttributes (ServerRequest request, ErrorAttributeOptions options) {
		Map<String, Object> errorAttributes = super.getErrorAttributes (request, options);
		Throwable t = getError (request);
		if (t instanceof HznException hznException) {
			errorAttributes.put ("code", hznException.getErrorCodes ().getCode ());
			errorAttributes.put ("message", hznException.getErrorCodes ().getMessage ());
		}
		return errorAttributes;
	}
}
