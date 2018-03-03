package org.integratedmodelling.klab.api.data.raw;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;

/**
 * 
 * @author Ferd
 *
 */
public interface IRawObservation {

  /**
   * Semantics is passed down to contextualizers to support any reasoning they want to do, but is
   * not essential to the contract of a "raw" observation, which is by definition stripped of all
   * semantics except for reporting it to applications that wish to know.
   * 
   * @return the semantics for the observation. Never null in the k.LAB engine runtime, but possibly
   *         null if this API is used outside of it.
   */
  IObservable getSemantics();

  /**
   * The geometry linked to the observation. In states, this is supplemented by the corresponding
   * contextualized datashape.
   * 
   * @return the geometry
   */
  IGeometry getGeometry();

  /**
   * Metadata. Never null, possibly empty. They are passed to the semantic artifact and may
   * be used for visualization, provenance etc.
   * 
   * @return the metadata
   */
  IMetadata getMetadata();

}
