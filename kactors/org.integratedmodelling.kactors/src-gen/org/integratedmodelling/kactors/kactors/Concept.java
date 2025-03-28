/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concept</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isNegated <em>Negated</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#getName <em>Name</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isAuthConcept <em>Auth Concept</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#getStringIdentifier <em>String Identifier</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#getIntIdentifier <em>Int Identifier</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#getAuthority <em>Authority</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isPresence <em>Presence</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#getConcept <em>Concept</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isCount <em>Count</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isDistance <em>Distance</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isProbability <em>Probability</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isAssessment <em>Assessment</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isChange <em>Change</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isRate <em>Rate</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isUncertainty <em>Uncertainty</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isMagnitude <em>Magnitude</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isLevel <em>Level</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isType <em>Type</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isObservability <em>Observability</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isProportion <em>Proportion</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#getOther <em>Other</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isPercentage <em>Percentage</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isRatio <em>Ratio</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isMonetary <em>Monetary</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isValue <em>Value</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#isOccurrence <em>Occurrence</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Concept#getDeclaration <em>Declaration</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept()
 * @model
 * @generated
 */
public interface Concept extends EObject
{
  /**
   * Returns the value of the '<em><b>Negated</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Negated</em>' attribute.
   * @see #setNegated(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Negated()
   * @model
   * @generated
   */
  boolean isNegated();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isNegated <em>Negated</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Negated</em>' attribute.
   * @see #isNegated()
   * @generated
   */
  void setNegated(boolean value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Auth Concept</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Auth Concept</em>' attribute.
   * @see #setAuthConcept(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_AuthConcept()
   * @model
   * @generated
   */
  boolean isAuthConcept();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isAuthConcept <em>Auth Concept</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Auth Concept</em>' attribute.
   * @see #isAuthConcept()
   * @generated
   */
  void setAuthConcept(boolean value);

  /**
   * Returns the value of the '<em><b>String Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>String Identifier</em>' attribute.
   * @see #setStringIdentifier(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_StringIdentifier()
   * @model
   * @generated
   */
  String getStringIdentifier();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#getStringIdentifier <em>String Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>String Identifier</em>' attribute.
   * @see #getStringIdentifier()
   * @generated
   */
  void setStringIdentifier(String value);

  /**
   * Returns the value of the '<em><b>Int Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Int Identifier</em>' attribute.
   * @see #setIntIdentifier(int)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_IntIdentifier()
   * @model
   * @generated
   */
  int getIntIdentifier();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#getIntIdentifier <em>Int Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Int Identifier</em>' attribute.
   * @see #getIntIdentifier()
   * @generated
   */
  void setIntIdentifier(int value);

  /**
   * Returns the value of the '<em><b>Authority</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Authority</em>' attribute.
   * @see #setAuthority(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Authority()
   * @model
   * @generated
   */
  String getAuthority();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#getAuthority <em>Authority</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Authority</em>' attribute.
   * @see #getAuthority()
   * @generated
   */
  void setAuthority(String value);

  /**
   * Returns the value of the '<em><b>Presence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Presence</em>' attribute.
   * @see #setPresence(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Presence()
   * @model
   * @generated
   */
  boolean isPresence();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isPresence <em>Presence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Presence</em>' attribute.
   * @see #isPresence()
   * @generated
   */
  void setPresence(boolean value);

  /**
   * Returns the value of the '<em><b>Concept</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Concept</em>' containment reference.
   * @see #setConcept(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Concept()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getConcept();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#getConcept <em>Concept</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Concept</em>' containment reference.
   * @see #getConcept()
   * @generated
   */
  void setConcept(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Count</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Count</em>' attribute.
   * @see #setCount(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Count()
   * @model
   * @generated
   */
  boolean isCount();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isCount <em>Count</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Count</em>' attribute.
   * @see #isCount()
   * @generated
   */
  void setCount(boolean value);

  /**
   * Returns the value of the '<em><b>Distance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Distance</em>' attribute.
   * @see #setDistance(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Distance()
   * @model
   * @generated
   */
  boolean isDistance();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isDistance <em>Distance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Distance</em>' attribute.
   * @see #isDistance()
   * @generated
   */
  void setDistance(boolean value);

  /**
   * Returns the value of the '<em><b>Probability</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Probability</em>' attribute.
   * @see #setProbability(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Probability()
   * @model
   * @generated
   */
  boolean isProbability();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isProbability <em>Probability</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Probability</em>' attribute.
   * @see #isProbability()
   * @generated
   */
  void setProbability(boolean value);

  /**
   * Returns the value of the '<em><b>Assessment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assessment</em>' attribute.
   * @see #setAssessment(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Assessment()
   * @model
   * @generated
   */
  boolean isAssessment();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isAssessment <em>Assessment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Assessment</em>' attribute.
   * @see #isAssessment()
   * @generated
   */
  void setAssessment(boolean value);

  /**
   * Returns the value of the '<em><b>Change</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Change</em>' attribute.
   * @see #setChange(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Change()
   * @model
   * @generated
   */
  boolean isChange();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isChange <em>Change</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Change</em>' attribute.
   * @see #isChange()
   * @generated
   */
  void setChange(boolean value);

  /**
   * Returns the value of the '<em><b>Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rate</em>' attribute.
   * @see #setRate(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Rate()
   * @model
   * @generated
   */
  boolean isRate();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isRate <em>Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Rate</em>' attribute.
   * @see #isRate()
   * @generated
   */
  void setRate(boolean value);

  /**
   * Returns the value of the '<em><b>Uncertainty</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uncertainty</em>' attribute.
   * @see #setUncertainty(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Uncertainty()
   * @model
   * @generated
   */
  boolean isUncertainty();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isUncertainty <em>Uncertainty</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Uncertainty</em>' attribute.
   * @see #isUncertainty()
   * @generated
   */
  void setUncertainty(boolean value);

  /**
   * Returns the value of the '<em><b>Magnitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Magnitude</em>' attribute.
   * @see #setMagnitude(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Magnitude()
   * @model
   * @generated
   */
  boolean isMagnitude();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isMagnitude <em>Magnitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Magnitude</em>' attribute.
   * @see #isMagnitude()
   * @generated
   */
  void setMagnitude(boolean value);

  /**
   * Returns the value of the '<em><b>Level</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Level</em>' attribute.
   * @see #setLevel(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Level()
   * @model
   * @generated
   */
  boolean isLevel();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isLevel <em>Level</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Level</em>' attribute.
   * @see #isLevel()
   * @generated
   */
  void setLevel(boolean value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Type()
   * @model
   * @generated
   */
  boolean isType();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #isType()
   * @generated
   */
  void setType(boolean value);

  /**
   * Returns the value of the '<em><b>Observability</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Observability</em>' attribute.
   * @see #setObservability(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Observability()
   * @model
   * @generated
   */
  boolean isObservability();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isObservability <em>Observability</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Observability</em>' attribute.
   * @see #isObservability()
   * @generated
   */
  void setObservability(boolean value);

  /**
   * Returns the value of the '<em><b>Proportion</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Proportion</em>' attribute.
   * @see #setProportion(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Proportion()
   * @model
   * @generated
   */
  boolean isProportion();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isProportion <em>Proportion</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Proportion</em>' attribute.
   * @see #isProportion()
   * @generated
   */
  void setProportion(boolean value);

  /**
   * Returns the value of the '<em><b>Other</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Other</em>' containment reference.
   * @see #setOther(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Other()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getOther();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#getOther <em>Other</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Other</em>' containment reference.
   * @see #getOther()
   * @generated
   */
  void setOther(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Percentage</em>' attribute.
   * @see #setPercentage(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Percentage()
   * @model
   * @generated
   */
  boolean isPercentage();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isPercentage <em>Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Percentage</em>' attribute.
   * @see #isPercentage()
   * @generated
   */
  void setPercentage(boolean value);

  /**
   * Returns the value of the '<em><b>Ratio</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ratio</em>' attribute.
   * @see #setRatio(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Ratio()
   * @model
   * @generated
   */
  boolean isRatio();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isRatio <em>Ratio</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ratio</em>' attribute.
   * @see #isRatio()
   * @generated
   */
  void setRatio(boolean value);

  /**
   * Returns the value of the '<em><b>Monetary</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Monetary</em>' attribute.
   * @see #setMonetary(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Monetary()
   * @model
   * @generated
   */
  boolean isMonetary();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isMonetary <em>Monetary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Monetary</em>' attribute.
   * @see #isMonetary()
   * @generated
   */
  void setMonetary(boolean value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Value()
   * @model
   * @generated
   */
  boolean isValue();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #isValue()
   * @generated
   */
  void setValue(boolean value);

  /**
   * Returns the value of the '<em><b>Occurrence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Occurrence</em>' attribute.
   * @see #setOccurrence(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Occurrence()
   * @model
   * @generated
   */
  boolean isOccurrence();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#isOccurrence <em>Occurrence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Occurrence</em>' attribute.
   * @see #isOccurrence()
   * @generated
   */
  void setOccurrence(boolean value);

  /**
   * Returns the value of the '<em><b>Declaration</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Declaration</em>' containment reference.
   * @see #setDeclaration(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConcept_Declaration()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getDeclaration();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Concept#getDeclaration <em>Declaration</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Declaration</em>' containment reference.
   * @see #getDeclaration()
   * @generated
   */
  void setDeclaration(ConceptDeclaration value);

} // Concept
