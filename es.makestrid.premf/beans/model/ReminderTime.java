package es.strid.model;

import java.util.Date;

public class ReminderTime extends Reminder {
	private static final long serialVersionUID = 1L;

	public static final String PROP_TIME = "time";
	
	private Date time = new Date();

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		Date oldValue = this.time;
		this.time = time;
		firePropertyChange(PROP_TIME, oldValue, time);
	}

	@Override
	public boolean isEnabled() {
		return false;		// FIXME: Implement me, please
	}
	
}
