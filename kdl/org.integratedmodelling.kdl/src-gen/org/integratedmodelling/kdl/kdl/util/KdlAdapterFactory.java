/**
 * generated by Xtext 2.36.0
 */
package org.integratedmodelling.kdl.kdl.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.integratedmodelling.kdl.kdl.ActorDefinition;
import org.integratedmodelling.kdl.kdl.Annotation;
import org.integratedmodelling.kdl.kdl.ClassifierRHS;
import org.integratedmodelling.kdl.kdl.Computation;
import org.integratedmodelling.kdl.kdl.Currency;
import org.integratedmodelling.kdl.kdl.DataflowBody;
import org.integratedmodelling.kdl.kdl.Define;
import org.integratedmodelling.kdl.kdl.Function;
import org.integratedmodelling.kdl.kdl.KdlPackage;
import org.integratedmodelling.kdl.kdl.KeyValuePair;
import org.integratedmodelling.kdl.kdl.List;
import org.integratedmodelling.kdl.kdl.Literal;
import org.integratedmodelling.kdl.kdl.LookupTable;
import org.integratedmodelling.kdl.kdl.Map;
import org.integratedmodelling.kdl.kdl.MapEntry;
import org.integratedmodelling.kdl.kdl.Metadata;
import org.integratedmodelling.kdl.kdl.Model;
import org.integratedmodelling.kdl.kdl.Parameter;
import org.integratedmodelling.kdl.kdl.ParameterList;
import org.integratedmodelling.kdl.kdl.REL_OPERATOR;
import org.integratedmodelling.kdl.kdl.Table;
import org.integratedmodelling.kdl.kdl.TableRow;
import org.integratedmodelling.kdl.kdl.Unit;
import org.integratedmodelling.kdl.kdl.UnitElement;
import org.integratedmodelling.kdl.kdl.Urn;
import org.integratedmodelling.kdl.kdl.Value;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.integratedmodelling.kdl.kdl.KdlPackage
 * @generated
 */
public class KdlAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static KdlPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public KdlAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = KdlPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected KdlSwitch<Adapter> modelSwitch =
    new KdlSwitch<Adapter>()
    {
      @Override
      public Adapter caseModel(Model object)
      {
        return createModelAdapter();
      }
      @Override
      public Adapter caseAnnotation(Annotation object)
      {
        return createAnnotationAdapter();
      }
      @Override
      public Adapter caseActorDefinition(ActorDefinition object)
      {
        return createActorDefinitionAdapter();
      }
      @Override
      public Adapter caseDataflowBody(DataflowBody object)
      {
        return createDataflowBodyAdapter();
      }
      @Override
      public Adapter caseComputation(Computation object)
      {
        return createComputationAdapter();
      }
      @Override
      public Adapter caseParameter(Parameter object)
      {
        return createParameterAdapter();
      }
      @Override
      public Adapter caseUnitElement(UnitElement object)
      {
        return createUnitElementAdapter();
      }
      @Override
      public Adapter caseUnit(Unit object)
      {
        return createUnitAdapter();
      }
      @Override
      public Adapter caseCurrency(Currency object)
      {
        return createCurrencyAdapter();
      }
      @Override
      public Adapter caseClassifierRHS(ClassifierRHS object)
      {
        return createClassifierRHSAdapter();
      }
      @Override
      public Adapter caseList(List object)
      {
        return createListAdapter();
      }
      @Override
      public Adapter caseLiteral(Literal object)
      {
        return createLiteralAdapter();
      }
      @Override
      public Adapter caseMetadata(Metadata object)
      {
        return createMetadataAdapter();
      }
      @Override
      public Adapter caseParameterList(ParameterList object)
      {
        return createParameterListAdapter();
      }
      @Override
      public Adapter caseValue(Value object)
      {
        return createValueAdapter();
      }
      @Override
      public Adapter caseDefine(Define object)
      {
        return createDefineAdapter();
      }
      @Override
      public Adapter caseUrn(Urn object)
      {
        return createUrnAdapter();
      }
      @Override
      public Adapter caseMap(Map object)
      {
        return createMapAdapter();
      }
      @Override
      public Adapter caseMapEntry(MapEntry object)
      {
        return createMapEntryAdapter();
      }
      @Override
      public Adapter caseLookupTable(LookupTable object)
      {
        return createLookupTableAdapter();
      }
      @Override
      public Adapter caseTable(Table object)
      {
        return createTableAdapter();
      }
      @Override
      public Adapter caseTableRow(TableRow object)
      {
        return createTableRowAdapter();
      }
      @Override
      public Adapter caseKeyValuePair(KeyValuePair object)
      {
        return createKeyValuePairAdapter();
      }
      @Override
      public Adapter caseFunction(Function object)
      {
        return createFunctionAdapter();
      }
      @Override
      public Adapter caseREL_OPERATOR(REL_OPERATOR object)
      {
        return createREL_OPERATORAdapter();
      }
      @Override
      public Adapter caseNumber(org.integratedmodelling.kdl.kdl.Number object)
      {
        return createNumberAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Model
   * @generated
   */
  public Adapter createModelAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Annotation <em>Annotation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Annotation
   * @generated
   */
  public Adapter createAnnotationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.ActorDefinition <em>Actor Definition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.ActorDefinition
   * @generated
   */
  public Adapter createActorDefinitionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.DataflowBody <em>Dataflow Body</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.DataflowBody
   * @generated
   */
  public Adapter createDataflowBodyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Computation <em>Computation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Computation
   * @generated
   */
  public Adapter createComputationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Parameter
   * @generated
   */
  public Adapter createParameterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.UnitElement <em>Unit Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.UnitElement
   * @generated
   */
  public Adapter createUnitElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Unit <em>Unit</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Unit
   * @generated
   */
  public Adapter createUnitAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Currency <em>Currency</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Currency
   * @generated
   */
  public Adapter createCurrencyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.ClassifierRHS <em>Classifier RHS</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.ClassifierRHS
   * @generated
   */
  public Adapter createClassifierRHSAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.List <em>List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.List
   * @generated
   */
  public Adapter createListAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Literal <em>Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Literal
   * @generated
   */
  public Adapter createLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Metadata <em>Metadata</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Metadata
   * @generated
   */
  public Adapter createMetadataAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.ParameterList <em>Parameter List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.ParameterList
   * @generated
   */
  public Adapter createParameterListAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Value <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Value
   * @generated
   */
  public Adapter createValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Define <em>Define</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Define
   * @generated
   */
  public Adapter createDefineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Urn <em>Urn</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Urn
   * @generated
   */
  public Adapter createUrnAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Map <em>Map</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Map
   * @generated
   */
  public Adapter createMapAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.MapEntry <em>Map Entry</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.MapEntry
   * @generated
   */
  public Adapter createMapEntryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.LookupTable <em>Lookup Table</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.LookupTable
   * @generated
   */
  public Adapter createLookupTableAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Table <em>Table</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Table
   * @generated
   */
  public Adapter createTableAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.TableRow <em>Table Row</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.TableRow
   * @generated
   */
  public Adapter createTableRowAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.KeyValuePair <em>Key Value Pair</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.KeyValuePair
   * @generated
   */
  public Adapter createKeyValuePairAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Function <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Function
   * @generated
   */
  public Adapter createFunctionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.REL_OPERATOR <em>REL OPERATOR</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.REL_OPERATOR
   * @generated
   */
  public Adapter createREL_OPERATORAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.integratedmodelling.kdl.kdl.Number <em>Number</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.integratedmodelling.kdl.kdl.Number
   * @generated
   */
  public Adapter createNumberAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //KdlAdapterFactory
