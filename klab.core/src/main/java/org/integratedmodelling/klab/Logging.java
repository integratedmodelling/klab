package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.ILoggingService;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public enum Logging implements ILoggingService {

  INSTANCE;
  
  private Logger                                              logger;


  private Logging() {
  
      logger = (Logger) LoggerFactory.getLogger(this.getClass());
      if (logger != null) {
          /*
           * I'd rather not do this, but 100M of debug output when nobody has ever asked for it and no
           * logging property files are around to be changed is a bit much to take.
           */
          logger.setLevel(Level.INFO);
      }
  }
  
  @Override
  public void info(Object... o) {
      if (logger != null) {
          logger.info(NotificationUtils.getMessage(o));
      } else {
          System.err.println("INFO: " + NotificationUtils.getMessage(o));
      }
  }

  @Override
  public void warn(Object... o) {
      if (logger != null) {
          logger.warn(NotificationUtils.getMessage(o));
      } else {
          System.err.println("WARN: " + NotificationUtils.getMessage(o));
      }
  }

  @Override
  public void error(Object... o) {
      if (logger != null) {
          logger.error(NotificationUtils.getMessage(o));
      } else {
          System.err.println("WARN: " + NotificationUtils.getMessage(o));
      }
  }

  @Override
  public void debug(Object... o) {
      if (logger != null) {
          logger.debug(NotificationUtils.getMessage(o));
      } else {
          System.err.println("WARN: " + NotificationUtils.getMessage(o));
      }
  }

  
}
