/**
 * generated by Xtext 2.25.0
 */
package org.integratedmodelling.kdl.kdl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unit Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kdl.kdl.UnitElement#getId <em>Id</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.UnitElement#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kdl.kdl.KdlPackage#getUnitElement()
 * @model
 * @generated
 */
public interface UnitElement extends EObject
{
  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getUnitElement_Id()
   * @model
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.UnitElement#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Returns the value of the '<em><b>Unit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Unit</em>' containment reference.
   * @see #setUnit(Unit)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getUnitElement_Unit()
   * @model containment="true"
   * @generated
   */
  Unit getUnit();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.UnitElement#getUnit <em>Unit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Unit</em>' containment reference.
   * @see #getUnit()
   * @generated
   */
  void setUnit(Unit value);

} // UnitElement