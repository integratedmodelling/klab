package org.integratedmodelling.klab.api.runtime.dataflow;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IDataflowService;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Dataflows in k.LAB represent "raw" computations, which create, compute and link
 * {@link IObjectArtifact}s in response to a request for observation of a given semantic
 * {@link IResolvable}. The computation is stripped of all semantics, except for identifying the
 * identity of each built observation; therefore it can be run by a semantically-unaware workflow
 * system.
 * 
 * The end result of running a dataflow is a {@link IArtifact}. In k.LAB, this corresponds to either
 * a {@link IObservation} (the usual case) or a {@link IModel} (when the computation is a learning
 * activity, which builds an explanation of a process).
 * 
 * Dataflows written by users or created by k.LAB can be stored on k.LAB nodes as URN-specified
 * computations and referenced in k.LAB models. The KDL language that specified dataflows is also
 * used to define service contracts for k.IM-callable services or remote computations accessed
 * through REST calls.
 * 
 * A dataflow is the top-level {@link IActuator actuator} of a k.LAB computation. It adds top-level
 * semantics to the actuator's contract. Only a dataflow can be run and serialized from the API.
 * 
 * 
 * Dataflows are created from KDL specifications by {@link IDataflowService}. Dataflows are also
 * built by the engine after resolving a IResolvable, and can be serialized to KDL if necessary
 * using {@link #getKdlCode()}.
 * 
 * The KDL specification and the parser provided in the klab-kdl project provide a bridge to
 * different workflow systems. Models of computation are inferred in k.LAB and depend on the
 * specific {@link IRuntimeProvider runtime} adopted as well as on the semantics of the services
 * (actors) used; exposing the computational model is work in progress.
 * 
 * TODO expose all metadata and context fields.
 * 
 * @author ferdinando.villa
 * @param <T> the most specific type of artifact this dataflow will build when run.
 *
 */
public interface IDataflow<T extends IArtifact> extends IActuator {

  /**
   * The dataflow is the result of resolving a URN. If {@link ICoverage#isEmpty() its coverage is
   * empty}, the dataflow will produce an {@link IArtifact#isEmpty() empty artifact} when run.
   * Otherwise the coverage reflects the applicable scale of the dataflow, i.e. the range of extents
   * and resolutions where it applies.
   * 
   * @return the coverage of this dataflow.
   */
  ICoverage getCoverage();

  /**
   * Run the dataflow in the passed scale using the configured or default {@link IRuntimeProvider}
   * and return the resulting artifact.
   * 
   * @param scale the scale of contextualization. Assumed (and not checked) compatible with the
   *        scale of the resolution that generated this dataflow.
   * 
   *        TODO the scale should be checked against the coverage and the empty artifact should be
   *        returned if incompatible.
   * 
   * @param monitor
   * @return the built artifact. May be empty, never null.
   * @throws KlabException
   */
  T run(IScale scale, IMonitor monitor) throws KlabException;

  /**
   * Return the KDL source code for the dataflow. If the dataflow has been read from a KLD stream,
   * return the original code, otherwise reconstruct it by decompiling the dataflow.
   * 
   * @return the KDL code. Never null.
   */
  String getKdlCode();

}
