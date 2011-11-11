package es.makestrid.premf.proxies;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.prevayler.Prevayler;

public abstract class EListProxy<T> implements EList<T>, Iterable<T> {
	
	private Resource resource;
	private EList<T> delegate;
	private Prevayler prevayler;
	
	public EListProxy(EList<T> delegate, Prevayler prevayler, Resource resource) {
		this.delegate = delegate;
		this.prevayler = prevayler;
		this.resource = resource;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public EList<T> getDelegate() {
		return delegate;
	}
	
	public Prevayler getPrevayler() {
		return prevayler;
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
	@SuppressWarnings("rawtypes")
	public boolean containsAll(Collection c) {
		return delegate.containsAll(c);
	}

	@Override
	public T get(int index) {
		return delegate.get(index);
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
		// TODO
		return ListIteratorProxy.wrap(delegate.listIterator(), prevayler);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO
		return ListIteratorProxy.wrap(delegate.listIterator(index), prevayler);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return delegate.subList(fromIndex, toIndex);
	}

}
