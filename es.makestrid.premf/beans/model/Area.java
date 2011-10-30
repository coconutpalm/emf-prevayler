package es.strid.model;

import es.strid.model.dao.AbstractModelObject;

public class Area extends AbstractModelObject {
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME = "name";
	
	String name;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange(PROP_NAME, oldValue, name);
	}
	
}
