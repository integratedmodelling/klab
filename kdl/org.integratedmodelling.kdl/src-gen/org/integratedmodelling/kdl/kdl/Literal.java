/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kdl.kdl;

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
 *   <li>{@link org.integratedmodelling.kdl.kdl.Literal#getNumber <em>Number</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Literal#getFrom <em>From</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Literal#getTo <em>To</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Literal#getString <em>String</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Literal#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Literal#getId <em>Id</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Literal#isComma <em>Comma</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kdl.kdl.KdlPackage#getLiteral()
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
   * @see #setNumber(org.integratedmodelling.kdl.kdl.Number)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getLiteral_Number()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kdl.kdl.Number getNumber();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Literal#getNumber <em>Number</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number</em>' containment reference.
   * @see #getNumber()
   * @generated
   */
  void setNumber(org.integratedmodelling.kdl.kdl.Number value);

  /**
   * Returns the value of the '<em><b>From</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>From</em>' containment reference.
   * @see #setFrom(org.integratedmodelling.kdl.kdl.Number)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getLiteral_From()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kdl.kdl.Number getFrom();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Literal#getFrom <em>From</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From</em>' containment reference.
   * @see #getFrom()
   * @generated
   */
  void setFrom(org.integratedmodelling.kdl.kdl.Number value);

  /**
   * Returns the value of the '<em><b>To</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>To</em>' containment reference.
   * @see #setTo(org.integratedmodelling.kdl.kdl.Number)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getLiteral_To()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kdl.kdl.Number getTo();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Literal#getTo <em>To</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>To</em>' containment reference.
   * @see #getTo()
   * @generated
   */
  void setTo(org.integratedmodelling.kdl.kdl.Number value);

  /**
   * Returns the value of the '<em><b>String</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>String</em>' attribute.
   * @see #setString(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getLiteral_String()
   * @model
   * @generated
   */
  String getString();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Literal#getString <em>String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>String</em>' attribute.
   * @see #getString()
   * @generated
   */
  void setString(String value);

  /**
   * Returns the value of the '<em><b>Boolean</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Boolean</em>' attribute.
   * @see #setBoolean(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getLiteral_Boolean()
   * @model
   * @generated
   */
  String getBoolean();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Literal#getBoolean <em>Boolean</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Boolean</em>' attribute.
   * @see #getBoolean()
   * @generated
   */
  void setBoolean(String value);

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getLiteral_Id()
   * @model
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Literal#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Returns the value of the '<em><b>Comma</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Comma</em>' attribute.
   * @see #setComma(boolean)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getLiteral_Comma()
   * @model
   * @generated
   */
  boolean isComma();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Literal#isComma <em>Comma</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Comma</em>' attribute.
   * @see #isComma()
   * @generated
   */
  void setComma(boolean value);

} // Literal
