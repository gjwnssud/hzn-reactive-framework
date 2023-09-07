package com.hzn.reactive.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Slf4j
public class ExceptionLog {

	public static String printDefaultMessage (Throwable t) {
		return ObjectUtils.isEmpty (t.getMessage ()) ? HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase () : t.getMessage ();
	}

	public static void printExceptionMessage (Throwable t) {
		printExceptionMessage (t, null, null);
	}

	public static void printExceptionMessage (Throwable t, String invokedMethodName) {
		printExceptionMessage (t, invokedMethodName, null);
	}

	public static void printExceptionMessage (Throwable t, Logger logger) {
		printExceptionMessage (t, null, logger);
	}

	public static void printExceptionMessage (Throwable t, String invokedMethodName, Logger _logger) {
		StackTraceElement[] stackTraceElements = t.getStackTrace ();
		if (!ObjectUtils.isEmpty (stackTraceElements)) {
			Logger logger = _logger == null ? log : _logger;
			StackTraceElement stackTraceElement = stackTraceElements[0];
			if (!ObjectUtils.isEmpty (invokedMethodName)) {
				logger.error ("====================== [{}] start. ======================", invokedMethodName);
			}
			logger.error ("ClassName = {}", stackTraceElement.getClassName ());
			logger.error ("MethodName = {}", stackTraceElement.getMethodName ());
			logger.error ("LineNumber = {}", stackTraceElement.getLineNumber ());
			logger.error ("Message = {}", t.getMessage ());
			if (!ObjectUtils.isEmpty (invokedMethodName)) {
				logger.error ("====================== [{}] end. =======================", invokedMethodName);
			}
		}
	}
}
