/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kdl.kdl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kdl.kdl.List#getContents <em>Contents</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kdl.kdl.KdlPackage#getList()
 * @model
 * @generated
 */
public interface List extends EObject
{
  /**
   * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kdl.kdl.Value}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Contents</em>' containment reference list.
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getList_Contents()
   * @model containment="true"
   * @generated
   */
  EList<Value> getContents();

} // List
