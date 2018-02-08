package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.Artifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The resolver for a {@link IProcess}.
 * 
 * The resolver is also called in to create the observation early in the computation, just after
 * {@link #setRuntimeContext(IActuator, Artifact, IMonitor)} is called. Default abstract
 * implementations should be provided that implement the {@link #createObservation(IScale)}
 * function.
 * 
 * @author ferdinando.villa
 *
 */
public interface IProcessResolver extends IResolver<IProcess> {

  IProcess createObservation(IScale scale);

}
