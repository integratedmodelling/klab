package org.integratedmodelling.klab.api.data.raw;

/**
 * 
 * @author Ferd
 *
 */
public interface IObjectData extends IObservationData {

  String getName();

  IObservationData get(String name);

}
