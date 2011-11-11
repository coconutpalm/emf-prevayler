package es.makestrid.premf.proxies;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

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

	private static Collection<String> objectList2uriList(Collection<?> objectList, Resource root) {
		Collection<String> objectsToRetain = new LinkedList<String>();
		for (Object o : objectList) {
			String uriFragment = root.getURIFragment((EObject) o);
			objectsToRetain.add(uriFragment);
		}
		return objectsToRetain;
	}

	private static Collection<EObject> uriList2objectList(Collection<String> uris, Resource system) {
		Collection<EObject> toRetain = new LinkedList<EObject>();
		for (String uriFragment : uris) {
			EObject obj = system.getEObject(uriFragment);
			toRetain.add(obj);
		}
		return toRetain;
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
			return system.getContents().add(toAdd);
		}
	}
	@Override
	public boolean add(T object) {
		EObject toAdd = (EObject) object;
		return (Boolean) getPrevayler().execute(new Add(toAdd));
	}

	private static class AddAll implements SureTransactionWithQuery {
		private static final long serialVersionUID = -8239859788533098090L;
		private transient Collection<EObject> toAdd;
		private LinkedList<String> toAddSerialized = new LinkedList<String>();
		
		public AddAll(Collection<EObject> toAdd) {
			this.toAdd = toAdd;
			for (EObject obj : toAdd) {
				String serializedObj = serialize(obj);
				toAddSerialized.add(serializedObj);
			}
		}
		
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			if (null == toAdd) {
				toAdd = new LinkedList<EObject>();
				for (String serializedObj : toAddSerialized) {
					EObject obj = deserialize(serializedObj);
					toAdd.add(obj);
				}
			}
			return system.getContents().addAll(toAdd);
		}
	}
	@Override
	public boolean addAll(Collection<? extends T> c) {
		@SuppressWarnings("unchecked")
		final Collection<EObject> collection = (Collection<EObject>) c;
		SureTransactionWithQuery transaction = new AddAll(collection);
		return (Boolean) getPrevayler().execute(transaction);
	}

	private static class AddAllAtIndex implements SureTransactionWithQuery {
		private static final long serialVersionUID = -8239859788533098090L;
		private transient Collection<EObject> toAdd;
		private LinkedList<String> toAddSerialized = new LinkedList<String>();
		private int index;
		
		public AddAllAtIndex(int index, Collection<EObject> toAdd) {
			this.index = index;
			this.toAdd = toAdd;
			for (EObject obj : toAdd) {
				String serializedObj = serialize(obj);
				toAddSerialized.add(serializedObj);
			}
		}
		
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			if (null == toAdd) {
				toAdd = new LinkedList<EObject>();
				for (String serializedObj : toAddSerialized) {
					EObject obj = deserialize(serializedObj);
					toAdd.add(obj);
				}
			}
			return system.getContents().addAll(index, toAdd);
		}
	}
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		@SuppressWarnings("unchecked")
		final Collection<EObject> collection = (Collection<EObject>) c;
		SureTransactionWithQuery transaction = new AddAllAtIndex(index, collection);
		return (Boolean) getPrevayler().execute(transaction);
	}

	private static class RemoveAll implements SureTransactionWithQuery {
		private static final long serialVersionUID = -3222939003865965568L;
		private transient Collection<?> c;
		private Collection<String> objectsToRemove;
		
		public RemoveAll(Collection<?> c, Resource root) {
			this.c = c;
			objectsToRemove = objectList2uriList(c, root);
		}
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			if (null == c) {
				c = uriList2objectList(objectsToRemove, system);
			}
			return system.getContents().removeAll(c);
		}
	}
	@Override
	public boolean removeAll(final Collection<?> c) {
		SureTransactionWithQuery transaction = new RemoveAll(c, getResource());
		return (Boolean) getPrevayler().execute(transaction);
	}

	private static class RetainAll implements SureTransactionWithQuery {
		private static final long serialVersionUID = -3792271133280132542L;
		private transient Collection<?> c;
		private Collection<String> objectsToRetain;
		
		public RetainAll(Collection<?> c, Resource root) {
			this.c = c;
			objectsToRetain = objectList2uriList(c, root);
		}
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			if (null == c) {
				c = uriList2objectList(objectsToRetain, system);
			}
			return system.getContents().retainAll(c);
		}
	}
	@Override
	public boolean retainAll(final Collection<?> c) {
		SureTransactionWithQuery transaction = new RetainAll(c, getResource());
		return (Boolean) getPrevayler().execute(transaction);
	}

	private static class Clear implements SureTransactionWithQuery {
		private static final long serialVersionUID = 150406864428064903L;
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			system.getContents().clear();
			return null;
		}
	}
	@Override
	public void clear() {
		SureTransactionWithQuery transaction = new Clear();
		getPrevayler().execute(transaction);
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
