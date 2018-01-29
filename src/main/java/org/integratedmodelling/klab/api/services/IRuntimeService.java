package org.integratedmodelling.klab.api.services;

/**
 * FIXME review API - this is exposing all the wrong things.
 * 
 * @author ferdinando.villa
 *
 */
public interface IRuntimeService {

  void info(Object... o);

  void warn(Object... o);

  void error(Object... o);

  void debug(Object... o);

}
