package com.hzn.reactive.framework.aspect;

import com.hzn.reactive.framework.util.ExceptionLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Order (0)
public class ServiceLog {

	@Around ("execution(public * com.hzn.reactive.framework.api..service..*(..))")
	public Object logging (ProceedingJoinPoint point) {
		Signature signature = point.getSignature ();
		Logger logger = LoggerFactory.getLogger (signature.getDeclaringTypeName ());
		String className = signature.getDeclaringType ().getSimpleName ();
		String methodName = signature.getName ();

		logger.info ("====================== [{}.{}] start. ============================================", className, methodName);
		StopWatch stopWatch = new StopWatch ();
		stopWatch.start ();
		Object object = null;
		try {
			object = point.proceed ();
		} catch (Throwable t) {
			ExceptionLog.printExceptionMessage (t, logger);
		} finally {
			stopWatch.stop ();
		}
		logger.info ("====================== [{}.{}] end. elapsed time : {} sec ======================", className, methodName,
					 (double) stopWatch.getLastTaskTimeMillis () / 1000);
		return object;
	}
}
