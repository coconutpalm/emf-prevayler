package es.makestrid.premf.proxies;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.EList;
import org.prevayler.Prevayler;

public class EListProxy<T> implements EList<T> {
	
	public static <T> EList<T> wrap(EList<T> delegate, Prevayler prevayler) {
		return new EListProxy<T>(delegate, prevayler);
	}

	private EList<T> delegate;
	private Prevayler prevayler;
	
	public EListProxy(EList<T> delegate, Prevayler prevayler) {
		this.delegate = delegate;
		this.prevayler = prevayler;
	}
	
	public EList<T> unwrap() {
		return delegate;
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return IteratorProxy.wrap(delegate.iterator(), prevayler);
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[delegate.size()];
		Iterator<T> contents = delegate.iterator();
		for (int i = 0; i < result.length; i++) {
			result[i] = ObjectProxy.wrap(contents.next(), prevayler);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] result) {
		Iterator<T> contents = delegate.iterator();
		for (int i = 0; i < result.length; i++) {
			result[i] = ObjectProxy.wrap(contents.next(), prevayler);
		}
		return result;
	}

	@Override
	public boolean add(Object e) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean containsAll(Collection c) {
		return delegate.containsAll(c);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean addAll(Collection c) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean addAll(int index, Collection c) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean removeAll(Collection c) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean retainAll(Collection c) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public T get(int index) {
		return delegate.get(index);
	}

	@Override
	public T set(int index, Object element) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, Object element) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int index) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o) {
		return delegate.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return ListIteratorProxy.wrap(delegate.listIterator(), prevayler);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return ListIteratorProxy.wrap(delegate.listIterator(index), prevayler);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return delegate.subList(fromIndex, toIndex);
	}

	@Override
	public void move(int newPosition, Object object) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public T move(int newPosition, int oldPosition) {
		//TODO
		throw new UnsupportedOperationException();
	}

}
