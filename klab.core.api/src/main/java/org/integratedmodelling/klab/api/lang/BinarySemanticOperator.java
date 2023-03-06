package org.integratedmodelling.klab.api.lang;

/**
 * Connectors for concepts that are linked in expressions. These can be chained. Besides the
 * standard AND (@link {@link #UNION}) and OR (@link {@link #INTERSECTION}) we have, for now, (@link
 * {@link #FOLLOWS}) that creates an event made up of two consecutive events. We do not allow a
 * negation operator as deniability is handled elsewhere.
 * 
 * More work needs to be done to ensure correct interpretation of unions and intersections,
 * particularly to recognize a duality that is due to observational issues such as scale or
 * uncertainty vs. phenomenology. We may eventually provide different operators to distinguish
 * different cases.
 * 
 * @author ferdinando.villa
 *
 */
public enum BinarySemanticOperator {

    /**
     * A UNION B creates a concept that is either A or B.
     */
    UNION("or"),
    

    /**
     * A INTERSECTION B creates a concept that is both A and B.
     */
    INTERSECTION("and"),

    /**
     * A FOLLOWS B applies to two event types and creates the event that corresponds to A following
     * B. E.g.
     * <p>
     * 
     * <pre>
     * Life is (Death follows Adulthood follows Childhood follows Birth).
     * </pre>
     */
    FOLLOWS("follows");
    
    String declaration;
    
    BinarySemanticOperator(String code) {
        this.declaration = code;
    }

    public static BinarySemanticOperator forCode(String code) {
        for (BinarySemanticOperator val : values()) {
            if (code.equals(val.declaration)) {
                return val;
            }
        }
        return null;
    }
    
}
