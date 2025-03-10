/**
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kim.kim.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.integratedmodelling.kim.kim.DocSelector;
import org.integratedmodelling.kim.kim.KimPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Doc Selector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.DocSelectorImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.DocSelectorImpl#isDefinition <em>Definition</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.DocSelectorImpl#isInitialization <em>Initialization</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.DocSelectorImpl#isTermination <em>Termination</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.DocSelectorImpl#isTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocSelectorImpl extends MinimalEObjectImpl.Container implements DocSelector
{
  /**
   * The default value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected static final String ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected String id = ID_EDEFAULT;

  /**
   * The default value of the '{@link #isDefinition() <em>Definition</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isDefinition()
   * @generated
   * @ordered
   */
  protected static final boolean DEFINITION_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isDefinition() <em>Definition</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isDefinition()
   * @generated
   * @ordered
   */
  protected boolean definition = DEFINITION_EDEFAULT;

  /**
   * The default value of the '{@link #isInitialization() <em>Initialization</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isInitialization()
   * @generated
   * @ordered
   */
  protected static final boolean INITIALIZATION_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isInitialization() <em>Initialization</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isInitialization()
   * @generated
   * @ordered
   */
  protected boolean initialization = INITIALIZATION_EDEFAULT;

  /**
   * The default value of the '{@link #isTermination() <em>Termination</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isTermination()
   * @generated
   * @ordered
   */
  protected static final boolean TERMINATION_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isTermination() <em>Termination</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isTermination()
   * @generated
   * @ordered
   */
  protected boolean termination = TERMINATION_EDEFAULT;

  /**
   * The default value of the '{@link #isTransition() <em>Transition</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isTransition()
   * @generated
   * @ordered
   */
  protected static final boolean TRANSITION_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isTransition() <em>Transition</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isTransition()
   * @generated
   * @ordered
   */
  protected boolean transition = TRANSITION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DocSelectorImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return KimPackage.Literals.DOC_SELECTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getId()
  {
    return id;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setId(String newId)
  {
    String oldId = id;
    id = newId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.DOC_SELECTOR__ID, oldId, id));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isDefinition()
  {
    return definition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDefinition(boolean newDefinition)
  {
    boolean oldDefinition = definition;
    definition = newDefinition;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.DOC_SELECTOR__DEFINITION, oldDefinition, definition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isInitialization()
  {
    return initialization;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInitialization(boolean newInitialization)
  {
    boolean oldInitialization = initialization;
    initialization = newInitialization;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.DOC_SELECTOR__INITIALIZATION, oldInitialization, initialization));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isTermination()
  {
    return termination;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTermination(boolean newTermination)
  {
    boolean oldTermination = termination;
    termination = newTermination;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.DOC_SELECTOR__TERMINATION, oldTermination, termination));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isTransition()
  {
    return transition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTransition(boolean newTransition)
  {
    boolean oldTransition = transition;
    transition = newTransition;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.DOC_SELECTOR__TRANSITION, oldTransition, transition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case KimPackage.DOC_SELECTOR__ID:
        return getId();
      case KimPackage.DOC_SELECTOR__DEFINITION:
        return isDefinition();
      case KimPackage.DOC_SELECTOR__INITIALIZATION:
        return isInitialization();
      case KimPackage.DOC_SELECTOR__TERMINATION:
        return isTermination();
      case KimPackage.DOC_SELECTOR__TRANSITION:
        return isTransition();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case KimPackage.DOC_SELECTOR__ID:
        setId((String)newValue);
        return;
      case KimPackage.DOC_SELECTOR__DEFINITION:
        setDefinition((Boolean)newValue);
        return;
      case KimPackage.DOC_SELECTOR__INITIALIZATION:
        setInitialization((Boolean)newValue);
        return;
      case KimPackage.DOC_SELECTOR__TERMINATION:
        setTermination((Boolean)newValue);
        return;
      case KimPackage.DOC_SELECTOR__TRANSITION:
        setTransition((Boolean)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case KimPackage.DOC_SELECTOR__ID:
        setId(ID_EDEFAULT);
        return;
      case KimPackage.DOC_SELECTOR__DEFINITION:
        setDefinition(DEFINITION_EDEFAULT);
        return;
      case KimPackage.DOC_SELECTOR__INITIALIZATION:
        setInitialization(INITIALIZATION_EDEFAULT);
        return;
      case KimPackage.DOC_SELECTOR__TERMINATION:
        setTermination(TERMINATION_EDEFAULT);
        return;
      case KimPackage.DOC_SELECTOR__TRANSITION:
        setTransition(TRANSITION_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case KimPackage.DOC_SELECTOR__ID:
        return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
      case KimPackage.DOC_SELECTOR__DEFINITION:
        return definition != DEFINITION_EDEFAULT;
      case KimPackage.DOC_SELECTOR__INITIALIZATION:
        return initialization != INITIALIZATION_EDEFAULT;
      case KimPackage.DOC_SELECTOR__TERMINATION:
        return termination != TERMINATION_EDEFAULT;
      case KimPackage.DOC_SELECTOR__TRANSITION:
        return transition != TRANSITION_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (id: ");
    result.append(id);
    result.append(", definition: ");
    result.append(definition);
    result.append(", initialization: ");
    result.append(initialization);
    result.append(", termination: ");
    result.append(termination);
    result.append(", transition: ");
    result.append(transition);
    result.append(')');
    return result.toString();
  }

} //DocSelectorImpl
