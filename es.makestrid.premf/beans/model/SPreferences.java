package es.strid.model;

import java.io.Serializable;
import java.util.Date;

public class SPreferences implements Serializable {

	private static final long serialVersionUID = 1L;

	boolean syncToWeb = false;
	Date lastSync = null;
	String username = "";
	String password = "";
//	String locationRingTone; // FIXME: data type?
//	String ticklerRingTone;
}
