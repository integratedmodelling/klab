/**
 * generated by Xtext 2.19.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Row</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableRow#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableRow()
 * @model
 * @generated
 */
public interface TableRow extends EObject
{
  /**
   * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kactors.kactors.TableClassifier}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elements</em>' containment reference list.
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableRow_Elements()
   * @model containment="true"
   * @generated
   */
  EList<TableClassifier> getElements();

} // TableRow