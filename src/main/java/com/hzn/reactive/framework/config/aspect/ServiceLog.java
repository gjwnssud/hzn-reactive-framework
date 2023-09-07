package com.hzn.reactive.framework.config.aspect;

import com.hzn.reactive.framework.constant.Constant;
import com.hzn.reactive.framework.exception.HznException;
import com.hzn.reactive.framework.util.ExceptionLog;
import com.hzn.reactive.framework.util.ExceptionLog.ExceptionInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Order (0)
public class ServiceLog {

	@Around ("execution(public * com.hzn.reactive.framework.api..service..*(..))")
	public Object logging (ProceedingJoinPoint point) throws Throwable {
		Signature signature = point.getSignature ();
		Logger logger = LoggerFactory.getLogger (signature.getDeclaringTypeName ());
		String className = signature.getDeclaringType ().getSimpleName ();
		String methodName = signature.getName ();
		String traceId = MDC.get (Constant.TRACE_ID);
		logger.info ("{} - [{}.{}] start.", traceId, className, methodName);
		StopWatch stopWatch = new StopWatch ();
		stopWatch.start ();
		Object object;
		try {
			object = point.proceed ();
		} catch (HznException e) {
			ExceptionLog.print (e, ExceptionInfo.builder ().className (className).methodName (methodName)
												.lineNumber (e.getStackTrace ()[0].getLineNumber ()).message (e.getMessage ()).build (), logger);
			throw e;
		} catch (Throwable t) {
			ExceptionLog.print (t, logger);
			throw t;
		} finally {
			stopWatch.stop ();
		}
		logger.info ("{} - [{}.{}] end. elapsed time : {} sec", traceId, className, methodName, (double) stopWatch.getLastTaskTimeMillis () / 1000);
		return object;
	}
}
