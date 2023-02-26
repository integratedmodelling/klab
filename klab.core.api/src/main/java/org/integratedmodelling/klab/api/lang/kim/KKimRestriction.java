package org.integratedmodelling.klab.api.lang.kim;

public interface KKimRestriction extends KKimStatement {

    enum Type {
        USES,
        HAS,
        CONTAINS,
        IMPLIES
    }

    enum Cardinality {
        ONLY,
        NONE,
        EXACTLY,
        AT_LEAST,
        AT_MOST
    }

    /**
     * The type of restriction. May be further specified through inference starting at {@link #getTargetSubject()}.
     * 
     * @return the type
     */
    Type getType();

    /**
     * Type of cardinality.
     * 
     * @return the cardinality type
     */
    Cardinality getCardinality();

    /**
     * Argument to cardinality if necessary. Guaranteed correct also if redundant
     * with it.
     * 
     * @return the numerosity
     */
    int getNumerosity();

    /**
     * The main concept mentioned in the restriction.
     * 
     * @return the main concept
     */
    KKimConcept getFiller();

    /**
     * Target subject ('for'/'as') to be turned into the property that connects to it. Rarely 
     * specified as the type is usually enough to infer the property.
     * 
     * @return target subject type or null.
     */
    KKimConcept getTargetSubject();

    /**
     * The data type, not null only in data restrictions.
     * 
     * @return the data type
     */
//    DataType getDataType();

    /**
     * Data value if data type is not null.
     * 
     * @return the value
     */
    Object getValue();

    /**
     * Top of range in case data type isn't null and user specified a range.
     * 
     * @return the range
     */
    Number getRange();
}
