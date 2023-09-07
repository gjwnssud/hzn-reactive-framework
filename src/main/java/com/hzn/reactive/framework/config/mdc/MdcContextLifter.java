package com.hzn.reactive.framework.config.mdc;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscription;
import org.slf4j.MDC;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

@RequiredArgsConstructor
public class MdcContextLifter<T> implements CoreSubscriber<T> {
	private final CoreSubscriber<T> coreSubscriber;

	@Override
	public void onSubscribe (Subscription subscription) {
		coreSubscriber.onSubscribe (subscription);
	}

	@Override
	public void onNext (T t) {
		copyToMdc (coreSubscriber.currentContext ());
		coreSubscriber.onNext (t);
	}

	@Override
	public void onError (Throwable throwable) {
		coreSubscriber.onError (throwable);
	}

	@Override
	public void onComplete () {
		coreSubscriber.onComplete ();
	}

	@Override
	public Context currentContext () {
		return coreSubscriber.currentContext ();
	}

	void copyToMdc (Context context) {
		if (context != null && !context.isEmpty ()) {
			Map<String, String> map = context.stream ().collect (Collectors.toMap (e -> e.getKey ().toString (), e -> e.getValue ().toString ()));
			MDC.setContextMap (map);
		} else {
			MDC.clear ();
		}
	}
}
