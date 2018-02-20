package org.integratedmodelling.klab.api.runtime.dataflow;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
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
     * @param name
     * @param cls
     * @return the dataflow specified through the builder.
     * @throws IllegalArgumentException if the class does not match the requested semantics.
     */
    IActuator build();

    /**
     * Pass the observable we want this dataflow to automatically instantiate. To be used when the
     * contextualizer is a resolver.
     * 
     * @param observable
     * @param namespace the namespace of resolution
     * @return this builder
     */
    Builder instantiating(IObservable observable, INamespace namespace);

    /**
     * Pass the observable we want this dataflow to automatically instantiate. To be used when the
     * contextualizer is a resolver.
     * 
     * @param observable
     * @return this builder
     */
    Builder instantiating(IObservable observable);

    /**
     * Define the scale for this dataflow.
     * 
     * @param scale
     * @return this builder
     */
    Builder withScale(IScale scale);

    /**
     * Notify the coverage resulting from resolution
     * 
     * @param coverage
     * @return this builder
     */
    Builder withCoverage(double coverage);

    /**
     * Add a child actuator with the passed name and type. Use the returned builder to complete it.
     * Do not call build() on the returned builder: this will be done recursively when the root one
     * is built.
     * 
     * @param actuatorName
     * @param type
     * @return a builder for the <i>child</i> actuator.
     */
    Builder add(String actuatorName, Class<? extends IArtifact> type);

    /**
     * Pass any direct observation that serves as context for this computation.
     * 
     * @param context
     * @return
     */
    Builder within(IDirectObservation context);

    /**
     * Notify a dependency between two resolvables, possibly with a partial coverage
     * 
     * @param source the resolvable that provides the resolution
     * @param target the resolvable that is resolved by source
     * @param coverage the coverage handled by this dependency
     */
    void addDependency(IResolvable source, IResolvable target, ICoverage coverage);

//
//    /**
//     * Add a child builder.
//     * 
//     * @param child
//     * @return
//     */
//    Builder add(Builder child);
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
