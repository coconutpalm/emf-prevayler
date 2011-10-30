package es.strid.model;

import java.io.Serializable;


public class Sequence implements Serializable {
	private static final long serialVersionUID = 1L;
	private long currentValue = 0;
	
	public synchronized long getNextValue() {
		++currentValue;
		return currentValue;
	}
}
