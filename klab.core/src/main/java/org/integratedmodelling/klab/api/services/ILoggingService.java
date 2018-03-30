package org.integratedmodelling.klab.api.services;

public interface ILoggingService {
  
  void info(Object... o);

  void warn(Object... o);

  void error(Object... o);

  void debug(Object... o);
}
