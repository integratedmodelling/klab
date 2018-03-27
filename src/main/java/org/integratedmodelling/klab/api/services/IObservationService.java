package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Manage observations stored locally (as IObserver specifications) and provide an API to query and
 * retrieve those stored on the k.LAB network.
 * 
 * @author ferdinando.villa
 *
 */
public interface IObservationService {

  /**
   * Resolve the URN for a top-level resolvable to the computation that will produce the
   * corresponding observation when run. Unsuccessful resolution is indicated by a dataflow with
   * empty coverage, which will produce an empty observation.
   * <p>
   * The {@link ISession#observe(String, String...)} method calls this function and runs the
   * dataflow in a {@link ITask}.
   * <p>
   * 
   * @param urn the identifier for a top-level observation (describing a IObserver or a remote
   *        context).
   * @param session a valid engine session
   * @param scenarios zero or more scenario IDs to affect the resolution
   * @return the computation to observe the URN.
   */
  IDataflow<IObservation> resolve(String urn, ISession session, String[] scenarios);

  /**
   * Resolve the passed URN to to the computation that will produce the corresponding observation in
   * the context of the passed one. Unsuccessful resolution is indicated by a dataflow with empty
   * coverage, which will produce an empty observation.
   * <p>
   * The resolution is done in the {@link ISession} that owns the passed observation.
   * <p>
   * The {@link ISubject#observe(String, String...)} method calls this function and runs the
   * dataflow in a {@link ITask}.
   * <p>
   * 
   * @param urn
   * @param context
   * @param scenarios zero or more scenario IDs to affect the resolution
   * @return the computation to observe the URN in the passed context.
   */
  IDataflow<IObservation> resolve(String urn, IDirectObservation context, String[] scenarios);

  /**
   * Release all information pertaining to named namespace, both in live and persistent storage.
   * 
   * @param namespace
   * @param monitor
   * @throws KlabException
   */
  void releaseNamespace(INamespace namespace, IMonitor monitor) throws KlabException;

  /**
   * Index passed observation definition for retrieval.
   * 
   * @param observer
   * @param monitor
   * @throws KlabException
   */
  void index(IObserver observer, IMonitor monitor) throws KlabException;


}
