package org.integratedmodelling.klab.raster.files;

import java.net.URL;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The raster publisher will attempt WCS publishing if a WCS server is connected.
 * 
 * @author ferdinando.villa
 *
 */
public class RasterPublisher implements IResourcePublisher {

  @Override
  public IResource publish(URL url, IParameters userData, Builder builder) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

}
