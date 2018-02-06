package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IProvenance.Artifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The resolver for a {@link IState}.
 * 
 * The resolver is also called in to create the state early in the computation, just after
 * {@link #setRuntimeContext(IActuator, Artifact, IMonitor)} is called. Default abstract
 * implementations should be provided that implement the {@link #createObservation(IScale)}
 * function.
 * 
 * @author ferdinando.villa
 *
 */
public interface IStateResolver extends IResolver<IState> {

  IState createObservation(IScale scale);

}
