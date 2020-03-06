package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.Value;

/**
 * Values. Most are reported as the object they are encoded with (strings for
 * most non-POD objects) but carry the type defined by the semantics they are
 * declared with.
 * 
 * @author Ferd
 *
 */
public class KActorsValue extends KActorStatement {

	public enum Type {
		REGEXP, LITERAL, OBSERVABLE, VARIABLE, EXPRESSION, LIST, CLASS, ANYVALUE, ANYTHING, NODATA
	}

	private Type type;
	private Object value;
	
	public KActorsValue(Value value, KActorStatement parent) {
		super(value, parent);
	}
}
