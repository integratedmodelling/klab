package org.integratedmodelling.klab.raster;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;

@ResourceAdapter(type = "vector", version = Version.CURRENT, requires = {"fileUrl"})
public class VectorAdapter implements IResourceAdapter {

  @Override
  public IResourceValidator getValidator() {
    return null;
  }

  @Override
  public IResourcePublisher getPublisher() {
    return null;
  }

  @Override
  public IResourceEncoder getEncoder() {
    return null;
  }

}
