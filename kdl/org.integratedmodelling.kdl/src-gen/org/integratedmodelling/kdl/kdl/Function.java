/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kdl.kdl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getMediated <em>Mediated</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getName <em>Name</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getUrn <em>Urn</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getValue <em>Value</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getTarget <em>Target</em>}</li>
 *   <li>{@link org.integratedmodelling.kdl.kdl.Function#getChain <em>Chain</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction()
 * @model
 * @generated
 */
public interface Function extends EObject
{
  /**
   * Returns the value of the '<em><b>Mediated</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mediated</em>' attribute.
   * @see #setMediated(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Mediated()
   * @model
   * @generated
   */
  String getMediated();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Function#getMediated <em>Mediated</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mediated</em>' attribute.
   * @see #getMediated()
   * @generated
   */
  void setMediated(String value);

  /**
   * Returns the value of the '<em><b>Variable</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variable</em>' attribute.
   * @see #setVariable(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Variable()
   * @model
   * @generated
   */
  String getVariable();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Function#getVariable <em>Variable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Variable</em>' attribute.
   * @see #getVariable()
   * @generated
   */
  void setVariable(String value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Function#getName <em>Name</em>}' attribute.
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
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Parameters()
   * @model containment="true"
   * @generated
   */
  ParameterList getParameters();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Function#getParameters <em>Parameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameters</em>' containment reference.
   * @see #getParameters()
   * @generated
   */
  void setParameters(ParameterList value);

  /**
   * Returns the value of the '<em><b>Urn</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Urn</em>' containment reference.
   * @see #setUrn(Urn)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Urn()
   * @model containment="true"
   * @generated
   */
  Urn getUrn();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Function#getUrn <em>Urn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Urn</em>' containment reference.
   * @see #getUrn()
   * @generated
   */
  void setUrn(Urn value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(Literal)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Value()
   * @model containment="true"
   * @generated
   */
  Literal getValue();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Function#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(Literal value);

  /**
   * Returns the value of the '<em><b>Expression</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' attribute.
   * @see #setExpression(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Expression()
   * @model
   * @generated
   */
  String getExpression();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Function#getExpression <em>Expression</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' attribute.
   * @see #getExpression()
   * @generated
   */
  void setExpression(String value);

  /**
   * Returns the value of the '<em><b>Target</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' attribute.
   * @see #setTarget(String)
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Target()
   * @model
   * @generated
   */
  String getTarget();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kdl.kdl.Function#getTarget <em>Target</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' attribute.
   * @see #getTarget()
   * @generated
   */
  void setTarget(String value);

  /**
   * Returns the value of the '<em><b>Chain</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kdl.kdl.Function}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Chain</em>' containment reference list.
   * @see org.integratedmodelling.kdl.kdl.KdlPackage#getFunction_Chain()
   * @model containment="true"
   * @generated
   */
  EList<Function> getChain();

} // Function
