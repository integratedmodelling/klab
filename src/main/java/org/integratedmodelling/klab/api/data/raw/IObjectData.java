package org.integratedmodelling.klab.api.data.raw;

import java.util.Collection;

/**
 * 
 * @author Ferd
 *
 */
public interface IObjectData extends IObservationData {

  /**
   * Objects have names.
   * 
   * @return the object's name
   */
  String getName();

  /**
   * Objects may own other objects. The ownership is one of the three structures in
   * a context: 1) artifact grouping (provenance); 2) ownership structure (always a
   * tree), and 3) relationship/network structure.
   * 
   * @return all the directly owned objects
   */
  Collection<IObservationData> getChildren();

}
