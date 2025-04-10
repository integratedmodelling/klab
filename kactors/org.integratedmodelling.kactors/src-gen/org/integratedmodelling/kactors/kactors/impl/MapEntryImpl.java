/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kactors.kactors.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.integratedmodelling.kactors.kactors.Classifier;
import org.integratedmodelling.kactors.kactors.KactorsPackage;
import org.integratedmodelling.kactors.kactors.MapEntry;
import org.integratedmodelling.kactors.kactors.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kactors.kactors.impl.MapEntryImpl#getClassifier <em>Classifier</em>}</li>
 *   <li>{@link org.integratedmodelling.kactors.kactors.impl.MapEntryImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MapEntryImpl extends MinimalEObjectImpl.Container implements MapEntry
{
  /**
   * The cached value of the '{@link #getClassifier() <em>Classifier</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getClassifier()
   * @generated
   * @ordered
   */
  protected Classifier classifier;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected Value value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MapEntryImpl()
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
    return KactorsPackage.Literals.MAP_ENTRY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Classifier getClassifier()
  {
    return classifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetClassifier(Classifier newClassifier, NotificationChain msgs)
  {
    Classifier oldClassifier = classifier;
    classifier = newClassifier;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KactorsPackage.MAP_ENTRY__CLASSIFIER, oldClassifier, newClassifier);
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
  public void setClassifier(Classifier newClassifier)
  {
    if (newClassifier != classifier)
    {
      NotificationChain msgs = null;
      if (classifier != null)
        msgs = ((InternalEObject)classifier).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KactorsPackage.MAP_ENTRY__CLASSIFIER, null, msgs);
      if (newClassifier != null)
        msgs = ((InternalEObject)newClassifier).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KactorsPackage.MAP_ENTRY__CLASSIFIER, null, msgs);
      msgs = basicSetClassifier(newClassifier, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KactorsPackage.MAP_ENTRY__CLASSIFIER, newClassifier, newClassifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Value getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetValue(Value newValue, NotificationChain msgs)
  {
    Value oldValue = value;
    value = newValue;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KactorsPackage.MAP_ENTRY__VALUE, oldValue, newValue);
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
  public void setValue(Value newValue)
  {
    if (newValue != value)
    {
      NotificationChain msgs = null;
      if (value != null)
        msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KactorsPackage.MAP_ENTRY__VALUE, null, msgs);
      if (newValue != null)
        msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KactorsPackage.MAP_ENTRY__VALUE, null, msgs);
      msgs = basicSetValue(newValue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KactorsPackage.MAP_ENTRY__VALUE, newValue, newValue));
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
      case KactorsPackage.MAP_ENTRY__CLASSIFIER:
        return basicSetClassifier(null, msgs);
      case KactorsPackage.MAP_ENTRY__VALUE:
        return basicSetValue(null, msgs);
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
      case KactorsPackage.MAP_ENTRY__CLASSIFIER:
        return getClassifier();
      case KactorsPackage.MAP_ENTRY__VALUE:
        return getValue();
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
      case KactorsPackage.MAP_ENTRY__CLASSIFIER:
        setClassifier((Classifier)newValue);
        return;
      case KactorsPackage.MAP_ENTRY__VALUE:
        setValue((Value)newValue);
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
      case KactorsPackage.MAP_ENTRY__CLASSIFIER:
        setClassifier((Classifier)null);
        return;
      case KactorsPackage.MAP_ENTRY__VALUE:
        setValue((Value)null);
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
      case KactorsPackage.MAP_ENTRY__CLASSIFIER:
        return classifier != null;
      case KactorsPackage.MAP_ENTRY__VALUE:
        return value != null;
    }
    return super.eIsSet(featureID);
  }

} //MapEntryImpl
