/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kdl.kdl.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.integratedmodelling.kdl.kdl.ClassifierRHS;
import org.integratedmodelling.kdl.kdl.KdlPackage;
import org.integratedmodelling.kdl.kdl.TableRow;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Row</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kdl.kdl.impl.TableRowImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableRowImpl extends MinimalEObjectImpl.Container implements TableRow
{
  /**
   * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElements()
   * @generated
   * @ordered
   */
  protected EList<ClassifierRHS> elements;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TableRowImpl()
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
    return KdlPackage.Literals.TABLE_ROW;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ClassifierRHS> getElements()
  {
    if (elements == null)
    {
      elements = new EObjectContainmentEList<ClassifierRHS>(ClassifierRHS.class, this, KdlPackage.TABLE_ROW__ELEMENTS);
    }
    return elements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case KdlPackage.TABLE_ROW__ELEMENTS:
        return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case KdlPackage.TABLE_ROW__ELEMENTS:
        return getElements();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case KdlPackage.TABLE_ROW__ELEMENTS:
        getElements().clear();
        getElements().addAll((Collection<? extends ClassifierRHS>)newValue);
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
      case KdlPackage.TABLE_ROW__ELEMENTS:
        getElements().clear();
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
      case KdlPackage.TABLE_ROW__ELEMENTS:
        return elements != null && !elements.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //TableRowImpl
