package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

/**
 * A contextualizer builds the observation of an observable in a context based on what the
 * provenance model implies. There are two types of contextualizers: those that
 * <strong>explain</strong> a single instance of an observation (<strong>resolvers</strong>) and
 * those that <strong>instantiate</strong> zero or more observations (to be explained by other
 * contextualizers). These have additional methods and correspond to {@link IResolver} and
 * {@link IInstantiator} respectively.
 * 
 * Contextualizers can be contributed by components and are managed by {@link IActuator}s during the
 * execution of {@link IDataflow}s. In KDL dataflow specifications, contextualizers are called in
 * <code> compute </code> statements.
 * 
 * In a workflow engine such as Ptolemy, contextualizers represent context-aware actors specified by
 * {@link IServiceCall}s.
 * 
 * To provide a new contextualizer, extend one of the non-abstract child interfaces and provide the
 * specifications of its k.LAB identity using a KDL file specifying a component definition.
 * 
 * @author ferdinando.villa
 *
 */
public abstract interface IContextualizer {

  /**
   * Contextualizers can be linked to a geometry, which is set in the corresponding function
   * prototype. The stated geometry defines whether the contextualizer is interrogated in time and
   * whether it is applicable to a specific type of scale. Geometries along a computational chain
   * must be compatible and propagate to the models, so they can be used during resolution to choose
   * or rank models for the scale of contextualization.
   * 
   * @return the geometry. A contextualizer that reports a null geometry is a mediator and must be
   *         able to take any input geometry for its type.
   */
  IGeometry getGeometry();

}
