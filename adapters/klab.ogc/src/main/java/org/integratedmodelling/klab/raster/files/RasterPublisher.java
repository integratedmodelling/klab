package org.integratedmodelling.klab.raster.files;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The raster publisher will attempt WCS publishing if a WCS server is connected.
 * 
 * @author ferdinando.villa
 *
 */
public class RasterPublisher implements IResourcePublisher {

  @Override
  public IResource publish(IResource localResource, IMonitor monitor) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }


}
