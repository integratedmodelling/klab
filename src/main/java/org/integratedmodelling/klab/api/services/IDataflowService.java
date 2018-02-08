package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.resolution.ResolutionScope;

public interface IDataflowService {

  IKdlDataflow declare(InputStream file) throws KlabValidationException;

  IKdlDataflow declare(File file) throws KlabException;

  IKdlDataflow declare(URL url) throws KlabException;

  IDataflow compile(ResolutionScope scope) throws KlabException;

}
