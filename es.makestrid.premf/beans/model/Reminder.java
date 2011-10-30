package es.strid.model;

import es.strid.model.dao.AbstractModelObject;

public abstract class Reminder extends AbstractModelObject {

	private static final long serialVersionUID = 1L;
	
	public abstract boolean isEnabled();

}
