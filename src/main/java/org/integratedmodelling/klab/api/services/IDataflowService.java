package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import org.integratedmodelling.kdl.api.IKdlDataflow;
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
   * @param scope
   * @param cls
   * @return a dataflow that will compute an artifact of the requested type when run.
   * @throws KlabException
   */
  <T extends IArtifact> IDataflow<T> compile(IResolutionScope scope, Class<T> cls)
      throws KlabException;

}
