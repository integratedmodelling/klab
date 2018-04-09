package org.integratedmodelling.klab.api.data.adapters;

import java.net.URL;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IResourcePublisher {

  /**
   * Publish a resource that was previously validated returning a Builder with no errors. If no
   * errors happen during publishing, produce a final IResource with a valid URN, ready for storage
   * in the resource catalog.
   * 
   * @param url
   * @param userData
   * @param builder
   * @return
   * @throws KlabException
   */
  public IResource publish(URL url, IParameters userData, Builder builder) throws KlabException;

}
