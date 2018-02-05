package org.integratedmodelling.klab.api.data.classification;

public interface IClassifier {

  /**
   * True if passed object matches the conditions of the classifier.
   * 
   * @param o
   * @return True if passed object matches the conditions of the classifier
   */
  public boolean classify(Object o);

  /**
   * True if this classifier matches everything.
   * 
   * @return True if this classifier matches everything
   */
  boolean isUniversal();

  /**
   * True if this classifier only matches null (unknown).
   * 
   * @return True if this classifier only matches null
   */
  boolean isNil();

  /**
   * True if this is an interval classifier.
   * 
   * @return True if this is an interval classifier
   */
  boolean isInterval();


  /**
   * Classifiers may be used as a value; this one should return the most appropriate 
   * value translation of the classifier, i.e. the matched object if it's matching a
   * single one, or possibly a random object among the choices if it's in OR.
  
   * @return the value this classifier resolves to.
   */
  public Object asValue();
}
