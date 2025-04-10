/**
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kim.kim;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kim.kim.Table#getHeaders <em>Headers</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.Table#getRows <em>Rows</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.Table#getColumnClassifiers <em>Column Classifiers</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kim.kim.KimPackage#getTable()
 * @model
 * @generated
 */
public interface Table extends EObject
{
  /**
   * Returns the value of the '<em><b>Headers</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Headers</em>' containment reference.
   * @see #setHeaders(HeaderRow)
   * @see org.integratedmodelling.kim.kim.KimPackage#getTable_Headers()
   * @model containment="true"
   * @generated
   */
  HeaderRow getHeaders();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.Table#getHeaders <em>Headers</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Headers</em>' containment reference.
   * @see #getHeaders()
   * @generated
   */
  void setHeaders(HeaderRow value);

  /**
   * Returns the value of the '<em><b>Rows</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kim.kim.TableRow}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rows</em>' containment reference list.
   * @see org.integratedmodelling.kim.kim.KimPackage#getTable_Rows()
   * @model containment="true"
   * @generated
   */
  EList<TableRow> getRows();

  /**
   * Returns the value of the '<em><b>Column Classifiers</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Column Classifiers</em>' containment reference.
   * @see #setColumnClassifiers(TableRow)
   * @see org.integratedmodelling.kim.kim.KimPackage#getTable_ColumnClassifiers()
   * @model containment="true"
   * @generated
   */
  TableRow getColumnClassifiers();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.Table#getColumnClassifiers <em>Column Classifiers</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Column Classifiers</em>' containment reference.
   * @see #getColumnClassifiers()
   * @generated
   */
  void setColumnClassifiers(TableRow value);

} // Table
