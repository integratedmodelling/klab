/**
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kim.kim.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.integratedmodelling.kim.kim.ClassifierRHS;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.Date;
import org.integratedmodelling.kim.kim.KimPackage;
import org.integratedmodelling.kim.kim.List;
import org.integratedmodelling.kim.kim.Quantity;
import org.integratedmodelling.kim.kim.REL_OPERATOR;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Classifier RHS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getInt0 <em>Int0</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getLeftLimit <em>Left Limit</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getInt1 <em>Int1</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getRightLimit <em>Right Limit</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getNum <em>Num</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getSet <em>Set</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getString <em>String</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getConcept <em>Concept</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getToResolve <em>To Resolve</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getOp <em>Op</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getNodata <em>Nodata</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#isStar <em>Star</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getDate <em>Date</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#getExpr <em>Expr</em>}</li>
 *   <li>{@link org.integratedmodelling.kim.kim.impl.ClassifierRHSImpl#isAnything <em>Anything</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassifierRHSImpl extends TableRowImpl implements ClassifierRHS
{
  /**
   * The default value of the '{@link #getBoolean() <em>Boolean</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBoolean()
   * @generated
   * @ordered
   */
  protected static final String BOOLEAN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBoolean() <em>Boolean</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBoolean()
   * @generated
   * @ordered
   */
  protected String boolean_ = BOOLEAN_EDEFAULT;

  /**
   * The cached value of the '{@link #getInt0() <em>Int0</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInt0()
   * @generated
   * @ordered
   */
  protected org.integratedmodelling.kim.kim.Number int0;

  /**
   * The default value of the '{@link #getLeftLimit() <em>Left Limit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLeftLimit()
   * @generated
   * @ordered
   */
  protected static final String LEFT_LIMIT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLeftLimit() <em>Left Limit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLeftLimit()
   * @generated
   * @ordered
   */
  protected String leftLimit = LEFT_LIMIT_EDEFAULT;

  /**
   * The cached value of the '{@link #getInt1() <em>Int1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInt1()
   * @generated
   * @ordered
   */
  protected org.integratedmodelling.kim.kim.Number int1;

  /**
   * The default value of the '{@link #getRightLimit() <em>Right Limit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRightLimit()
   * @generated
   * @ordered
   */
  protected static final String RIGHT_LIMIT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRightLimit() <em>Right Limit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRightLimit()
   * @generated
   * @ordered
   */
  protected String rightLimit = RIGHT_LIMIT_EDEFAULT;

  /**
   * The cached value of the '{@link #getNum() <em>Num</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNum()
   * @generated
   * @ordered
   */
  protected org.integratedmodelling.kim.kim.Number num;

  /**
   * The cached value of the '{@link #getSet() <em>Set</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSet()
   * @generated
   * @ordered
   */
  protected List set;

  /**
   * The default value of the '{@link #getString() <em>String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getString()
   * @generated
   * @ordered
   */
  protected static final String STRING_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getString() <em>String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getString()
   * @generated
   * @ordered
   */
  protected String string = STRING_EDEFAULT;

  /**
   * The cached value of the '{@link #getConcept() <em>Concept</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConcept()
   * @generated
   * @ordered
   */
  protected ConceptDeclaration concept;

  /**
   * The cached value of the '{@link #getToResolve() <em>To Resolve</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToResolve()
   * @generated
   * @ordered
   */
  protected EList<ConceptDeclaration> toResolve;

  /**
   * The cached value of the '{@link #getOp() <em>Op</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOp()
   * @generated
   * @ordered
   */
  protected REL_OPERATOR op;

  /**
   * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpression()
   * @generated
   * @ordered
   */
  protected org.integratedmodelling.kim.kim.Number expression;

  /**
   * The default value of the '{@link #getNodata() <em>Nodata</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNodata()
   * @generated
   * @ordered
   */
  protected static final String NODATA_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNodata() <em>Nodata</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNodata()
   * @generated
   * @ordered
   */
  protected String nodata = NODATA_EDEFAULT;

  /**
   * The default value of the '{@link #isStar() <em>Star</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isStar()
   * @generated
   * @ordered
   */
  protected static final boolean STAR_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isStar() <em>Star</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isStar()
   * @generated
   * @ordered
   */
  protected boolean star = STAR_EDEFAULT;

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
   * The cached value of the '{@link #getQuantity() <em>Quantity</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQuantity()
   * @generated
   * @ordered
   */
  protected Quantity quantity;

  /**
   * The cached value of the '{@link #getDate() <em>Date</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDate()
   * @generated
   * @ordered
   */
  protected Date date;

  /**
   * The default value of the '{@link #getExpr() <em>Expr</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpr()
   * @generated
   * @ordered
   */
  protected static final String EXPR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExpr() <em>Expr</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpr()
   * @generated
   * @ordered
   */
  protected String expr = EXPR_EDEFAULT;

  /**
   * The default value of the '{@link #isAnything() <em>Anything</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAnything()
   * @generated
   * @ordered
   */
  protected static final boolean ANYTHING_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isAnything() <em>Anything</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAnything()
   * @generated
   * @ordered
   */
  protected boolean anything = ANYTHING_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ClassifierRHSImpl()
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
    return KimPackage.Literals.CLASSIFIER_RHS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getBoolean()
  {
    return boolean_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBoolean(String newBoolean)
  {
    String oldBoolean = boolean_;
    boolean_ = newBoolean;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__BOOLEAN, oldBoolean, boolean_));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public org.integratedmodelling.kim.kim.Number getInt0()
  {
    return int0;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInt0(org.integratedmodelling.kim.kim.Number newInt0, NotificationChain msgs)
  {
    org.integratedmodelling.kim.kim.Number oldInt0 = int0;
    int0 = newInt0;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__INT0, oldInt0, newInt0);
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
  public void setInt0(org.integratedmodelling.kim.kim.Number newInt0)
  {
    if (newInt0 != int0)
    {
      NotificationChain msgs = null;
      if (int0 != null)
        msgs = ((InternalEObject)int0).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__INT0, null, msgs);
      if (newInt0 != null)
        msgs = ((InternalEObject)newInt0).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__INT0, null, msgs);
      msgs = basicSetInt0(newInt0, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__INT0, newInt0, newInt0));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLeftLimit()
  {
    return leftLimit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLeftLimit(String newLeftLimit)
  {
    String oldLeftLimit = leftLimit;
    leftLimit = newLeftLimit;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__LEFT_LIMIT, oldLeftLimit, leftLimit));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public org.integratedmodelling.kim.kim.Number getInt1()
  {
    return int1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInt1(org.integratedmodelling.kim.kim.Number newInt1, NotificationChain msgs)
  {
    org.integratedmodelling.kim.kim.Number oldInt1 = int1;
    int1 = newInt1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__INT1, oldInt1, newInt1);
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
  public void setInt1(org.integratedmodelling.kim.kim.Number newInt1)
  {
    if (newInt1 != int1)
    {
      NotificationChain msgs = null;
      if (int1 != null)
        msgs = ((InternalEObject)int1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__INT1, null, msgs);
      if (newInt1 != null)
        msgs = ((InternalEObject)newInt1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__INT1, null, msgs);
      msgs = basicSetInt1(newInt1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__INT1, newInt1, newInt1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getRightLimit()
  {
    return rightLimit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRightLimit(String newRightLimit)
  {
    String oldRightLimit = rightLimit;
    rightLimit = newRightLimit;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__RIGHT_LIMIT, oldRightLimit, rightLimit));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public org.integratedmodelling.kim.kim.Number getNum()
  {
    return num;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetNum(org.integratedmodelling.kim.kim.Number newNum, NotificationChain msgs)
  {
    org.integratedmodelling.kim.kim.Number oldNum = num;
    num = newNum;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__NUM, oldNum, newNum);
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
  public void setNum(org.integratedmodelling.kim.kim.Number newNum)
  {
    if (newNum != num)
    {
      NotificationChain msgs = null;
      if (num != null)
        msgs = ((InternalEObject)num).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__NUM, null, msgs);
      if (newNum != null)
        msgs = ((InternalEObject)newNum).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__NUM, null, msgs);
      msgs = basicSetNum(newNum, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__NUM, newNum, newNum));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public List getSet()
  {
    return set;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSet(List newSet, NotificationChain msgs)
  {
    List oldSet = set;
    set = newSet;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__SET, oldSet, newSet);
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
  public void setSet(List newSet)
  {
    if (newSet != set)
    {
      NotificationChain msgs = null;
      if (set != null)
        msgs = ((InternalEObject)set).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__SET, null, msgs);
      if (newSet != null)
        msgs = ((InternalEObject)newSet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__SET, null, msgs);
      msgs = basicSetSet(newSet, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__SET, newSet, newSet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getString()
  {
    return string;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setString(String newString)
  {
    String oldString = string;
    string = newString;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__STRING, oldString, string));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConceptDeclaration getConcept()
  {
    return concept;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConcept(ConceptDeclaration newConcept, NotificationChain msgs)
  {
    ConceptDeclaration oldConcept = concept;
    concept = newConcept;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__CONCEPT, oldConcept, newConcept);
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
  public void setConcept(ConceptDeclaration newConcept)
  {
    if (newConcept != concept)
    {
      NotificationChain msgs = null;
      if (concept != null)
        msgs = ((InternalEObject)concept).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__CONCEPT, null, msgs);
      if (newConcept != null)
        msgs = ((InternalEObject)newConcept).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__CONCEPT, null, msgs);
      msgs = basicSetConcept(newConcept, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__CONCEPT, newConcept, newConcept));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ConceptDeclaration> getToResolve()
  {
    if (toResolve == null)
    {
      toResolve = new EObjectContainmentEList<ConceptDeclaration>(ConceptDeclaration.class, this, KimPackage.CLASSIFIER_RHS__TO_RESOLVE);
    }
    return toResolve;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public REL_OPERATOR getOp()
  {
    return op;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetOp(REL_OPERATOR newOp, NotificationChain msgs)
  {
    REL_OPERATOR oldOp = op;
    op = newOp;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__OP, oldOp, newOp);
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
  public void setOp(REL_OPERATOR newOp)
  {
    if (newOp != op)
    {
      NotificationChain msgs = null;
      if (op != null)
        msgs = ((InternalEObject)op).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__OP, null, msgs);
      if (newOp != null)
        msgs = ((InternalEObject)newOp).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__OP, null, msgs);
      msgs = basicSetOp(newOp, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__OP, newOp, newOp));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public org.integratedmodelling.kim.kim.Number getExpression()
  {
    return expression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExpression(org.integratedmodelling.kim.kim.Number newExpression, NotificationChain msgs)
  {
    org.integratedmodelling.kim.kim.Number oldExpression = expression;
    expression = newExpression;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__EXPRESSION, oldExpression, newExpression);
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
  public void setExpression(org.integratedmodelling.kim.kim.Number newExpression)
  {
    if (newExpression != expression)
    {
      NotificationChain msgs = null;
      if (expression != null)
        msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__EXPRESSION, null, msgs);
      if (newExpression != null)
        msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__EXPRESSION, null, msgs);
      msgs = basicSetExpression(newExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__EXPRESSION, newExpression, newExpression));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getNodata()
  {
    return nodata;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNodata(String newNodata)
  {
    String oldNodata = nodata;
    nodata = newNodata;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__NODATA, oldNodata, nodata));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isStar()
  {
    return star;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStar(boolean newStar)
  {
    boolean oldStar = star;
    star = newStar;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__STAR, oldStar, star));
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
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__ID, oldId, id));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Quantity getQuantity()
  {
    return quantity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetQuantity(Quantity newQuantity, NotificationChain msgs)
  {
    Quantity oldQuantity = quantity;
    quantity = newQuantity;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__QUANTITY, oldQuantity, newQuantity);
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
  public void setQuantity(Quantity newQuantity)
  {
    if (newQuantity != quantity)
    {
      NotificationChain msgs = null;
      if (quantity != null)
        msgs = ((InternalEObject)quantity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__QUANTITY, null, msgs);
      if (newQuantity != null)
        msgs = ((InternalEObject)newQuantity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__QUANTITY, null, msgs);
      msgs = basicSetQuantity(newQuantity, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__QUANTITY, newQuantity, newQuantity));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Date getDate()
  {
    return date;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDate(Date newDate, NotificationChain msgs)
  {
    Date oldDate = date;
    date = newDate;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__DATE, oldDate, newDate);
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
  public void setDate(Date newDate)
  {
    if (newDate != date)
    {
      NotificationChain msgs = null;
      if (date != null)
        msgs = ((InternalEObject)date).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__DATE, null, msgs);
      if (newDate != null)
        msgs = ((InternalEObject)newDate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KimPackage.CLASSIFIER_RHS__DATE, null, msgs);
      msgs = basicSetDate(newDate, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__DATE, newDate, newDate));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getExpr()
  {
    return expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setExpr(String newExpr)
  {
    String oldExpr = expr;
    expr = newExpr;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__EXPR, oldExpr, expr));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isAnything()
  {
    return anything;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAnything(boolean newAnything)
  {
    boolean oldAnything = anything;
    anything = newAnything;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, KimPackage.CLASSIFIER_RHS__ANYTHING, oldAnything, anything));
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
      case KimPackage.CLASSIFIER_RHS__INT0:
        return basicSetInt0(null, msgs);
      case KimPackage.CLASSIFIER_RHS__INT1:
        return basicSetInt1(null, msgs);
      case KimPackage.CLASSIFIER_RHS__NUM:
        return basicSetNum(null, msgs);
      case KimPackage.CLASSIFIER_RHS__SET:
        return basicSetSet(null, msgs);
      case KimPackage.CLASSIFIER_RHS__CONCEPT:
        return basicSetConcept(null, msgs);
      case KimPackage.CLASSIFIER_RHS__TO_RESOLVE:
        return ((InternalEList<?>)getToResolve()).basicRemove(otherEnd, msgs);
      case KimPackage.CLASSIFIER_RHS__OP:
        return basicSetOp(null, msgs);
      case KimPackage.CLASSIFIER_RHS__EXPRESSION:
        return basicSetExpression(null, msgs);
      case KimPackage.CLASSIFIER_RHS__QUANTITY:
        return basicSetQuantity(null, msgs);
      case KimPackage.CLASSIFIER_RHS__DATE:
        return basicSetDate(null, msgs);
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
      case KimPackage.CLASSIFIER_RHS__BOOLEAN:
        return getBoolean();
      case KimPackage.CLASSIFIER_RHS__INT0:
        return getInt0();
      case KimPackage.CLASSIFIER_RHS__LEFT_LIMIT:
        return getLeftLimit();
      case KimPackage.CLASSIFIER_RHS__INT1:
        return getInt1();
      case KimPackage.CLASSIFIER_RHS__RIGHT_LIMIT:
        return getRightLimit();
      case KimPackage.CLASSIFIER_RHS__NUM:
        return getNum();
      case KimPackage.CLASSIFIER_RHS__SET:
        return getSet();
      case KimPackage.CLASSIFIER_RHS__STRING:
        return getString();
      case KimPackage.CLASSIFIER_RHS__CONCEPT:
        return getConcept();
      case KimPackage.CLASSIFIER_RHS__TO_RESOLVE:
        return getToResolve();
      case KimPackage.CLASSIFIER_RHS__OP:
        return getOp();
      case KimPackage.CLASSIFIER_RHS__EXPRESSION:
        return getExpression();
      case KimPackage.CLASSIFIER_RHS__NODATA:
        return getNodata();
      case KimPackage.CLASSIFIER_RHS__STAR:
        return isStar();
      case KimPackage.CLASSIFIER_RHS__ID:
        return getId();
      case KimPackage.CLASSIFIER_RHS__QUANTITY:
        return getQuantity();
      case KimPackage.CLASSIFIER_RHS__DATE:
        return getDate();
      case KimPackage.CLASSIFIER_RHS__EXPR:
        return getExpr();
      case KimPackage.CLASSIFIER_RHS__ANYTHING:
        return isAnything();
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
      case KimPackage.CLASSIFIER_RHS__BOOLEAN:
        setBoolean((String)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__INT0:
        setInt0((org.integratedmodelling.kim.kim.Number)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__LEFT_LIMIT:
        setLeftLimit((String)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__INT1:
        setInt1((org.integratedmodelling.kim.kim.Number)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__RIGHT_LIMIT:
        setRightLimit((String)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__NUM:
        setNum((org.integratedmodelling.kim.kim.Number)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__SET:
        setSet((List)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__STRING:
        setString((String)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__CONCEPT:
        setConcept((ConceptDeclaration)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__TO_RESOLVE:
        getToResolve().clear();
        getToResolve().addAll((Collection<? extends ConceptDeclaration>)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__OP:
        setOp((REL_OPERATOR)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__EXPRESSION:
        setExpression((org.integratedmodelling.kim.kim.Number)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__NODATA:
        setNodata((String)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__STAR:
        setStar((Boolean)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__ID:
        setId((String)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__QUANTITY:
        setQuantity((Quantity)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__DATE:
        setDate((Date)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__EXPR:
        setExpr((String)newValue);
        return;
      case KimPackage.CLASSIFIER_RHS__ANYTHING:
        setAnything((Boolean)newValue);
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
      case KimPackage.CLASSIFIER_RHS__BOOLEAN:
        setBoolean(BOOLEAN_EDEFAULT);
        return;
      case KimPackage.CLASSIFIER_RHS__INT0:
        setInt0((org.integratedmodelling.kim.kim.Number)null);
        return;
      case KimPackage.CLASSIFIER_RHS__LEFT_LIMIT:
        setLeftLimit(LEFT_LIMIT_EDEFAULT);
        return;
      case KimPackage.CLASSIFIER_RHS__INT1:
        setInt1((org.integratedmodelling.kim.kim.Number)null);
        return;
      case KimPackage.CLASSIFIER_RHS__RIGHT_LIMIT:
        setRightLimit(RIGHT_LIMIT_EDEFAULT);
        return;
      case KimPackage.CLASSIFIER_RHS__NUM:
        setNum((org.integratedmodelling.kim.kim.Number)null);
        return;
      case KimPackage.CLASSIFIER_RHS__SET:
        setSet((List)null);
        return;
      case KimPackage.CLASSIFIER_RHS__STRING:
        setString(STRING_EDEFAULT);
        return;
      case KimPackage.CLASSIFIER_RHS__CONCEPT:
        setConcept((ConceptDeclaration)null);
        return;
      case KimPackage.CLASSIFIER_RHS__TO_RESOLVE:
        getToResolve().clear();
        return;
      case KimPackage.CLASSIFIER_RHS__OP:
        setOp((REL_OPERATOR)null);
        return;
      case KimPackage.CLASSIFIER_RHS__EXPRESSION:
        setExpression((org.integratedmodelling.kim.kim.Number)null);
        return;
      case KimPackage.CLASSIFIER_RHS__NODATA:
        setNodata(NODATA_EDEFAULT);
        return;
      case KimPackage.CLASSIFIER_RHS__STAR:
        setStar(STAR_EDEFAULT);
        return;
      case KimPackage.CLASSIFIER_RHS__ID:
        setId(ID_EDEFAULT);
        return;
      case KimPackage.CLASSIFIER_RHS__QUANTITY:
        setQuantity((Quantity)null);
        return;
      case KimPackage.CLASSIFIER_RHS__DATE:
        setDate((Date)null);
        return;
      case KimPackage.CLASSIFIER_RHS__EXPR:
        setExpr(EXPR_EDEFAULT);
        return;
      case KimPackage.CLASSIFIER_RHS__ANYTHING:
        setAnything(ANYTHING_EDEFAULT);
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
      case KimPackage.CLASSIFIER_RHS__BOOLEAN:
        return BOOLEAN_EDEFAULT == null ? boolean_ != null : !BOOLEAN_EDEFAULT.equals(boolean_);
      case KimPackage.CLASSIFIER_RHS__INT0:
        return int0 != null;
      case KimPackage.CLASSIFIER_RHS__LEFT_LIMIT:
        return LEFT_LIMIT_EDEFAULT == null ? leftLimit != null : !LEFT_LIMIT_EDEFAULT.equals(leftLimit);
      case KimPackage.CLASSIFIER_RHS__INT1:
        return int1 != null;
      case KimPackage.CLASSIFIER_RHS__RIGHT_LIMIT:
        return RIGHT_LIMIT_EDEFAULT == null ? rightLimit != null : !RIGHT_LIMIT_EDEFAULT.equals(rightLimit);
      case KimPackage.CLASSIFIER_RHS__NUM:
        return num != null;
      case KimPackage.CLASSIFIER_RHS__SET:
        return set != null;
      case KimPackage.CLASSIFIER_RHS__STRING:
        return STRING_EDEFAULT == null ? string != null : !STRING_EDEFAULT.equals(string);
      case KimPackage.CLASSIFIER_RHS__CONCEPT:
        return concept != null;
      case KimPackage.CLASSIFIER_RHS__TO_RESOLVE:
        return toResolve != null && !toResolve.isEmpty();
      case KimPackage.CLASSIFIER_RHS__OP:
        return op != null;
      case KimPackage.CLASSIFIER_RHS__EXPRESSION:
        return expression != null;
      case KimPackage.CLASSIFIER_RHS__NODATA:
        return NODATA_EDEFAULT == null ? nodata != null : !NODATA_EDEFAULT.equals(nodata);
      case KimPackage.CLASSIFIER_RHS__STAR:
        return star != STAR_EDEFAULT;
      case KimPackage.CLASSIFIER_RHS__ID:
        return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
      case KimPackage.CLASSIFIER_RHS__QUANTITY:
        return quantity != null;
      case KimPackage.CLASSIFIER_RHS__DATE:
        return date != null;
      case KimPackage.CLASSIFIER_RHS__EXPR:
        return EXPR_EDEFAULT == null ? expr != null : !EXPR_EDEFAULT.equals(expr);
      case KimPackage.CLASSIFIER_RHS__ANYTHING:
        return anything != ANYTHING_EDEFAULT;
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
    result.append(" (boolean: ");
    result.append(boolean_);
    result.append(", leftLimit: ");
    result.append(leftLimit);
    result.append(", rightLimit: ");
    result.append(rightLimit);
    result.append(", string: ");
    result.append(string);
    result.append(", nodata: ");
    result.append(nodata);
    result.append(", star: ");
    result.append(star);
    result.append(", id: ");
    result.append(id);
    result.append(", expr: ");
    result.append(expr);
    result.append(", anything: ");
    result.append(anything);
    result.append(')');
    return result.toString();
  }

} //ClassifierRHSImpl
