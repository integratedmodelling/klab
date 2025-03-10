/**
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kim.kim;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Identity Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kim.kim.IdentityRequirement#getType <em>Type</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.IdentityRequirement#getIdentities <em>Identities</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.IdentityRequirement#getAuthority <em>Authority</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kim.kim.KimPackage#getIdentityRequirement()
 * @model
 * @generated
 */
public interface IdentityRequirement extends EObject
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see org.integratedmodelling.kim.kim.KimPackage#getIdentityRequirement_Type()
   * @model
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.IdentityRequirement#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Returns the value of the '<em><b>Identities</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kim.kim.ConceptDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Identities</em>' containment reference list.
   * @see org.integratedmodelling.kim.kim.KimPackage#getIdentityRequirement_Identities()
   * @model containment="true"
   * @generated
   */
  EList<ConceptDeclaration> getIdentities();

  /**
   * Returns the value of the '<em><b>Authority</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Authority</em>' attribute.
   * @see #setAuthority(String)
   * @see org.integratedmodelling.kim.kim.KimPackage#getIdentityRequirement_Authority()
   * @model
   * @generated
   */
  String getAuthority();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.IdentityRequirement#getAuthority <em>Authority</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Authority</em>' attribute.
   * @see #getAuthority()
   * @generated
   */
  void setAuthority(String value);

} // IdentityRequirement
