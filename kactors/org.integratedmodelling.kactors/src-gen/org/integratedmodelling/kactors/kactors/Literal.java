/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Literal#getNumber <em>Number</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Literal#getFrom <em>From</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Literal#getTo <em>To</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Literal#getString <em>String</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Literal#getDate <em>Date</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Literal#getBoolean <em>Boolean</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getLiteral()
 * @model
 * @generated
 */
public interface Literal extends EObject
{
  /**
   * Returns the value of the '<em><b>Number</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Number</em>' containment reference.
   * @see #setNumber(org.integratedmodelling.kactors.kactors.Number)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getLiteral_Number()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kactors.kactors.Number getNumber();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Literal#getNumber <em>Number</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number</em>' containment reference.
   * @see #getNumber()
   * @generated
   */
  void setNumber(org.integratedmodelling.kactors.kactors.Number value);

  /**
   * Returns the value of the '<em><b>From</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>From</em>' containment reference.
   * @see #setFrom(org.integratedmodelling.kactors.kactors.Number)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getLiteral_From()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kactors.kactors.Number getFrom();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Literal#getFrom <em>From</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From</em>' containment reference.
   * @see #getFrom()
   * @generated
   */
  void setFrom(org.integratedmodelling.kactors.kactors.Number value);

  /**
   * Returns the value of the '<em><b>To</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>To</em>' containment reference.
   * @see #setTo(org.integratedmodelling.kactors.kactors.Number)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getLiteral_To()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kactors.kactors.Number getTo();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Literal#getTo <em>To</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>To</em>' containment reference.
   * @see #getTo()
   * @generated
   */
  void setTo(org.integratedmodelling.kactors.kactors.Number value);

  /**
   * Returns the value of the '<em><b>String</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>String</em>' attribute.
   * @see #setString(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getLiteral_String()
   * @model
   * @generated
   */
  String getString();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Literal#getString <em>String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>String</em>' attribute.
   * @see #getString()
   * @generated
   */
  void setString(String value);

  /**
   * Returns the value of the '<em><b>Date</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' containment reference.
   * @see #setDate(Date)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getLiteral_Date()
   * @model containment="true"
   * @generated
   */
  Date getDate();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Literal#getDate <em>Date</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date</em>' containment reference.
   * @see #getDate()
   * @generated
   */
  void setDate(Date value);

  /**
   * Returns the value of the '<em><b>Boolean</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Boolean</em>' attribute.
   * @see #setBoolean(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getLiteral_Boolean()
   * @model
   * @generated
   */
  String getBoolean();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Literal#getBoolean <em>Boolean</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Boolean</em>' attribute.
   * @see #getBoolean()
   * @generated
   */
  void setBoolean(String value);

} // Literal
