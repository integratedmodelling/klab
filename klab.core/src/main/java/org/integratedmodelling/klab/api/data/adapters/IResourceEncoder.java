package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public interface IResourceEncoder {
  
  /**
   * 
   * @param resource
   * @param geometry
   * @param monitor for notifications and identity retrieval
   * @return
   */
  IKlabData getEncodedData(IResource resource, IGeometry geometry, IMonitor monitor);
}
