package org.integratedmodelling.klab.api.data.mediation;

public interface INumericRange extends IValueMediator {
    
    /**
     * Any unbounded boundary will be the corresponding Double.(NEGATIVE_)INFINITE.
     * 
     * @return the range of the scale.
     */
    Number getRangeMin();

    /**
     * Any unbounded boundary will be the corresponding Double.(NEGATIVE_)INFINITE.
     * 
     * @return the range of the scale.
     */
    Number getRangeMax();

    /**
     * Both bounds are defined
     * 
     * @return true if both boundaries are defined.
     */
    boolean isBounded();

    /**
     * If only integers should be used.
     * 
     * @return true if the scale is not meant to have values in non-integer numbers.
     */
    boolean isInteger();
}
