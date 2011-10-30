package es.strid.model;

import es.strid.model.dao.AbstractModelObject;

public class Task extends AbstractModelObject {
	private static final long serialVersionUID = 1L;
	
	public static final String[] priorityStrings = {"1", "2", "3", "4", "5"};
	public static final int[] priorities = {1, 2, 3, 4, 5};

	public static final String PROP_DONE = "done";
	public static final String PROP_PARENT_TASK = "parentTask";
	public static final String PROP_PRIORITY = "priority";
	public static final String PROP_NAME = "name";
	public static final String PROP_NOTES = "notes";
	public static final String PROP_STATUS = "status";
	public static final String PROP_PROJECT = "project";
	public static final String PROP_CONTEXT = "context";
	public static final String PROP_STARRED = "starred";
	public static final String PROP_CONTACT_ID = "contactID";
	public static final String PROP_START_DATE = "startDate";
	public static final String PROP_REMINDER_DATE = "reminderDate";
	public static final String PROP_DUE_DATE = "dueDate";
	public static final String PROP_TICKLED_DATE = "tickledDate";

	boolean done=false;
	Long parentTask = -1L;
	int priority = 1;
	String name = "";
	String notes = "";
	TaskStatus status = TaskStatus.NEXT;
	Project project = null;
	ContextOrLocation context = null;
	boolean starred = false;
	int contactID = -1;
	ReminderTime startDate = null;
	ReminderTime reminderDate = null;
	ReminderTime dueDate = null;
	ReminderTime tickledDate = null;
	
	
	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}
	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		boolean oldValue = this.done;
		this.done = done;
		firePropertyChange(PROP_DONE, oldValue, done);
	}
	/**
	 * @return the parentTask
	 */
	public Long getParentTask() {
		return parentTask;
	}
	/**
	 * @param parentTask the parentTask to set
	 */
	public void setParentTask(Long parentTask) {
		Long oldValue = this.parentTask;
		this.parentTask = parentTask;
		firePropertyChange(PROP_PARENT_TASK, oldValue, parentTask);
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		if (priority < 1 || priority > 5) {
			throw new IllegalArgumentException("Priority must be between 1 and 5");
		}
		int oldValue = this.priority;
		this.priority = priority;
		firePropertyChange(PROP_PRIORITY, oldValue, priority);
	}
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
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		Project oldValue = this.project;
		this.project = project;
		firePropertyChange(PROP_PROJECT, oldValue, project);
	}
	/**
	 * @return the context
	 */
	public ContextOrLocation getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(ContextOrLocation context) {
		ContextOrLocation oldValue = this.context;
		this.context = context;
		firePropertyChange(PROP_CONTEXT, oldValue, context);
	}
	/**
	 * @return the starred
	 */
	public boolean isStarred() {
		return starred;
	}
	/**
	 * @param starred the starred to set
	 */
	public void setStarred(boolean starred) {
		boolean oldValue = this.starred;
		this.starred = starred;
		firePropertyChange(PROP_STARRED, oldValue, starred);
	}
	/**
	 * @return the contactID
	 */
	public int getContactID() {
		return contactID;
	}
	/**
	 * @param contactID the contactID to set
	 */
	public void setContactID(int contactID) {
		int oldValue = this.contactID;
		this.contactID = contactID;
		firePropertyChange(PROP_CONTACT_ID, oldValue, contactID);
	}
	/**
	 * @return the startDate
	 */
	public ReminderTime getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(ReminderTime startDate) {
		ReminderTime oldValue = this.startDate;
		this.startDate = startDate;
		firePropertyChange(PROP_START_DATE, oldValue, startDate);
	}
	/**
	 * @return the reminderDate
	 */
	public ReminderTime getReminderDate() {
		return reminderDate;
	}
	/**
	 * @param reminderDate the reminderDate to set
	 */
	public void setReminderDate(ReminderTime reminderDate) {
		ReminderTime oldValue = this.reminderDate;
		this.reminderDate = reminderDate;
		firePropertyChange(PROP_REMINDER_DATE, oldValue, reminderDate);
	}
	/**
	 * @return the dueDate
	 */
	public ReminderTime getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(ReminderTime dueDate) {
		ReminderTime oldValue = this.dueDate;
		this.dueDate = dueDate;
		firePropertyChange(PROP_DUE_DATE, oldValue, dueDate);
	}
	/**
	 * @return the tickledDate
	 */
	public ReminderTime getTickledDate() {
		return tickledDate;
	}
	/**
	 * @param tickledDate the tickledDate to set
	 */
	public void setTickledDate(ReminderTime tickledDate) {
		ReminderTime oldValue = this.tickledDate;
		this.tickledDate = tickledDate;
		firePropertyChange(PROP_TICKLED_DATE, oldValue, tickledDate);
	}
}
