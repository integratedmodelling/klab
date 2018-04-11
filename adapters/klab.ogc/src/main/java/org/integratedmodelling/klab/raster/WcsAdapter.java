package org.integratedmodelling.klab.raster;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.raster.wcs.WcsEncoder;
import org.integratedmodelling.klab.raster.wcs.WcsPublisher;
import org.integratedmodelling.klab.raster.wcs.WcsValidator;

@ResourceAdapter(type = "wcs", version = Version.CURRENT, requires = {"serviceUrl", "wcsVersion"},
    optional = {"namespace"})
public class WcsAdapter implements IResourceAdapter {

  @Override
  public IResourceValidator getValidator() {
    return new WcsValidator();
  }

  @Override
  public IResourcePublisher getPublisher() {
    return new WcsPublisher();
  }

  @Override
  public IResourceEncoder getEncoder() {
    return new WcsEncoder();
  }


}
