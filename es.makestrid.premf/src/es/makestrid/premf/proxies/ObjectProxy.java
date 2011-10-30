package es.makestrid.premf.proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.eclipse.emf.ecore.EObject;
import org.prevayler.Prevayler;

public class ObjectProxy<T> implements InvocationHandler {
	
	public static <T> T wrap(T delegate, Prevayler prevayler) {
		@SuppressWarnings("unchecked")
		T result = (T) Proxy.newProxyInstance(ObjectProxy.class.getClassLoader(),
				new Class<?>[] { EObject.class }, new ObjectProxy<T>(delegate, prevayler));
		return result;
	}

	private T delegate;
	private Prevayler prevayler;
	
	public ObjectProxy(T delegate, Prevayler prevayler) {
		this.delegate = delegate;
		this.prevayler = prevayler;
	}
	
	public T unwrap() {
		return delegate;
	}

	@Override
	public Object invoke(Object delegate, Method method, Object[] args)
			throws Throwable {
		return method.invoke(delegate, args);
	}

}
