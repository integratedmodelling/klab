package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Services related to dataflows and the KDL language.
 * 
 * @author ferdinando.villa
 *
 */
public interface IDataflowService {

  /**
   * Read and return the dataflow specifications corresponding to the passed
   * input, which is expected to contain valid KDL.
   * 
   * @param input
   * @return the parsed dataflow.
   * @throws KlabException
   */
  IKdlDataflow declare(InputStream input) throws KlabException;

  /**
   * Read and return the dataflow specifications corresponding to the passed
   * input file, which is expected to contain valid KDL.
   * 
   * @param file
   * @return the parsed dataflow.
   * @throws KlabException
   */
  IKdlDataflow declare(File file) throws KlabException;

  /**
   * Read and return the dataflow specifications corresponding to the passed
   * input URL, which is expected to contain valid KDL.
   * 
   * @param url
   * @return the parsed dataflow.
   * @throws KlabException
   */
  IKdlDataflow declare(URL url) throws KlabException;

  /**
   * Compile a resolution scope into a dataflow computing the passed artifact type.
   * 
   * @param name
   * @param scope
   * @param cls
   * @return a dataflow that will compute an artifact of the requested type when run.
   * @throws KlabException
   */
  <T extends IArtifact> IDataflow<T> compile(String name, IResolutionScope scope, Class<T> cls)
      throws KlabException;

  /**
   * Given a transition, return all the action triggers that pertain to it. If more than one trigger
   * is returned, any actions corresponding to the first will be applied before, and the second
   * after, the transition event: e.g. definition vs. resolution or (last) transition vs.
   * termination.
   * 
   * @param transition
   * @return all pertaining triggers. Possibly empty, never null.
   */
  List<Trigger> getActionTriggersFor(ILocator transition);

}
