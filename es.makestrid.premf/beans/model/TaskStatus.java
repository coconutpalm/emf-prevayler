package es.strid.model;

import java.io.Serializable;

public enum TaskStatus implements Serializable {
	NEXT, WAITING, FUTURE, TICKLED;
}
