package es.makestrid.premf.proxies;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.prevayler.Prevayler;
import org.prevayler.SureTransactionWithQuery;

import static es.makestrid.premf.internal.EcoreHelper.serialize;
import static es.makestrid.premf.internal.EcoreHelper.deserialize;

public class EListResourceContentsProxy<T> extends EListProxy<T> {

	public static <T> EList<T> wrap(EList<T> delegate, Prevayler prevayler, Resource root) {
		return new EListResourceContentsProxy<T>(delegate, prevayler, root);
	}

	public EListResourceContentsProxy(EList<T> delegate, Prevayler prevayler,
			Resource root) {
		super(delegate, prevayler, root);
	}
	
	private static class Add implements SureTransactionWithQuery {
		private static final long serialVersionUID = -8239859788533098090L;
		private transient EObject toAdd;
		private String toAddSerialized;
		
		public Add(EObject toAdd) {
			this.toAdd = toAdd;
			toAddSerialized = serialize(toAdd);
		}
		
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			if (null == toAdd) {
				toAdd = deserialize(toAddSerialized);
			}
			system.getContents().add(toAdd);
			return true;
		}
	}

	@Override
	public boolean add(T object) {
//		SureTransactionWithQuery transaction = new SureTransactionWithQuery() {
//			private static final long serialVersionUID = -3792271133280132542L;
//			private String toAddString = serialize(toAdd);
//			@Override
//			public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
//				EObject toAdd2 = deserialize(toAddString);
//				Resource system = (Resource) prevalentSystem;
//				if (EcoreUtil.equals(toAdd2, toAdd)) {
//					system.getContents().add(toAdd);
//				} else {
//					system.getContents().add(toAdd2);
//				}
//				return true;
//			}
//		};
		
		EObject toAdd = (EObject) object;
		boolean result = (Boolean) getPrevayler().execute(new Add(toAdd));
		return result;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		@SuppressWarnings("unchecked")
		final Collection<? extends EObject> collection = (Collection<? extends EObject>) c;
		SureTransactionWithQuery transaction = new SureTransactionWithQuery() {
			private static final long serialVersionUID = -3792271133280132542L;
			@Override
			public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
				Resource system = (Resource) prevalentSystem;
				system.getContents().addAll(collection);
				return true;
			}
		};
		boolean result = (Boolean) getPrevayler().execute(transaction);
		return result;
	}

	@Override
	public boolean addAll(final int index, Collection<? extends T> c) {
		@SuppressWarnings("unchecked")
		final Collection<? extends EObject> collection = (Collection<? extends EObject>) c;
		SureTransactionWithQuery transaction = new SureTransactionWithQuery() {
			private static final long serialVersionUID = -3792271133280132542L;
			@Override
			public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
				Resource system = (Resource) prevalentSystem;
				system.getContents().addAll(index, collection);
				return true;
			}
		};
		boolean result = (Boolean) getPrevayler().execute(transaction);
		return result;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		SureTransactionWithQuery transaction = new SureTransactionWithQuery() {
			private static final long serialVersionUID = -3792271133280132542L;
			@Override
			public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
				Resource system = (Resource) prevalentSystem;
				system.getContents().removeAll(c);
				return true;
			}
		};
		boolean result = (Boolean) getPrevayler().execute(transaction);
		return result;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		SureTransactionWithQuery transaction = new SureTransactionWithQuery() {
			private static final long serialVersionUID = -3792271133280132542L;
			@Override
			public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
				Resource system = (Resource) prevalentSystem;
				system.getContents().retainAll(c);
				return true;
			}
		};
		boolean result = (Boolean) getPrevayler().execute(transaction);
		return result;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T set(int index, T element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void move(int newPosition, T object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T move(int newPosition, int oldPosition) {
		// TODO Auto-generated method stub
		return null;
	}

}
