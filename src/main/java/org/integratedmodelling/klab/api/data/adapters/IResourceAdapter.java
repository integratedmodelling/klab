package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Shutdown;

/**
 * A {@code IResourceAdapter} is the interface for a plug-in providing a new adapter for a resource
 * type. A class implementing {@code IResourceAdapter} must be annotated with a
 * {@link ResourceAdapter} annotation in order to be discovered by the runtime.
 * 
 * The implementing class may specify initialization and finalization methods by annotating them
 * with the {@link Initialize} and {@link Shutdown} annotations also used for components.
 * 
 * @author Ferd
 *
 */
public interface IResourceAdapter {

  /**
   * Produce a new instance of the {@link IResourceValidator} for this resource type.
   *
   * @return
   */
  IResourceValidator getValidator();

  /**
   * Produce a new instance of the {@link IResourcePublisher} for this resource type.
   * 
   * @return
   */
  IResourcePublisher getPublisher();

  /**
   * Produce a new instance of the {@link IResourceEncoder} for this resource type.
   * 
   * @return
   */
  IResourceEncoder getEncoder();

  /**
   * Prototype for the required and optional metadata that this adapter will require or accept.
   * Implementations should be able to provide KDL specs for this.
   * 
   * @return a prototype for the metadata, with the same name as the adapter.
   */
  IPrototype metadataPrototype();
}
