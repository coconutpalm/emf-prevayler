package es.makestrid.premf.proxies;

import org.eclipse.emf.common.util.TreeIterator;
import org.prevayler.Prevayler;

public class TreeIteratorProxy<T> implements TreeIterator<T> {
	
	@SuppressWarnings("unchecked")
	public static <T> TreeIterator<T> wrap(TreeIterator<T> delegate, Prevayler prevayler) {
		return new TreeIteratorProxy<T>(delegate, prevayler);
	}

	private TreeIterator<T> delegate;
	private Prevayler prevayler;
	
	public TreeIteratorProxy(TreeIterator<T> delegate, Prevayler prevayler) {
		this.delegate = delegate;
		this.prevayler = prevayler;
	}
	
	public TreeIterator<T> unwrap() {
		return delegate;
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public T next() {
		return delegate.next();
	}

	@Override
	public void remove() {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void prune() {
		//TODO
		throw new UnsupportedOperationException();
	}


}
