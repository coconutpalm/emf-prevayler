package es.strid.model;

public class ReminderLocation extends Reminder {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isEnabled() {
		return false;
	}

}
