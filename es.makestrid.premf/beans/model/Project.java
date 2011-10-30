package es.strid.model;

import es.strid.model.dao.AbstractModelObject;

public class Project extends AbstractModelObject {
	private static final long serialVersionUID = 1L;
	public static final String PROP_NAME = "name";
	public static final String PROP_NOTES = "notes";
	public static final String PROP_STATUS = "status";
	public static final String PROP_AREA = "area";
	
	String name = "";
	String notes = "";
	TaskStatus status = TaskStatus.NEXT;
	Area area = null;
	
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
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		String oldValue = this.notes;
		this.notes = notes;
		firePropertyChange(PROP_NOTES, oldValue, notes);
	}
	/**
	 * @return the status
	 */
	public TaskStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatus status) {
		TaskStatus oldValue = this.status;
		this.status = status;
		firePropertyChange(PROP_STATUS, oldValue, status);
	}
	/**
	 * @return the area or null if none.
	 */
	public Area getArea() {
		return area;
	}
	/**
	 * @param area the area to set.  May be null.
	 */
	public void setArea(Area area) {
		Area oldValue = this.area;
		this.area = area;
		firePropertyChange(PROP_AREA, oldValue, area);
	}
	
	
}
