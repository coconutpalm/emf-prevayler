package es.makestrid.premf.proxies;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.prevayler.Prevayler;

public class IteratorProxy<T> implements Iterator<T> {
	
	public static <T> IteratorProxy<T> wrap(Iterator<T> delegate, Prevayler prevayler) {
		return new IteratorProxy<T>(delegate, prevayler);
	}

	protected int position = 0;
	protected Iterator<T> delegate;
	protected Prevayler prevayler;
	
	public IteratorProxy(Iterator<T> delegate, Prevayler prevayler) {
		this.delegate = delegate;
		this.prevayler = prevayler;
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		synchronized(this) {
			++position;
			return ObjectProxy.wrap(delegate.next(), prevayler);
		}
	}

	@Override
	public void remove() {
		//TODO
		throw new UnsupportedOperationException();
	}

}
