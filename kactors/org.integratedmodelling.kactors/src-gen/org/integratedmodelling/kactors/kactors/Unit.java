/**
 * generated by Xtext 2.19.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Unit#getRoot <em>Root</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Unit#getConnectors <em>Connectors</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Unit#getUnits <em>Units</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getUnit()
 * @model
 * @generated
 */
public interface Unit extends EObject
{
  /**
   * Returns the value of the '<em><b>Root</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Root</em>' containment reference.
   * @see #setRoot(UnitElement)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getUnit_Root()
   * @model containment="true"
   * @generated
   */
  UnitElement getRoot();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Unit#getRoot <em>Root</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Root</em>' containment reference.
   * @see #getRoot()
   * @generated
   */
  void setRoot(UnitElement value);

  /**
   * Returns the value of the '<em><b>Connectors</b></em>' attribute list.
   * The list contents are of type {@link org.integratedmodelling.kactors.kactors.UnitOp}.
   * The literals are from the enumeration {@link org.integratedmodelling.kactors.kactors.UnitOp}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Connectors</em>' attribute list.
   * @see org.integratedmodelling.kactors.kactors.UnitOp
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getUnit_Connectors()
   * @model unique="false"
   * @generated
   */
  EList<UnitOp> getConnectors();

  /**
   * Returns the value of the '<em><b>Units</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kactors.kactors.UnitElement}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Units</em>' containment reference list.
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getUnit_Units()
   * @model containment="true"
   * @generated
   */
  EList<UnitElement> getUnits();

} // Unit