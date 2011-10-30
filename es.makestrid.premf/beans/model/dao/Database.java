/**
 * 
 */
package es.strid.model.dao;

import java.util.ArrayList;

/**
 * @author djo
 *
 */
public abstract class Database {

	private static Database database = null;
	public static synchronized Database instance() {
		if (!isOpen()) {
			throw new RuntimeException("Database is not open");
		}
		return database;
	}
	
	public static void setDatabase(Database database) {
		Database.database = database;
	}
	
	public static synchronized boolean isOpen() {
		return database != null;
	}
	
	public abstract void close();

	public abstract void delete(AbstractModelObject object);

	public abstract void delete(String className, Long id);

	public abstract AbstractModelObject insert(String className);

	public abstract ArrayList<AbstractModelObject> select(String className, WhereClause<? extends AbstractModelObject> where);

	public abstract AbstractModelObject select(String className, Long whereID);

	public abstract void update(AbstractModelObject object);
}
