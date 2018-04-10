package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IResourcePublisher {

  /**
   * Publish a local resource, which must have no errors. If no errors happen during publishing,
   * produce a new public IResource with a valid URN, ready for storage in the public resource
   * catalog.
   * 
   * @param localResource
   * @return a new resource. If errors happen, throw an exception; if the function returns, the
   *         resource must be valid.
   * @throws KlabException if the passed resource is not local, has errors, or anything happens
   *         during publication.
   */
  public IResource publish(IResource localResource) throws KlabException;

}
