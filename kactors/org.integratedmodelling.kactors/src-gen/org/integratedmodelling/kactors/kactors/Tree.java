/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tree</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Tree#getRoot <em>Root</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Tree#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTree()
 * @model
 * @generated
 */
public interface Tree extends EObject
{
  /**
   * Returns the value of the '<em><b>Root</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Root</em>' containment reference.
   * @see #setRoot(Value)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTree_Root()
   * @model containment="true"
   * @generated
   */
  Value getRoot();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Tree#getRoot <em>Root</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Root</em>' containment reference.
   * @see #getRoot()
   * @generated
   */
  void setRoot(Value value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference list.
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTree_Value()
   * @model containment="true"
   * @generated
   */
  EList<EObject> getValue();

} // Tree
