package org.integratedmodelling.kactors.api;

/**
 * Values can be a lot of different things in k.Actors and serve as matches for fired values, so we
 * categorize them on parsing to allow quick matching. The categorization includes the distinction
 * between syntactic roles and is not just about type (e.g. a naked identifier is not a string). A
 * derived class should use this as delegate to define as() to match types to useful objects not
 * part of the interface.
 * <p>
 * Values can also be used to encode expressions, potentially building an evaluation tree, although
 * parenthesized expressions are for now made impossible by the syntax (round brackets used for too
 * many other purposes) and the only expression supported is a ternary operator. Operators can be
 * easily added but I'm not sure it would be a good idea without the possibility to parenthesize
 * (and the likely fact that an apparently legitimate parenthesized expression would parse correctly
 * and mean something else entirely).
 * <p>
 * An error value (whose content may be null, an exception or a string message) will not match
 * anything except ANYTHING or ERROR.
 * 
 * @author Ferd
 *
 */
public interface IKActorsValue extends IKActorsCodeStatement {

    public enum Type {
        REGEXP, NUMBER, BOOLEAN, STRING, OBSERVABLE, IDENTIFIER, EXPRESSION, LIST, SET, CLASS, ANYVALUE, ANYTHING, NODATA, RANGE, MAP, TABLE, TREE, QUANTITY, DATE, TYPE, NUMBERED_PATTERN, URN,

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
        COMPONENT

    }

    /**
     * If the value subsumes others in an expression, it will have an expression type other than
     * VALUE. For now only supporting ternary operators, eventually we can build expression trees
     * with operators, assuming the parenthesization can be dealt with in the parser (probably not
     * unless we use different brackets for groups).
     * 
     * @author Ferd
     *
     */
    public enum ExpressionType {

        /**
         * The default: this value just means itself
         */
        VALUE,

        /**
         * An expression where the current value is the condition and there is a "then" and an
         * "else" value
         */
        TERNARY_OPERATOR
    }

    /**
     * The value type
     * 
     * @return
     */
    Type getType();

    /**
     * Expression type is VALUE for anything not part of an expression, which is the default.
     * 
     * @return
     */
    ExpressionType getExpressionType();

    /**
     * The value itself. Will be null if unknown, anyvalue or anything.
     * 
     * @return
     */
    Object getValue();

    /**
     * Return the value as the type passed. Meant to complement the enum in a fluent API and not to
     * be used for conversions.
     * 
     * @param <T>
     * @param cls
     * @return
     */
    <T> T as(Class<? extends T> cls);

    /**
     * If true, the value specifies a constraint that excludes its own value when used in matching.
     *
     * @return
     */
    boolean isExclusive();

    /**
     * If {@link #getExpressionType()} returns {@link Type#TERNARY_EXPRESSION}, this contains the
     * value to evaluate if this evaluates to true.
     * 
     * @return
     */
    IKActorsValue getTrueCase();

    /**
     * If {@link #getExpressionType()} returns {@link Type#TERNARY_EXPRESSION}, this contains the
     * value to evaluate if this evaluates to false.
     * 
     * @return
     */
    IKActorsValue getFalseCase();

}
