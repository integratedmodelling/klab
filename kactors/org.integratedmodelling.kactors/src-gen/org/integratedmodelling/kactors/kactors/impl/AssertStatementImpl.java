/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.integratedmodelling.kactors.kactors.AssertStatement;
import org.integratedmodelling.kactors.kactors.Assertion;
import org.integratedmodelling.kactors.kactors.KactorsPackage;
import org.integratedmodelling.kactors.kactors.ParameterList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assert Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.impl.AssertStatementImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.impl.AssertStatementImpl#getAssertions <em>Assertions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssertStatementImpl extends MinimalEObjectImpl.Container implements AssertStatement
{
  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected ParameterList parameters;

  /**
   * The cached value of the '{@link #getAssertions() <em>Assertions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssertions()
   * @generated
   * @ordered
   */
  protected EList<Assertion> assertions;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AssertStatementImpl()
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
    return KactorsPackage.Literals.ASSERT_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ParameterList getParameters()
  {
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetParameters(ParameterList newParameters, NotificationChain msgs)
  {
    ParameterList oldParameters = parameters;
    parameters = newParameters;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KactorsPackage.ASSERT_STATEMENT__PARAMETERS, oldParameters, newParameters);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setParameters(ParameterList newParameters)
  {
    if (newParameters != parameters)
    {
      NotificationChain msgs = null;
      if (parameters != null)
        msgs = ((InternalEObject)parameters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KactorsPackage.ASSERT_STATEMENT__PARAMETERS, null, msgs);
      if (newParameters != null)
        msgs = ((InternalEObject)newParameters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KactorsPackage.ASSERT_STATEMENT__PARAMETERS, null, msgs);
      msgs = basicSetParameters(newParameters, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KactorsPackage.ASSERT_STATEMENT__PARAMETERS, newParameters, newParameters));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Assertion> getAssertions()
  {
    if (assertions == null)
    {
      assertions = new EObjectContainmentEList<Assertion>(Assertion.class, this, KactorsPackage.ASSERT_STATEMENT__ASSERTIONS);
    }
    return assertions;
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
      case KactorsPackage.ASSERT_STATEMENT__PARAMETERS:
        return basicSetParameters(null, msgs);
      case KactorsPackage.ASSERT_STATEMENT__ASSERTIONS:
        return ((InternalEList<?>)getAssertions()).basicRemove(otherEnd, msgs);
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
      case KactorsPackage.ASSERT_STATEMENT__PARAMETERS:
        return getParameters();
      case KactorsPackage.ASSERT_STATEMENT__ASSERTIONS:
        return getAssertions();
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
      case KactorsPackage.ASSERT_STATEMENT__PARAMETERS:
        setParameters((ParameterList)newValue);
        return;
      case KactorsPackage.ASSERT_STATEMENT__ASSERTIONS:
        getAssertions().clear();
        getAssertions().addAll((Collection<? extends Assertion>)newValue);
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
      case KactorsPackage.ASSERT_STATEMENT__PARAMETERS:
        setParameters((ParameterList)null);
        return;
      case KactorsPackage.ASSERT_STATEMENT__ASSERTIONS:
        getAssertions().clear();
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
      case KactorsPackage.ASSERT_STATEMENT__PARAMETERS:
        return parameters != null;
      case KactorsPackage.ASSERT_STATEMENT__ASSERTIONS:
        return assertions != null && !assertions.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //AssertStatementImpl
