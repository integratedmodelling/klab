/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Classifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getNum <em>Num</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getString <em>String</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getObservable <em>Observable</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getOp <em>Op</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getInt0 <em>Int0</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getLeftLimit <em>Left Limit</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getInt1 <em>Int1</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getRightLimit <em>Right Limit</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getSet <em>Set</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getDate <em>Date</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getExpr <em>Expr</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#getNodata <em>Nodata</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#isStar <em>Star</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.TableClassifier#isAnything <em>Anything</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier()
 * @model
 * @generated
 */
public interface TableClassifier extends EObject
{
  /**
   * Returns the value of the '<em><b>Boolean</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Boolean</em>' attribute.
   * @see #setBoolean(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Boolean()
   * @model
   * @generated
   */
  String getBoolean();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getBoolean <em>Boolean</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Boolean</em>' attribute.
   * @see #getBoolean()
   * @generated
   */
  void setBoolean(String value);

  /**
   * Returns the value of the '<em><b>Num</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Num</em>' containment reference.
   * @see #setNum(org.integratedmodelling.kactors.kactors.Number)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Num()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kactors.kactors.Number getNum();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getNum <em>Num</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Num</em>' containment reference.
   * @see #getNum()
   * @generated
   */
  void setNum(org.integratedmodelling.kactors.kactors.Number value);

  /**
   * Returns the value of the '<em><b>String</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>String</em>' attribute.
   * @see #setString(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_String()
   * @model
   * @generated
   */
  String getString();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getString <em>String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>String</em>' attribute.
   * @see #getString()
   * @generated
   */
  void setString(String value);

  /**
   * Returns the value of the '<em><b>Observable</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Observable</em>' containment reference.
   * @see #setObservable(Observable)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Observable()
   * @model containment="true"
   * @generated
   */
  Observable getObservable();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getObservable <em>Observable</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Observable</em>' containment reference.
   * @see #getObservable()
   * @generated
   */
  void setObservable(Observable value);

  /**
   * Returns the value of the '<em><b>Op</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Op</em>' containment reference.
   * @see #setOp(REL_OPERATOR)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Op()
   * @model containment="true"
   * @generated
   */
  REL_OPERATOR getOp();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getOp <em>Op</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Op</em>' containment reference.
   * @see #getOp()
   * @generated
   */
  void setOp(REL_OPERATOR value);

  /**
   * Returns the value of the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' containment reference.
   * @see #setExpression(org.integratedmodelling.kactors.kactors.Number)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Expression()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kactors.kactors.Number getExpression();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(org.integratedmodelling.kactors.kactors.Number value);

  /**
   * Returns the value of the '<em><b>Int0</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Int0</em>' containment reference.
   * @see #setInt0(org.integratedmodelling.kactors.kactors.Number)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Int0()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kactors.kactors.Number getInt0();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getInt0 <em>Int0</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Int0</em>' containment reference.
   * @see #getInt0()
   * @generated
   */
  void setInt0(org.integratedmodelling.kactors.kactors.Number value);

  /**
   * Returns the value of the '<em><b>Left Limit</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left Limit</em>' attribute.
   * @see #setLeftLimit(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_LeftLimit()
   * @model
   * @generated
   */
  String getLeftLimit();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getLeftLimit <em>Left Limit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left Limit</em>' attribute.
   * @see #getLeftLimit()
   * @generated
   */
  void setLeftLimit(String value);

  /**
   * Returns the value of the '<em><b>Int1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Int1</em>' containment reference.
   * @see #setInt1(org.integratedmodelling.kactors.kactors.Number)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Int1()
   * @model containment="true"
   * @generated
   */
  org.integratedmodelling.kactors.kactors.Number getInt1();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getInt1 <em>Int1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Int1</em>' containment reference.
   * @see #getInt1()
   * @generated
   */
  void setInt1(org.integratedmodelling.kactors.kactors.Number value);

  /**
   * Returns the value of the '<em><b>Right Limit</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right Limit</em>' attribute.
   * @see #setRightLimit(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_RightLimit()
   * @model
   * @generated
   */
  String getRightLimit();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getRightLimit <em>Right Limit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right Limit</em>' attribute.
   * @see #getRightLimit()
   * @generated
   */
  void setRightLimit(String value);

  /**
   * Returns the value of the '<em><b>Set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Set</em>' containment reference.
   * @see #setSet(List)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Set()
   * @model containment="true"
   * @generated
   */
  List getSet();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getSet <em>Set</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Set</em>' containment reference.
   * @see #getSet()
   * @generated
   */
  void setSet(List value);

  /**
   * Returns the value of the '<em><b>Quantity</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Quantity</em>' containment reference.
   * @see #setQuantity(Quantity)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Quantity()
   * @model containment="true"
   * @generated
   */
  Quantity getQuantity();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getQuantity <em>Quantity</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Quantity</em>' containment reference.
   * @see #getQuantity()
   * @generated
   */
  void setQuantity(Quantity value);

  /**
   * Returns the value of the '<em><b>Date</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' containment reference.
   * @see #setDate(Date)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Date()
   * @model containment="true"
   * @generated
   */
  Date getDate();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getDate <em>Date</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date</em>' containment reference.
   * @see #getDate()
   * @generated
   */
  void setDate(Date value);

  /**
   * Returns the value of the '<em><b>Expr</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr</em>' attribute.
   * @see #setExpr(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Expr()
   * @model
   * @generated
   */
  String getExpr();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getExpr <em>Expr</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' attribute.
   * @see #getExpr()
   * @generated
   */
  void setExpr(String value);

  /**
   * Returns the value of the '<em><b>Nodata</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nodata</em>' attribute.
   * @see #setNodata(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Nodata()
   * @model
   * @generated
   */
  String getNodata();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#getNodata <em>Nodata</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Nodata</em>' attribute.
   * @see #getNodata()
   * @generated
   */
  void setNodata(String value);

  /**
   * Returns the value of the '<em><b>Star</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Star</em>' attribute.
   * @see #setStar(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Star()
   * @model
   * @generated
   */
  boolean isStar();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#isStar <em>Star</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Star</em>' attribute.
   * @see #isStar()
   * @generated
   */
  void setStar(boolean value);

  /**
   * Returns the value of the '<em><b>Anything</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Anything</em>' attribute.
   * @see #setAnything(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getTableClassifier_Anything()
   * @model
   * @generated
   */
  boolean isAnything();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.TableClassifier#isAnything <em>Anything</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Anything</em>' attribute.
   * @see #isAnything()
   * @generated
   */
  void setAnything(boolean value);

} // TableClassifier
