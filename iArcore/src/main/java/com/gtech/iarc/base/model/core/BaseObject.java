package com.gtech.iarc.base.model.core;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class BaseObject implements Serializable {

	private static final long serialVersionUID = -2188045770740684729L;

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public abstract long getId();

	public abstract void setId(long id);
}
