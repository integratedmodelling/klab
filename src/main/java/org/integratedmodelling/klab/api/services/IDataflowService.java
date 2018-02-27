package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public interface IDataflowService {

  IKdlDataflow declare(InputStream file) throws KlabValidationException;

  IKdlDataflow declare(File file) throws KlabException;

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
  List<Trigger> getActionTriggersFor(ITransition transition);

}
