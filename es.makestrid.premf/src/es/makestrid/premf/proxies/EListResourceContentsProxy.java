package es.makestrid.premf.proxies;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.prevayler.Prevayler;
import org.prevayler.SureTransactionWithQuery;

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
		private SerializedEMFObject toAddSerialized;
		
		public Add(EObject toAdd, Resource resource) {
			this.toAdd = toAdd;
			toAddSerialized = new SerializedEMFObject(toAdd, resource);
		}
		
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			if (null == toAdd) {
				toAdd = toAddSerialized.get(system);
			}
			return system.getContents().add(toAdd);
		}
	}
	@Override
	public boolean add(T object) {
		EObject toAdd = (EObject) object;
		return (Boolean) getPrevayler().execute(new Add(toAdd, getResource()));
	}

	private static class AddAll implements SureTransactionWithQuery {
		private static final long serialVersionUID = -8239859788533098090L;
		private transient Collection<EObject> toAdd;
		private LinkedList<SerializedEMFObject> toAddSerialized = new LinkedList<SerializedEMFObject>();
		
		public AddAll(Collection<EObject> toAdd, Resource resource) {
			this.toAdd = toAdd;
			for (EObject obj : toAdd) {
				SerializedEMFObject serializedObj = new SerializedEMFObject(obj, resource);
				toAddSerialized.add(serializedObj);
			}
		}
		
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			if (null == toAdd) {
				toAdd = new LinkedList<EObject>();
				for (SerializedEMFObject serializedObj : toAddSerialized) {
					EObject obj = serializedObj.get(system);
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
		SureTransactionWithQuery transaction = new AddAll(collection, getResource());
		return (Boolean) getPrevayler().execute(transaction);
	}

	private static class AddAllAtIndex implements SureTransactionWithQuery {
		private static final long serialVersionUID = -8239859788533098090L;
		private transient Collection<EObject> toAdd;
		private LinkedList<SerializedEMFObject> toAddSerialized = new LinkedList<SerializedEMFObject>();
		private int index;
		
		public AddAllAtIndex(int index, Collection<EObject> toAdd, Resource resource) {
			this.index = index;
			this.toAdd = toAdd;
			for (EObject obj : toAdd) {
				SerializedEMFObject serialized = new SerializedEMFObject(obj, resource);
				toAddSerialized.add(serialized);
			}
		}
		
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource system = (Resource) prevalentSystem;
			if (null == toAdd) {
				toAdd = new LinkedList<EObject>();
				for (SerializedEMFObject serializedObj : toAddSerialized) {
					EObject obj = serializedObj.get(system);
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
		SureTransactionWithQuery transaction = new AddAllAtIndex(index, collection, getResource());
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
	
	@SuppressWarnings("unchecked")
	private static class Set<T> implements SureTransactionWithQuery {
		private static final long serialVersionUID = -7201499776541734978L;
		private int index;
		private transient T element;
		private SerializedEMFObject serializedObject;

		public Set(int index, T element, Resource resource) {
			this.index = index;
			this.element = element;
			this.serializedObject = new SerializedEMFObject((EObject)element, resource);
		}
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource root = (Resource) prevalentSystem;
			if (null == element) {
				element = (T) serializedObject.get(root);
			}
			return root.getContents().set(index, (EObject)element);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public T set(int index, T element) {
		SureTransactionWithQuery transaction = new Set<T>(index, element, getResource());
		return (T) getPrevayler().execute(transaction);
	}

	private static class AddAtIndex<T> implements SureTransactionWithQuery {
		private static final long serialVersionUID = 4823835952393734212L;
		private int index;
		private transient T element;
		private SerializedEMFObject serializedObject;
		
		public AddAtIndex(int index, T element, Resource resource) {
			this.index = index;
			this.element = element;
			this.serializedObject = new SerializedEMFObject((EObject)element, resource);
		}
		@SuppressWarnings("unchecked")
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource root = (Resource) prevalentSystem;
			if (null == element) {
				element = (T) serializedObject.get(root);
			}
			root.getContents().add(index, (EObject) element);
			return null;
		}
	}
	@Override
	public void add(int index, T element) {
		SureTransactionWithQuery transaction = new AddAtIndex<T>(index, element, getResource());
		getPrevayler().execute(transaction);
	}

	private static class Remove implements SureTransactionWithQuery {
		private static final long serialVersionUID = 1L;
		private int index;
		
		public Remove(int index) {
			this.index = index;
		}
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource root = (Resource) prevalentSystem;
			return root.getContents().remove(index);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public T remove(int index) {
		SureTransactionWithQuery transaction = new Remove(index);
		return (T) getPrevayler().execute(transaction);
	}

	private static class RemoveObject implements SureTransactionWithQuery {
		private static final long serialVersionUID = 1L;
		private transient Object element;
		private SerializedEMFObject serializedObject;
		
		public RemoveObject(Object o, Resource resource) {
			this.element = o;
			this.serializedObject = new SerializedEMFObject((EObject)element, resource);
		}
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource root = (Resource) prevalentSystem;
			if (null == element) {
				element = serializedObject.get(root);
			}
			return root.getContents().remove(element);
		}
	}
	@Override
	public boolean remove(Object o) {
		SureTransactionWithQuery transaction = new RemoveObject(o, getResource());
		return (Boolean) getPrevayler().execute(transaction);
	}

	private static class MoveObject implements SureTransactionWithQuery {
		private static final long serialVersionUID = -6809203065064968216L;
		private int newPosition;
		private transient Object element;
		private SerializedEMFObject serializedObject;
		
		public MoveObject(int newPosition, Object object, Resource resource) {
			this.newPosition = newPosition;
			this.element = object;
			this.serializedObject = new SerializedEMFObject((EObject) object, resource);
		}
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource root = (Resource) prevalentSystem;
			if (null == element) {
				element = serializedObject.get(root);
			}
			root.getContents().move(newPosition, (EObject) element);
			return null;
		}
	}
	@Override
	public void move(int newPosition, T object) {
		SureTransactionWithQuery transaction = new MoveObject(newPosition, object, getResource());
		getPrevayler().execute(transaction);
	}

	private static class MoveFromIndex implements SureTransactionWithQuery {
		private static final long serialVersionUID = 452028282408998342L;
		private int newPosition;
		private int oldPosition;
		
		public MoveFromIndex(int newPosition, int oldPosition) {
			this.newPosition = newPosition;
			this.oldPosition = oldPosition;
		}
		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime) {
			Resource root = (Resource) prevalentSystem;
			return root.getContents().move(newPosition, oldPosition);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public T move(int newPosition, int oldPosition) {
		SureTransactionWithQuery transaction = new MoveFromIndex(newPosition, oldPosition);
		return (T) getPrevayler().execute(transaction);
	}

}
