package org.integratedmodelling.klab.api.data.raw;

/**
 * 
 * @author Ferd
 *
 */
public interface IRawObject extends IRawObservation {

  IRawObject next();

  String getName();

  IRawObservation get(String name);
}
