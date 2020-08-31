package org.integratedmodelling.kactors.api;

/**
 * Values can be a lot of different things in k.Actors and serve as matches for
 * fired values, so we categorize them on parsing to allow quick matching. The
 * categorization includes the distinction between syntactic roles and is not
 * just about type (e.g. a naked identifier is not a string). A derived class
 * should use this as delegate to define as() to match types to useful objects
 * not part of the interface.
 * <p>
 * An error value (whose content may be null, an exception or a string message)
 * will not match anything except ANYTHING or ERROR.
 * 
 * @author Ferd
 *
 */
public interface IKActorsValue extends IKActorsCodeStatement {

	public enum Type {
		REGEXP, NUMBER, BOOLEAN, STRING, OBSERVABLE, IDENTIFIER, EXPRESSION, LIST, SET, CLASS, ANYVALUE, ANYTHING, NODATA,
		RANGE, MAP, TABLE, TREE, QUANTITY, DATE, TYPE, NUMBERED_PATTERN, URN, ERROR,
		/**
		 * Matcher for anything that isn't null, false or error
		 */
		ANYTRUE,
		
		/**
		 * Objects that don't have literals in the language but can be fired
		 */
		OBSERVATION
	}

	/**
	 * The value type
	 * 
	 * @return
	 */
	Type getType();

	/**
	 * The value itself. Will be null if unknown, anyvalue or anything.
	 * 
	 * @return
	 */
	Object getValue();

	/**
	 * Return the value as the type passed. Meant to complement the enum in a fluent
	 * API and not to be used for conversions.
	 * 
	 * @param <T>
	 * @param cls
	 * @return
	 */
	<T> T as(Class<? extends T> cls);

	/**
	 * If true, the value specifies a constraint that excludes its own value when used in 
	 * matching.
	 *
	 * @return
	 */
	boolean isExclusive();

}
