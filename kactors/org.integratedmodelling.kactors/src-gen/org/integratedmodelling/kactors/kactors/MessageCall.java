/**
 * generated by Xtext 2.19.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.MessageCall#getName <em>Name</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.MessageCall#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.MessageCall#getGroup <em>Group</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.MessageCall#getActions <em>Actions</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getMessageCall()
 * @model
 * @generated
 */
public interface MessageCall extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getMessageCall_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.MessageCall#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference.
   * @see #setParameters(ParameterList)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getMessageCall_Parameters()
   * @model containment="true"
   * @generated
   */
  ParameterList getParameters();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.MessageCall#getParameters <em>Parameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameters</em>' containment reference.
   * @see #getParameters()
   * @generated
   */
  void setParameters(ParameterList value);

  /**
   * Returns the value of the '<em><b>Group</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Group</em>' containment reference.
   * @see #setGroup(StatementGroup)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getMessageCall_Group()
   * @model containment="true"
   * @generated
   */
  StatementGroup getGroup();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.MessageCall#getGroup <em>Group</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Group</em>' containment reference.
   * @see #getGroup()
   * @generated
   */
  void setGroup(StatementGroup value);

  /**
   * Returns the value of the '<em><b>Actions</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Actions</em>' containment reference.
   * @see #setActions(Actions)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getMessageCall_Actions()
   * @model containment="true"
   * @generated
   */
  Actions getActions();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.MessageCall#getActions <em>Actions</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Actions</em>' containment reference.
   * @see #getActions()
   * @generated
   */
  void setActions(Actions value);

} // MessageCall