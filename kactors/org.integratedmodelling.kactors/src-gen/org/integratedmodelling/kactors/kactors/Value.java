/**
 * generated by Xtext 2.19.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getTree <em>Tree</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#isEmpty <em>Empty</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getArgvalue <em>Argvalue</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getLiteral <em>Literal</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getUrn <em>Urn</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getId <em>Id</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getList <em>List</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getMap <em>Map</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getConstant <em>Constant</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getObservable <em>Observable</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getTable <em>Table</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Value#getMetadata <em>Metadata</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue()
 * @model
 * @generated
 */
public interface Value extends EObject
{
  /**
   * Returns the value of the '<em><b>Tree</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tree</em>' containment reference.
   * @see #setTree(Tree)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Tree()
   * @model containment="true"
   * @generated
   */
  Tree getTree();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getTree <em>Tree</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tree</em>' containment reference.
   * @see #getTree()
   * @generated
   */
  void setTree(Tree value);

  /**
   * Returns the value of the '<em><b>Empty</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Empty</em>' attribute.
   * @see #setEmpty(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Empty()
   * @model
   * @generated
   */
  boolean isEmpty();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#isEmpty <em>Empty</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Empty</em>' attribute.
   * @see #isEmpty()
   * @generated
   */
  void setEmpty(boolean value);

  /**
   * Returns the value of the '<em><b>Argvalue</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Argvalue</em>' attribute.
   * @see #setArgvalue(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Argvalue()
   * @model
   * @generated
   */
  String getArgvalue();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getArgvalue <em>Argvalue</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Argvalue</em>' attribute.
   * @see #getArgvalue()
   * @generated
   */
  void setArgvalue(String value);

  /**
   * Returns the value of the '<em><b>Literal</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Literal</em>' containment reference.
   * @see #setLiteral(Literal)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Literal()
   * @model containment="true"
   * @generated
   */
  Literal getLiteral();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getLiteral <em>Literal</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Literal</em>' containment reference.
   * @see #getLiteral()
   * @generated
   */
  void setLiteral(Literal value);

  /**
   * Returns the value of the '<em><b>Urn</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Urn</em>' attribute.
   * @see #setUrn(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Urn()
   * @model
   * @generated
   */
  String getUrn();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getUrn <em>Urn</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Urn</em>' attribute.
   * @see #getUrn()
   * @generated
   */
  void setUrn(String value);

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Id()
   * @model
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Returns the value of the '<em><b>List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>List</em>' containment reference.
   * @see #setList(List)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_List()
   * @model containment="true"
   * @generated
   */
  List getList();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getList <em>List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>List</em>' containment reference.
   * @see #getList()
   * @generated
   */
  void setList(List value);

  /**
   * Returns the value of the '<em><b>Map</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Map</em>' containment reference.
   * @see #setMap(Map)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Map()
   * @model containment="true"
   * @generated
   */
  Map getMap();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getMap <em>Map</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Map</em>' containment reference.
   * @see #getMap()
   * @generated
   */
  void setMap(Map value);

  /**
   * Returns the value of the '<em><b>Constant</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constant</em>' attribute.
   * @see #setConstant(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Constant()
   * @model
   * @generated
   */
  String getConstant();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getConstant <em>Constant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constant</em>' attribute.
   * @see #getConstant()
   * @generated
   */
  void setConstant(String value);

  /**
   * Returns the value of the '<em><b>Observable</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Observable</em>' containment reference.
   * @see #setObservable(Observable)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Observable()
   * @model containment="true"
   * @generated
   */
  Observable getObservable();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getObservable <em>Observable</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Observable</em>' containment reference.
   * @see #getObservable()
   * @generated
   */
  void setObservable(Observable value);

  /**
   * Returns the value of the '<em><b>Expression</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' attribute.
   * @see #setExpression(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Expression()
   * @model
   * @generated
   */
  String getExpression();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getExpression <em>Expression</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' attribute.
   * @see #getExpression()
   * @generated
   */
  void setExpression(String value);

  /**
   * Returns the value of the '<em><b>Table</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Table</em>' containment reference.
   * @see #setTable(LookupTable)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Table()
   * @model containment="true"
   * @generated
   */
  LookupTable getTable();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getTable <em>Table</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Table</em>' containment reference.
   * @see #getTable()
   * @generated
   */
  void setTable(LookupTable value);

  /**
   * Returns the value of the '<em><b>Quantity</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Quantity</em>' containment reference.
   * @see #setQuantity(Quantity)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Quantity()
   * @model containment="true"
   * @generated
   */
  Quantity getQuantity();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getQuantity <em>Quantity</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Quantity</em>' containment reference.
   * @see #getQuantity()
   * @generated
   */
  void setQuantity(Quantity value);

  /**
   * Returns the value of the '<em><b>Metadata</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Metadata</em>' containment reference.
   * @see #setMetadata(Metadata)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getValue_Metadata()
   * @model containment="true"
   * @generated
   */
  Metadata getMetadata();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Value#getMetadata <em>Metadata</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metadata</em>' containment reference.
   * @see #getMetadata()
   * @generated
   */
  void setMetadata(Metadata value);

} // Value