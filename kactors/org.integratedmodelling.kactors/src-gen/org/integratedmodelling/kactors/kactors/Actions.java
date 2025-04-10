/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Actions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Actions#getMatch <em>Match</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Actions#getMatches <em>Matches</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Actions#getStatement <em>Statement</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.Actions#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getActions()
 * @model
 * @generated
 */
public interface Actions extends EObject
{
  /**
   * Returns the value of the '<em><b>Match</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Match</em>' containment reference.
   * @see #setMatch(Match)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getActions_Match()
   * @model containment="true"
   * @generated
   */
  Match getMatch();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Actions#getMatch <em>Match</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Match</em>' containment reference.
   * @see #getMatch()
   * @generated
   */
  void setMatch(Match value);

  /**
   * Returns the value of the '<em><b>Matches</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kactors.kactors.Match}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Matches</em>' containment reference list.
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getActions_Matches()
   * @model containment="true"
   * @generated
   */
  EList<Match> getMatches();

  /**
   * Returns the value of the '<em><b>Statement</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Statement</em>' containment reference.
   * @see #setStatement(Statement)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getActions_Statement()
   * @model containment="true"
   * @generated
   */
  Statement getStatement();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Actions#getStatement <em>Statement</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Statement</em>' containment reference.
   * @see #getStatement()
   * @generated
   */
  void setStatement(Statement value);

  /**
   * Returns the value of the '<em><b>Statements</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Statements</em>' containment reference.
   * @see #setStatements(StatementList)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getActions_Statements()
   * @model containment="true"
   * @generated
   */
  StatementList getStatements();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.Actions#getStatements <em>Statements</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Statements</em>' containment reference.
   * @see #getStatements()
   * @generated
   */
  void setStatements(StatementList value);

} // Actions
