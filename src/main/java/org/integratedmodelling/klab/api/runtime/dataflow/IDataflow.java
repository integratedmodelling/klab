package org.integratedmodelling.klab.api.runtime.dataflow;

import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IDataflowService;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * A dataflow is the top-level actuator of a computation. Allows adding top-level semantics and
 * constraints for the computation.
 * 
 * The end result of running a dataflow is a {@link IArtifact}.
 * 
 * Dataflows are created from KDL specifications by {@link IDataflowService}, or they can be built
 * piece by piece using a {@link Builder} (and serialized to KDL if necessary using
 * {@link #getKdlCode()}).
 * 
 * @author ferdinando.villa
 * @param <T> the type of artifact this dataflow will build when run.
 *
 */
public interface IDataflow<T extends IArtifact> extends IActuator {

  public interface Builder {

    /**
     * Build the dataflow. The class requested must match the semantics of the root actuator.
     * Defines the type of the dataflow based on the class.
     *
     * @param monitor
     * @return the dataflow specified through the builder.
     * @throws IllegalArgumentException if the class does not match the requested semantics.
     */
    IDataflow<?> build(IMonitor monitor);

    /**
     * Notify the coverage resulting from resolution
     * 
     * @param coverage
     * @return this builder
     */
    Builder withCoverage(double coverage);

    /**
     * Pass any direct observation that serves as context for this computation.
     * 
     * @param context
     * @return this builder
     */
    Builder within(IDirectObservation context);

    /**
     * Notify a dependency between two resolvables, possibly with a partial coverage
     * 
     * @param source the resolvable that provides the resolution
     * @param target the resolvable that is resolved by source
     * @param coverage the coverage handled by this dependency
     * @return this builder
     */
    Builder withDependency(IResolvable source, IResolvable target, ICoverage coverage);

    /**
     * Add a single resolvable. Normally used to notify a {@link IObserver}.
     * 
     * @param resolvable
     * @return this builder
     */
    Builder withResolvable(IResolvable resolvable);

  }

  /**
   * Run the dataflow and return the resulting artifact.
   * 
   * @param monitor
   * @return the built artifact. May be empty, never null.
   * @throws KlabException
   */
  public T run(IMonitor monitor) throws KlabException;

  public String getKdlCode();

}
