package es.strid.model.dao;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.prevayler.Query;
import org.prevayler.Transaction;
import org.prevayler.TransactionWithQuery;

import es.strid.model.Area;
import es.strid.model.ContextOrLocation;
import es.strid.model.Project;
import es.strid.model.Reminder;
import es.strid.model.SPreferences;
import es.strid.model.Sequence;
import es.strid.model.Task;

import android.util.Log;


public class PrevaylerDatabase extends Database implements Serializable {
	private static final long serialVersionUID = 0L;
	
	public static String dataDirectory = "taskli.st";
	
	private transient Prevayler prevayler = null;
	private transient SnapshotThread snapshotThread = null;
	
	public PrevaylerDatabase() {
		prevayler = PrevaylerFactory.createTransientPrevayler(this);
	}
	
	public PrevaylerDatabase(String persistenceDir) {
        PrevaylerFactory factory = new PrevaylerFactory();
		factory.configurePrevalentSystem(this);
        factory.configureTransactionFiltering(false);
        factory.configurePrevalenceDirectory(persistenceDir);
        try {
			prevayler = factory.create();
		} catch (Exception e) {
			Log.e(getClass().getName(), "Unexpected exception creating Prevayler", e);
			throw new RuntimeException("Unexpected exception creating Prevayler", e);
		}
        snapshotThread = new SnapshotThread(prevayler, persistenceDir);
        snapshotThread.start();
	}
	
	public void checkpoint() {
		try {
			prevayler.takeSnapshot();
			cleanupJournalFiles();
		} catch (IOException e) {
			Log.e(getClass().getName(), e.getMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see es.strid.model.dao.IDatabase#close()
	 */
	public void close() {
		if (snapshotThread != null) {
			snapshotThread.terminated = true;
			snapshotThread.interrupt();
		}
		prevayler = null;
		snapshotThread = null;
	}
	
	private int sequence(String filename) {
		return Integer.parseInt(filename.substring(0, filename.indexOf('.')));
	}
	
	private void cleanupJournalFiles() {
		int highestSnapshot = -1;
		File snapshotDir = new File(dataDirectory);
		ArrayList<String> snapshotFiles = new ArrayList<String>();
		for (String file : snapshotDir.list()) {
			if (file.endsWith(".journal")) {
				new File(snapshotDir.getAbsolutePath() + "/" + file).delete();
			}
			if (file.endsWith(".snapshot")) {
				snapshotFiles.add(file);
				int fileID = sequence(file);
				if (fileID > highestSnapshot) {
					highestSnapshot = fileID;
				}
			}
		}
		for (int i = 0; i < snapshotFiles.size(); i++) {
			String file = snapshotFiles.get(i);
			if (sequence(file) < highestSnapshot) {
				new File(snapshotDir.getAbsolutePath() + "/" + file).delete();
			}
		}
	}

	private static class SnapshotThread extends Thread {
		private Prevayler prevayler = null;
		private String persistenceDir;
		public boolean terminated = false;

		public SnapshotThread(Prevayler prevayler, String persistenceDir) {
			this.prevayler = prevayler;
			this.persistenceDir = persistenceDir;
		}

		@Override
		public void run() {
			while (!terminated) {
				try {
					sleep(1000 * 60 * 6);
				} catch (InterruptedException e) {
				}
                try {
                	if (journalFilesExist()) {
						prevayler.takeSnapshot();
                	}
				} catch (IOException e) {
					Log.e(getClass().getName(), "Unable to checkpoint", e);
					throw new RuntimeException(e);
				}
			}
		}

		private boolean journalFilesExist() {
			File snapshotDir = new File(persistenceDir);
			for (String file : snapshotDir.list()) {
				if (file.endsWith(".journal")) {
					return true;
				}
			}
			return false;
		}
	}
	
	Sequence nextID = new Sequence();

	TreeMap<Long, Area> areas = new TreeMap<Long, Area>();
	TreeMap<Long, Project> projects = new TreeMap<Long, Project>();
	TreeMap<Long, ContextOrLocation> contexts = new TreeMap<Long, ContextOrLocation>();
	TreeMap<Long, Task> tasks = new TreeMap<Long, Task>();
	TreeMap<Long, Reminder> reminders = new TreeMap<Long, Reminder>();
	
	SPreferences preferences = new SPreferences();
	
	private static final Class<?>[] supportedClasses = new Class[] {
		Area.class, 
		Project.class, 
		ContextOrLocation.class, 
		Task.class, 
		Reminder.class
	};

	private Class<?> getClass(String className) {
		for (int i = 0; i < supportedClasses.length; i++) {
			if (className.equals(supportedClasses[i].getName())) {
				return supportedClasses[i];
			}
		}
		throw new RuntimeException(className + " is not a supported class.");
	}
	
	@SuppressWarnings("unchecked")
	private TreeMap<Long, AbstractModelObject> getTypeMap(String className) {
		TreeMap<Long, AbstractModelObject> resultMap = (TreeMap<Long, AbstractModelObject>) dataStore.get(className);
		return resultMap;
	}
	
	TreeMap<String, TreeMap<Long, ? extends AbstractModelObject>> dataStore = new TreeMap<String, TreeMap<Long, ? extends AbstractModelObject>>(); {
		dataStore.put(Area.class.getName(), areas);
		dataStore.put(Project.class.getName(), projects);
		dataStore.put(ContextOrLocation.class.getName(), contexts);
		dataStore.put(Task.class.getName(), tasks);
		dataStore.put(Reminder.class.getName(), reminders);
	}

	// -------------------------------------------------------------------------------------------------------
	
	private static class Insert implements TransactionWithQuery {
		private static final long serialVersionUID = 1L;
		private String className;

		public Insert(String className) {
			this.className = className;
		}

		@Override
		public Object executeAndQuery(Object prevalentSystem, Date executionTime)
				throws Exception {
			PrevaylerDatabase dao = (PrevaylerDatabase) prevalentSystem;
			Class<?> intoClass = dao.getClass(className);
			AbstractModelObject result = null;
			try {
				result = (AbstractModelObject) intoClass.newInstance();
				long resultID = dao.nextID.getNextValue();
				result.setId(resultID);
				TreeMap<Long, AbstractModelObject> resultMap = dao.getTypeMap(className);
				resultMap.put(resultID, result);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			}
			return result;
		}
	}
	
	/* (non-Javadoc)
	 * @see es.strid.model.dao.IDatabase#insert(java.lang.String)
	 */
	public AbstractModelObject insert(String className) {
		TransactionWithQuery insertCommand = new Insert(className);
		try {
			return (AbstractModelObject) prevayler.execute(insertCommand);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class Delete implements Transaction {
		private static final long serialVersionUID = 1L;
		private String className;
		private Long id;

		public Delete(String className, Long id) {
			this.className = className;
			this.id = id;
		}

		@Override
		public void executeOn(Object prevalentSystem, Date executionTime) {
			PrevaylerDatabase dao = (PrevaylerDatabase) prevalentSystem;
			TreeMap<Long, AbstractModelObject> collection = dao.getTypeMap(className);
			AbstractModelObject removedObject = collection.remove(id);
			if (removedObject == null) {
				throw new RuntimeException(className + ", ID=" + id + " does not exist");
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see es.strid.model.dao.IDatabase#delete(es.strid.model.dao.AbstractModelObject)
	 */
	public void delete(AbstractModelObject object) {
		delete(object.getClass().getName(), object.getId());
	}
	
	/* (non-Javadoc)
	 * @see es.strid.model.dao.IDatabase#delete(java.lang.String, java.lang.Long)
	 */
	public void delete(String className, Long id) {
		Transaction deleteCommand = new Delete(className, id);
		try {
			prevayler.execute(deleteCommand);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static class SelectWhere implements Query {
		private String className;
		private WhereClause<? extends AbstractModelObject> where;

		public SelectWhere(String className,
				WhereClause<? extends AbstractModelObject> where) {
			this.className = className;
			this.where = where;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object query(Object prevalentSystem, Date executionTime)
				throws Exception {
			PrevaylerDatabase dao = (PrevaylerDatabase) prevalentSystem;
			ArrayList<AbstractModelObject> result = new ArrayList<AbstractModelObject>();
			TreeMap<Long, AbstractModelObject> collection = dao.getTypeMap(className);
			WhereClause<AbstractModelObject> wherePredicate = (WhereClause<AbstractModelObject>) where;
			
			for (Iterator<Long> items = collection.keySet().iterator(); items.hasNext();) {
				AbstractModelObject object = collection.get(items.next());
				if (wherePredicate.include(object)) {
					result.add(object);
				}
			}
			return result;
		}
	}
	
	/* (non-Javadoc)
	 * @see es.strid.model.dao.IDatabase#select(java.lang.String, es.strid.model.dao.WhereClause)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<AbstractModelObject> select(String className, 
			WhereClause<? extends AbstractModelObject> where) 
	{
		Query selectCommand = new SelectWhere(className, where);
		try {
			return (ArrayList<AbstractModelObject>) prevayler.execute(selectCommand);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class SelectID implements Query {
		private String className;
		private Long whereID;

		public SelectID(String className, Long whereID) {
			this.className = className;
			this.whereID = whereID;
		}

		@Override
		public Object query(Object prevalentSystem, Date executionTime)
				throws Exception {
			PrevaylerDatabase dao = (PrevaylerDatabase) prevalentSystem;
			TreeMap<Long, AbstractModelObject> collection = dao.getTypeMap(className);
			AbstractModelObject result = collection.get(whereID);
			if (result == null) throw new RuntimeException("Could not find " + whereID + " of type " + className);
			return result;
		}
	}
	
	/* (non-Javadoc)
	 * @see es.strid.model.dao.IDatabase#select(java.lang.String, java.lang.Long)
	 */
	public AbstractModelObject select(String className, Long whereID) {
		Query selectCommand = new SelectID(className, whereID);
		try {
			return (AbstractModelObject) prevayler.execute(selectCommand);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class Update implements Transaction {
		private static final long serialVersionUID = 1L;
		private AbstractModelObject object;

		public Update(AbstractModelObject object) {
			this.object = object;
		}

		@Override
		public void executeOn(Object prevalentSystem, Date executionTime) {
			PrevaylerDatabase dao = (PrevaylerDatabase) prevalentSystem;
			final String className = object.getClass().getName();
			final Long objectID = object.getId();
			
			TreeMap<Long, AbstractModelObject> collection = dao.getTypeMap(className);
			if (collection.get(objectID) == null) {
				throw new RuntimeException(className + ", ID=" + objectID + "not found");
			}
			collection.put(objectID, object);
		}
	}

	/* (non-Javadoc)
	 * @see es.strid.model.dao.IDatabase#update(es.strid.model.dao.AbstractModelObject)
	 */
	public void update(AbstractModelObject object) {
		Transaction updateTransaction = new Update(object);
		try {
			prevayler.execute(updateTransaction);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

