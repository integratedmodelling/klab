/**
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kim.kim;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Currency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kim.kim.Currency#getId <em>Id</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.Currency#getYear <em>Year</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.Currency#getUnits <em>Units</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kim.kim.KimPackage#getCurrency()
 * @model
 * @generated
 */
public interface Currency extends EObject
{
  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see org.integratedmodelling.kim.kim.KimPackage#getCurrency_Id()
   * @model
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.Currency#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Returns the value of the '<em><b>Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Year</em>' attribute.
   * @see #setYear(int)
   * @see org.integratedmodelling.kim.kim.KimPackage#getCurrency_Year()
   * @model
   * @generated
   */
  int getYear();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.Currency#getYear <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Year</em>' attribute.
   * @see #getYear()
   * @generated
   */
  void setYear(int value);

  /**
   * Returns the value of the '<em><b>Units</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kim.kim.UnitElement}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Units</em>' containment reference list.
   * @see org.integratedmodelling.kim.kim.KimPackage#getCurrency_Units()
   * @model containment="true"
   * @generated
   */
  EList<UnitElement> getUnits();

} // Currency
