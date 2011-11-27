package es.makestrid.premf.proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

import org.eclipse.emf.ecore.EObject;
import org.prevayler.Prevayler;
import org.prevayler.TransactionWithQuery;

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

	private static class MethodExecuter implements TransactionWithQuery {
		private transient Object receiver;
		private SerializedEMFObject receiverSerialized;
		
		private transient Method method;
		private String methodName;
		private transient Object[] args;
		private Class<?>[] parameterTypes;
		
		private boolean[] argIsEObject;
		private Object[] argsSerialized;
		
		public MethodExecuter(Object receiver, Method method, Object[] args) {
			this.receiver = receiver;
			this.method = method;
			this.args = args;
		}
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime)
				throws Exception {
			Object result = method.invoke(receiver, args);
			return result;
		}
	}
	@Override
	public Object invoke(Object delegate, Method method, Object[] args)
			throws Throwable {
		TransactionWithQuery transaction = new MethodExecuter(delegate, method, args);
		return prevayler.execute(transaction);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ObjectProxy) {
			ObjectProxy container = (ObjectProxy) obj;
			return delegate.equals(container.unwrap());
		}
		return delegate.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

}
