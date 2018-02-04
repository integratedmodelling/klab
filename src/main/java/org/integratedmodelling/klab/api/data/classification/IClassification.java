package org.integratedmodelling.klab.api.data.classification;

import org.integratedmodelling.klab.api.knowledge.IConcept;

public interface IClassification extends Iterable<IClassifier> {

    /**
     * Return the main concept that subsumes all those expressed by the classifiers. In some situations this may be
     * null. In normal situations this will be an abstract concept and subsume all others.
     * 
     * @return
     */
    IConcept getConcept();

    /**
     * True if this has been declared and validated as a discretization. Subsumes {@link #isContiguousAndFinite()} == true.
     * 
     * @return
     */
    boolean isDiscretization();

    /**
     * Return true if all the intervals are contiguous and the extreme intervals have
     * finite boundaries. 
     * 
     * @return true if we express the discretization of a finite domain
     */
    public boolean isContiguousAndFinite();

    /**
     * Return the concept that the passed object classifies to, or null if no
     * classifiers match.
     * 
     * @param object
     * @return
     */
    IConcept classify(Object object);

    /**
   * Get the undiscretized value for the passed concept. If the concept is not in the classification
   * or this is not a discretization, return Double.NaN.
   * 
   * @param object
   * @return
   */
    public double undiscretize(IConcept object);

    /**
     * Return a sensible numeric value for the passed concept. NaN should be reserved for no-data,
     * concepts for which suitable classifiers are not defined, or unrecognized concepts; ranking 
     * order for orderings should be respected. If the data encode a discretization, it is OK to 
     * return the undiscretized values.
     * 
     * @param o
     * @return the number we can use to encode the concept, which must be one of the getConcepts()
     */
    public double getNumericValue(IConcept o);

    /**
     * Classify to the numeric ranking of the concept instead of the concept.
     * 
     * @param o
     * @return a numeric ranking - equivalent to calling getNumericCode(classify(o))
     */
    public int classifyToIndex(Object o);
}
