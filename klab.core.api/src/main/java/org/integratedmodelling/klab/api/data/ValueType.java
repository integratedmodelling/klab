package org.integratedmodelling.klab.api.data;

/**
 * Types for values supported in literals of the various k.LAB languages. Only k.Actors supports all
 * of these.
 * 
 * @author Ferd
 *
 */
public enum ValueType {

    REGEXP, NUMBER, BOOLEAN, STRING, CONCEPT, OBSERVABLE, IDENTIFIER, EXPRESSION, LIST, SET, CLASS, ANYVALUE, ANYTHING, NODATA, RANGE, MAP, TABLE, TREE, QUANTITY, DATE, TYPE, NUMBERED_PATTERN, URN,

    /**
     * Object is a Java object whose constructor is referenced in a set expression.
     */
    OBJECT,
    /**
     * An action that raises an exception will match this.
     */
    ERROR,
    /*
     * Empty is a legal "nothing" result which may represent an empty collection or an empty
     * artifact
     */
    EMPTY,
    /*
     * enum-like uppercase constant, matching enum values (or strings) at the Java side.
     */
    CONSTANT,
    /**
     * Matcher for anything that isn't null, false or error
     */
    ANYTRUE,

    /**
     * Objects that don't have literals in the language but can be fired
     */
    OBSERVATION,

    /**
     * Only for matching any object that has a specified annotation.
     */
    ANNOTATION,

    /**
     * A component path and possibly arguments passed after 'new'
     */
    COMPONENT,

    /**
     * A (chain of) function calls
     */
    CALLCHAIN,

    /**
     * A string in the form #KEY, pointing to a localized string in the companion
     * internationalization map.
     */
    LOCALIZED_KEY
}
