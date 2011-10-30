package es.makestrid.premf.proxies;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.prevayler.Prevayler;

public class ListIteratorProxy<T> extends IteratorProxy<T> implements
		ListIterator<T> {
	
	public static <T> ListIterator<T> wrap(ListIterator<T> delegate, Prevayler prevayler) {
		return new ListIteratorProxy<T>(delegate, prevayler);
	}

	public ListIteratorProxy(ListIterator<T> delegate, Prevayler prevayler) {
		super(delegate, prevayler);
	}
	
	private ListIterator<T> delegate() {
		return (ListIterator<T>) delegate;
	}

	@Override
	public boolean hasPrevious() {
		return delegate().hasPrevious();
	}

	@Override
	public T previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		synchronized(this) {
			--position;
			return delegate().previous();
		}
	}

	@Override
	public int nextIndex() {
		return delegate().nextIndex();
	}

	@Override
	public int previousIndex() {
		return delegate().previousIndex();
	}

	@Override
	public void set(T e) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(T e) {
		//TODO
		throw new UnsupportedOperationException();
	}

}
