/**
 * generated by Xtext 2.19.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getAssignment <em>Assignment</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getGroup <em>Group</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getInstantiation <em>Instantiation</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getVerb <em>Verb</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getText <em>Text</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getIf <em>If</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getWhile <em>While</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getDo <em>Do</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getFor <em>For</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getValue <em>Value</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Statement#getTag <em>Tag</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement()
 * @model
 * @generated
 */
public interface Statement extends EObject
{
  /**
   * Returns the value of the '<em><b>Assignment</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assignment</em>' containment reference.
   * @see #setAssignment(Assignment)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Assignment()
   * @model containment="true"
   * @generated
   */
  Assignment getAssignment();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getAssignment <em>Assignment</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Assignment</em>' containment reference.
   * @see #getAssignment()
   * @generated
   */
  void setAssignment(Assignment value);

  /**
   * Returns the value of the '<em><b>Group</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Group</em>' containment reference.
   * @see #setGroup(StatementGroup)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Group()
   * @model containment="true"
   * @generated
   */
  StatementGroup getGroup();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getGroup <em>Group</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Group</em>' containment reference.
   * @see #getGroup()
   * @generated
   */
  void setGroup(StatementGroup value);

  /**
   * Returns the value of the '<em><b>Instantiation</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Instantiation</em>' containment reference.
   * @see #setInstantiation(ActorInstantiation)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Instantiation()
   * @model containment="true"
   * @generated
   */
  ActorInstantiation getInstantiation();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getInstantiation <em>Instantiation</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Instantiation</em>' containment reference.
   * @see #getInstantiation()
   * @generated
   */
  void setInstantiation(ActorInstantiation value);

  /**
   * Returns the value of the '<em><b>Verb</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Verb</em>' containment reference.
   * @see #setVerb(MessageCall)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Verb()
   * @model containment="true"
   * @generated
   */
  MessageCall getVerb();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getVerb <em>Verb</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Verb</em>' containment reference.
   * @see #getVerb()
   * @generated
   */
  void setVerb(MessageCall value);

  /**
   * Returns the value of the '<em><b>Text</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Text</em>' attribute.
   * @see #setText(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Text()
   * @model
   * @generated
   */
  String getText();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getText <em>Text</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Text</em>' attribute.
   * @see #getText()
   * @generated
   */
  void setText(String value);

  /**
   * Returns the value of the '<em><b>Metadata</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Metadata</em>' containment reference.
   * @see #setMetadata(Metadata)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Metadata()
   * @model containment="true"
   * @generated
   */
  Metadata getMetadata();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getMetadata <em>Metadata</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metadata</em>' containment reference.
   * @see #getMetadata()
   * @generated
   */
  void setMetadata(Metadata value);

  /**
   * Returns the value of the '<em><b>If</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>If</em>' containment reference.
   * @see #setIf(IfStatement)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_If()
   * @model containment="true"
   * @generated
   */
  IfStatement getIf();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getIf <em>If</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>If</em>' containment reference.
   * @see #getIf()
   * @generated
   */
  void setIf(IfStatement value);

  /**
   * Returns the value of the '<em><b>While</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>While</em>' containment reference.
   * @see #setWhile(WhileStatement)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_While()
   * @model containment="true"
   * @generated
   */
  WhileStatement getWhile();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getWhile <em>While</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>While</em>' containment reference.
   * @see #getWhile()
   * @generated
   */
  void setWhile(WhileStatement value);

  /**
   * Returns the value of the '<em><b>Do</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Do</em>' containment reference.
   * @see #setDo(DoStatement)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Do()
   * @model containment="true"
   * @generated
   */
  DoStatement getDo();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getDo <em>Do</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Do</em>' containment reference.
   * @see #getDo()
   * @generated
   */
  void setDo(DoStatement value);

  /**
   * Returns the value of the '<em><b>For</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>For</em>' containment reference.
   * @see #setFor(ForStatement)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_For()
   * @model containment="true"
   * @generated
   */
  ForStatement getFor();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getFor <em>For</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>For</em>' containment reference.
   * @see #getFor()
   * @generated
   */
  void setFor(ForStatement value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(Value)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Value()
   * @model containment="true"
   * @generated
   */
  Value getValue();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(Value value);

  /**
   * Returns the value of the '<em><b>Tag</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tag</em>' attribute.
   * @see #setTag(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getStatement_Tag()
   * @model
   * @generated
   */
  String getTag();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Statement#getTag <em>Tag</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tag</em>' attribute.
   * @see #getTag()
   * @generated
   */
  void setTag(String value);

} // Statement