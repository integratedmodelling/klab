/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concept Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getMain <em>Main</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#isDistributedOfInherency <em>Distributed Of Inherency</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getInherency <em>Inherency</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#isDistributedForInherency <em>Distributed For Inherency</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getMotivation <em>Motivation</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getCompresent <em>Compresent</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getCausant <em>Causant</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getAdjacent <em>Adjacent</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getContainer <em>Container</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getContained <em>Contained</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getCaused <em>Caused</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#isDistributedTemporalInherency <em>Distributed Temporal Inherency</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getDuring <em>During</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#isDistributedWithinInherency <em>Distributed Within Inherency</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getContext <em>Context</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getRelationshipSource <em>Relationship Source</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getRelationshipTarget <em>Relationship Target</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getOperators <em>Operators</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getOperands <em>Operands</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration()
 * @model
 * @generated
 */
public interface ConceptDeclaration extends EObject
{
  /**
   * Returns the value of the '<em><b>Main</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kactors.kactors.Concept}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Main</em>' containment reference list.
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Main()
   * @model containment="true"
   * @generated
   */
  EList<Concept> getMain();

  /**
   * Returns the value of the '<em><b>Distributed Of Inherency</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Distributed Of Inherency</em>' attribute.
   * @see #setDistributedOfInherency(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_DistributedOfInherency()
   * @model
   * @generated
   */
  boolean isDistributedOfInherency();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#isDistributedOfInherency <em>Distributed Of Inherency</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Distributed Of Inherency</em>' attribute.
   * @see #isDistributedOfInherency()
   * @generated
   */
  void setDistributedOfInherency(boolean value);

  /**
   * Returns the value of the '<em><b>Inherency</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Inherency</em>' containment reference.
   * @see #setInherency(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Inherency()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getInherency();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getInherency <em>Inherency</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Inherency</em>' containment reference.
   * @see #getInherency()
   * @generated
   */
  void setInherency(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Distributed For Inherency</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Distributed For Inherency</em>' attribute.
   * @see #setDistributedForInherency(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_DistributedForInherency()
   * @model
   * @generated
   */
  boolean isDistributedForInherency();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#isDistributedForInherency <em>Distributed For Inherency</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Distributed For Inherency</em>' attribute.
   * @see #isDistributedForInherency()
   * @generated
   */
  void setDistributedForInherency(boolean value);

  /**
   * Returns the value of the '<em><b>Motivation</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Motivation</em>' containment reference.
   * @see #setMotivation(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Motivation()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getMotivation();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getMotivation <em>Motivation</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Motivation</em>' containment reference.
   * @see #getMotivation()
   * @generated
   */
  void setMotivation(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Compresent</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compresent</em>' containment reference.
   * @see #setCompresent(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Compresent()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getCompresent();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getCompresent <em>Compresent</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Compresent</em>' containment reference.
   * @see #getCompresent()
   * @generated
   */
  void setCompresent(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Causant</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Causant</em>' containment reference.
   * @see #setCausant(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Causant()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getCausant();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getCausant <em>Causant</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Causant</em>' containment reference.
   * @see #getCausant()
   * @generated
   */
  void setCausant(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Adjacent</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Adjacent</em>' containment reference.
   * @see #setAdjacent(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Adjacent()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getAdjacent();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getAdjacent <em>Adjacent</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Adjacent</em>' containment reference.
   * @see #getAdjacent()
   * @generated
   */
  void setAdjacent(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Container</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Container</em>' containment reference.
   * @see #setContainer(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Container()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getContainer();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getContainer <em>Container</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Container</em>' containment reference.
   * @see #getContainer()
   * @generated
   */
  void setContainer(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Contained</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Contained</em>' containment reference.
   * @see #setContained(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Contained()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getContained();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getContained <em>Contained</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Contained</em>' containment reference.
   * @see #getContained()
   * @generated
   */
  void setContained(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Caused</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Caused</em>' containment reference.
   * @see #setCaused(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Caused()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getCaused();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getCaused <em>Caused</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Caused</em>' containment reference.
   * @see #getCaused()
   * @generated
   */
  void setCaused(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Distributed Temporal Inherency</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Distributed Temporal Inherency</em>' attribute.
   * @see #setDistributedTemporalInherency(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_DistributedTemporalInherency()
   * @model
   * @generated
   */
  boolean isDistributedTemporalInherency();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#isDistributedTemporalInherency <em>Distributed Temporal Inherency</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Distributed Temporal Inherency</em>' attribute.
   * @see #isDistributedTemporalInherency()
   * @generated
   */
  void setDistributedTemporalInherency(boolean value);

  /**
   * Returns the value of the '<em><b>During</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>During</em>' containment reference.
   * @see #setDuring(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_During()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getDuring();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getDuring <em>During</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>During</em>' containment reference.
   * @see #getDuring()
   * @generated
   */
  void setDuring(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Distributed Within Inherency</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Distributed Within Inherency</em>' attribute.
   * @see #setDistributedWithinInherency(boolean)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_DistributedWithinInherency()
   * @model
   * @generated
   */
  boolean isDistributedWithinInherency();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#isDistributedWithinInherency <em>Distributed Within Inherency</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Distributed Within Inherency</em>' attribute.
   * @see #isDistributedWithinInherency()
   * @generated
   */
  void setDistributedWithinInherency(boolean value);

  /**
   * Returns the value of the '<em><b>Context</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Context</em>' containment reference.
   * @see #setContext(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Context()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getContext();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getContext <em>Context</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Context</em>' containment reference.
   * @see #getContext()
   * @generated
   */
  void setContext(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Relationship Source</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relationship Source</em>' containment reference.
   * @see #setRelationshipSource(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_RelationshipSource()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getRelationshipSource();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getRelationshipSource <em>Relationship Source</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Relationship Source</em>' containment reference.
   * @see #getRelationshipSource()
   * @generated
   */
  void setRelationshipSource(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Relationship Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relationship Target</em>' containment reference.
   * @see #setRelationshipTarget(ConceptDeclaration)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_RelationshipTarget()
   * @model containment="true"
   * @generated
   */
  ConceptDeclaration getRelationshipTarget();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getRelationshipTarget <em>Relationship Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Relationship Target</em>' containment reference.
   * @see #getRelationshipTarget()
   * @generated
   */
  void setRelationshipTarget(ConceptDeclaration value);

  /**
   * Returns the value of the '<em><b>Operators</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operators</em>' attribute list.
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Operators()
   * @model unique="false"
   * @generated
   */
  EList<String> getOperators();

  /**
   * Returns the value of the '<em><b>Operands</b></em>' containment reference list.
   * The list contents are of type {@link org.integratedmodelling.kactors.kactors.ConceptDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operands</em>' containment reference list.
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Operands()
   * @model containment="true"
   * @generated
   */
  EList<ConceptDeclaration> getOperands();

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.integratedmodelling.kactors.kactors.KactorsPackage#getConceptDeclaration_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.integratedmodelling.kactors.kactors.ConceptDeclaration#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // ConceptDeclaration
