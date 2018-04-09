package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.util.List;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;

public interface IResourceService {

  /**
   * Resolve the passed URN to a resource.
   * 
   * @param urn the
   * @return a resource
   * @throws KlabUnknownUrnException
   * @throws KlabUnauthorizedUrnException
   */
  IResource getResource(String urn) throws KlabUnknownUrnException, KlabUnauthorizedUrnException;

  /**
   * 
   * @param file
   * @return
   */
  IResource getLocalFileResource(File file);

  /**
   * Retrieve a model object identified through a URN - either an observer or a model, local or
   * remote, in the latter case triggering any necessary synchronization with the network.
   * 
   * @param urn
   * @return the model object corresponding to the urn, or null if not found.
   */
  IKimObject getModelObject(String urn);

  /**
   * Retrieve a resolvable object identified by a URN, promoting any resource that is not directly
   * resolvable to the correspondent resolvable when possible.
   * 
   * @param urn either a formal URN or one of the abbreviated forms recognized in k.IM (such as a
   *        concept identifier)
   * @return a resolvable resource, or null if nothing can be found.
   */
  IResolvable getResolvableResource(String urn);

  /**
   * Get the appropriate resource adapters to handle a local file resource. Use the best inspection
   * method possible that does not cost too much. Returning more than one adapter should normally
   * not happen. If applicable and possible, sort the list so that the most appropriate or best
   * performing is the first.
   * 
   * @param resource a local resource file
   * @return all resource adapters that can handle the passed file, best one on top.
   */
  List<IResourceAdapter> getResourceAdapter(File resource);

  /**
   * Get a previously registered resource adapter to handle the passed resource type.
   * 
   * @param id the adapter type ID
   * @return a resource adapter, or null if none was registered with this id.
   */
  IResourceAdapter getResourceAdapter(String id);

  /**
   * Create a builder to describe a future valid resource or the errors that will prevent it
   * from being published.
   * 
   * @return
   */
  IResource.Builder createBuilder();
  
}
