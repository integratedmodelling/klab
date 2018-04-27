package org.integratedmodelling.kim.api;

import org.integratedmodelling.kim.utils.Range;

public interface IKimObservable extends IKimStatement {

    /**
     * 
     * @return the main concept
     */
    IKimConcept getMain();

    /**
     * 
     * @return the 'down to' concept
     */
    IKimConcept getDownTo();

    /**
     * 
     * @return the 'by' concept
     */
    IKimConcept getBy();

    /**
     * 
     * @return the range
     */
    Range getRange();

    /**
     * 
     * @return the unit
     */
    String getUnit();

    /**
     * 
     * @return the currency
     */
    String getCurrency();

    /**
     * 
     * @return the 'named' name
     */
    String getFormalName();

    /**
     * 
     * @return the literal value
     */
    Object getValue();

    /**
     * 
     * @return true if abstract
     */
    boolean isAbstractObservable();

    /**
     * If the observable specification had an identifier (rather than a literal value) before an 'as' clause
     * introducing the semantics, this will return true and the {@link #getValue()} method will return a
     * string with the identifier's value. The interpretation of the identifier is context-dependent as it may
     * refer to a value previously defined in a 'define' statement, or to an attribute to be looked up in a
     * referenced resource.
     * 
     * @return true if identified by an attribute to be resolved
     */
    boolean hasAttributeIdentifier();

    /**
     * True if the 'optional' clause has been passed.
     * 
     * @return true if optional
     */
    boolean isOptional();

}
