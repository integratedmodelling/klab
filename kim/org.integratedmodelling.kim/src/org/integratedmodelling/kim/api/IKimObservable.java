package org.integratedmodelling.kim.api;

import org.integratedmodelling.kim.utils.Range;

public interface IKimObservable extends IKimStatement {

    /**
     * 
     * @return
     */
    IKimConcept getMain();

    /**
     * 
     * @return
     */
    IKimConcept getDownTo();

    /**
     * 
     * @return
     */
    IKimConcept getBy();

    /**
     * 
     * @return
     */
    Range getRange();

    /**
     * 
     * @return
     */
    String getUnit();

    /**
     * 
     * @return
     */
    String getCurrency();

    /**
     * 
     * @return
     */
    String getFormalName();

    /**
     * 
     * @return
     */
    Object getValue();

    /**
     * 
     * @return
     */
    boolean isAbstractObservable();

    /**
     * If the observable specification had an identifier (rather than a literal value) before an 'as' clause
     * introducing the semantics, this will return true and the {@link #getValue()} method will return a
     * string with the identifier's value. The interpretation of the identifier is context-dependent as it may
     * refer to a value previously defined in a 'define' statement, or to an attribute to be looked up in a
     * referenced resource.
     * 
     * @return
     */
    boolean hasAttributeIdentifier();

    /**
     * True if the 'optional' clause has been passed.
     * 
     * @return
     */
    boolean isOptional();

}
