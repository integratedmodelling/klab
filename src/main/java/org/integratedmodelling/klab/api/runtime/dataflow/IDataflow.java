package org.integratedmodelling.klab.api.runtime.dataflow;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IDataflowService;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * A dataflow is the top-level actuator of a computation. Allows adding top-level semantics and
 * constraints for the computation.
 * 
 * The end result of running a dataflow is a {@link IArtifact}.
 * 
 * Dataflows are created from KDL specifications by {@link IDataflowService}. Dataflows are also
 * built by the engine after resolving a IResolvable, and can be serialized to KDL if necessary
 * using {@link #getKdlCode()}.
 * 
 * TODO expose the various metadata/context fields.
 * 
 * @author ferdinando.villa
 * @param <T> the type of artifact this dataflow will build when run.
 *
 */
public interface IDataflow<T extends IArtifact> extends IActuator {

  /**
   * Run the dataflow using the configured or default {@link IRuntimeProvider} and return the
   * resulting artifact.
   * 
   * @param monitor
   * @return the built artifact. May be empty, never null.
   * @throws KlabException
   */
  public T run(IMonitor monitor) throws KlabException;

  public String getKdlCode();

}
