/**
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kim.kim;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Classification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kim.kim.Classification#getClassifiers <em>Classifiers</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kim.kim.KimPackage#getClassification()
 * @model
 * @generated
 */
public interface Classification extends EObject
{
  /**
   * Returns the value of the '<em><b>Classifiers</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kim.kim.Classifier}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Classifiers</em>' containment reference list.
   * @see org.integratedmodelling.kim.kim.KimPackage#getClassification_Classifiers()
   * @model containment="true"
   * @generated
   */
  EList<Classifier> getClassifiers();

} // Classification
