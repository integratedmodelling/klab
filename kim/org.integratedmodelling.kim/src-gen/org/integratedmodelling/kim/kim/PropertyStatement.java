/**
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.18.0.M3
 */
package org.integratedmodelling.kim.kim;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#getProperty <em>Property</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#isHas <em>Has</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#isContains <em>Contains</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#isUses <em>Uses</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#isOnly <em>Only</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#isExactly <em>Exactly</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#isAtLeast <em>At Least</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#isAtMost <em>At Most</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#getCardinality <em>Cardinality</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#isOrMore <em>Or More</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.PropertyStatement#getPropertyTarget <em>Property Target</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement()
 * @model
 * @generated
 */
public interface PropertyStatement extends EObject
{
  /**
   * Returns the value of the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Property</em>' attribute.
   * @see #setProperty(String)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_Property()
   * @model
   * @generated
   */
  String getProperty();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#getProperty <em>Property</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Property</em>' attribute.
   * @see #getProperty()
   * @generated
   */
  void setProperty(String value);

  /**
   * Returns the value of the '<em><b>Has</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Has</em>' attribute.
   * @see #setHas(boolean)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_Has()
   * @model
   * @generated
   */
  boolean isHas();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#isHas <em>Has</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Has</em>' attribute.
   * @see #isHas()
   * @generated
   */
  void setHas(boolean value);

  /**
   * Returns the value of the '<em><b>Contains</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Contains</em>' attribute.
   * @see #setContains(boolean)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_Contains()
   * @model
   * @generated
   */
  boolean isContains();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#isContains <em>Contains</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Contains</em>' attribute.
   * @see #isContains()
   * @generated
   */
  void setContains(boolean value);

  /**
   * Returns the value of the '<em><b>Uses</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uses</em>' attribute.
   * @see #setUses(boolean)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_Uses()
   * @model
   * @generated
   */
  boolean isUses();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#isUses <em>Uses</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Uses</em>' attribute.
   * @see #isUses()
   * @generated
   */
  void setUses(boolean value);

  /**
   * Returns the value of the '<em><b>Only</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Only</em>' attribute.
   * @see #setOnly(boolean)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_Only()
   * @model
   * @generated
   */
  boolean isOnly();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#isOnly <em>Only</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Only</em>' attribute.
   * @see #isOnly()
   * @generated
   */
  void setOnly(boolean value);

  /**
   * Returns the value of the '<em><b>Exactly</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exactly</em>' attribute.
   * @see #setExactly(boolean)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_Exactly()
   * @model
   * @generated
   */
  boolean isExactly();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#isExactly <em>Exactly</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exactly</em>' attribute.
   * @see #isExactly()
   * @generated
   */
  void setExactly(boolean value);

  /**
   * Returns the value of the '<em><b>At Least</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>At Least</em>' attribute.
   * @see #setAtLeast(boolean)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_AtLeast()
   * @model
   * @generated
   */
  boolean isAtLeast();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#isAtLeast <em>At Least</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>At Least</em>' attribute.
   * @see #isAtLeast()
   * @generated
   */
  void setAtLeast(boolean value);

  /**
   * Returns the value of the '<em><b>At Most</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>At Most</em>' attribute.
   * @see #setAtMost(boolean)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_AtMost()
   * @model
   * @generated
   */
  boolean isAtMost();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#isAtMost <em>At Most</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>At Most</em>' attribute.
   * @see #isAtMost()
   * @generated
   */
  void setAtMost(boolean value);

  /**
   * Returns the value of the '<em><b>Cardinality</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Cardinality</em>' attribute.
   * @see #setCardinality(int)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_Cardinality()
   * @model
   * @generated
   */
  int getCardinality();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#getCardinality <em>Cardinality</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Cardinality</em>' attribute.
   * @see #getCardinality()
   * @generated
   */
  void setCardinality(int value);

  /**
   * Returns the value of the '<em><b>Or More</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Or More</em>' attribute.
   * @see #setOrMore(boolean)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_OrMore()
   * @model
   * @generated
   */
  boolean isOrMore();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#isOrMore <em>Or More</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Or More</em>' attribute.
   * @see #isOrMore()
   * @generated
   */
  void setOrMore(boolean value);

  /**
   * Returns the value of the '<em><b>Property Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Property Target</em>' containment reference.
   * @see #setPropertyTarget(ConceptDeclaration)
   * @see org.integratedmodelling.kim.kim.KimPackage#getPropertyStatement_PropertyTarget()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getPropertyTarget();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kim.kim.PropertyStatement#getPropertyTarget <em>Property Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Property Target</em>' containment reference.
   * @see #getPropertyTarget()
   * @generated
   */
  void setPropertyTarget(ConceptDeclaration value);

} // PropertyStatement