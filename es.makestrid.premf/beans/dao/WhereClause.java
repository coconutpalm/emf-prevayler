package es.strid.model.dao;

import java.io.Serializable;

public interface WhereClause<T> extends Serializable {
	boolean include(T candidate);
}
