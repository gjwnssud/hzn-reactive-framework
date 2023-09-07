package com.hzn.reactive.framework.util;

import com.hzn.reactive.framework.constant.Constant;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Slf4j
public class ExceptionLog {

	public static String printDefault (Throwable t) {
		return ObjectUtils.isEmpty (t.getMessage ()) ? HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase () : t.getMessage ();
	}

	public static void print (Throwable t) {
		print (t, null, null);
	}

	public static void print (Throwable t, ExceptionInfo exceptionInfo) {
		print (t, exceptionInfo, null);
	}

	public static void print (Throwable t, Logger logger) {
		print (t, null, logger);
	}

	public static void print (Throwable t, ExceptionInfo exceptionInfo, Logger llogger) {
		Logger logger = llogger == null ? log : llogger;
		String className, methodName, message;
		int lineNumber;
		if (exceptionInfo != null) {
			className = exceptionInfo.getClassName ();
			methodName = exceptionInfo.getMethodName ();
			lineNumber = exceptionInfo.getLineNumber ();
			message = exceptionInfo.getMessage ();
		} else {
			StackTraceElement[] stackTraceElements = t.getStackTrace ();
			StackTraceElement stackTraceElement = stackTraceElements[0];
			className = stackTraceElement.getClassName ();
			methodName = stackTraceElement.getMethodName ();
			lineNumber = stackTraceElement.getLineNumber ();
			message = t.getMessage ();
		}
		String traceId = MDC.get (Constant.TRACE_ID);
		logger.error ("{} - [ClassName = {}]", traceId, className);
		logger.error ("{} - [MethodName = {}]", traceId, methodName);
		logger.error ("{} - [LineNumber = {}]", traceId, lineNumber);
		logger.error ("{} - [Message = {}]", traceId, message);
	}

	@Getter
	@Builder
	public static class ExceptionInfo {
		private String className;
		private String methodName;
		private int lineNumber;
		private String message;
	}
}
