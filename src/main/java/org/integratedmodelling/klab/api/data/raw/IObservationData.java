package org.integratedmodelling.klab.api.data.raw;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IObservation;

/**
 * The generic API for the data behind any observational artifacts. These objects are produced by
 * contextualizers called through service calls. The objects implement Iterator in order to also
 * represent groups of objects when appropriate.
 * 
 * IObservationData objects are received by the dataflow runtime and used to define
 * {@link IObservation informational artifacts}.
 * 
 * @author Ferd
 *
 */
public interface IObservationData {

  /**
   * The geometry linked to the observation. Observational artifacts will specialize this as IScale.
   * 
   * @return the geometry
   */
  IGeometry getGeometry();

  /**
   * Metadata. Never null, possibly empty. 
   * 
   * @return the metadata
   */
  IMetadata getMetadata();
  
  /**
   * Objects are arranged in a tree where only the top object has no parent.
   * 
   * @return the parent, or null.
   */
  IObjectData getParent();

}
