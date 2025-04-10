/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.MapEntry#getClassifier <em>Classifier</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.MapEntry#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getMapEntry()
 * @model
 * @generated
 */
public interface MapEntry extends EObject
{
  /**
   * Returns the value of the '<em><b>Classifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Classifier</em>' containment reference.
   * @see #setClassifier(Classifier)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getMapEntry_Classifier()
   * @model containment="true"
   * @generated
   */
  Classifier getClassifier();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.MapEntry#getClassifier <em>Classifier</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Classifier</em>' containment reference.
   * @see #getClassifier()
   * @generated
   */
  void setClassifier(Classifier value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(Value)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getMapEntry_Value()
   * @model containment="true"
   * @generated
   */
  Value getValue();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.MapEntry#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(Value value);

} // MapEntry
