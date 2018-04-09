package org.integratedmodelling.kim.validation;

import java.io.Serializable;
import java.util.logging.Level;
import org.integratedmodelling.kim.api.INotification;

/**
 * Trivial bean for notifications, so these can be sent outside of the validator and processed in
 * it.
 * 
 * @author ferdinando.villa
 *
 */
public class KimNotification implements INotification, Serializable {

  private static final long serialVersionUID = -5812547783872203517L;

  String                    message;
  Level                     level;
  long                      timestamp        = System.currentTimeMillis();

  public KimNotification(String message, Level level) {
    this.message = message;
    this.level = level;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Level getLevel() {
    return level;
  }

  public void setLevel(Level level) {
    this.level = level;
  }

  @Override
  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public Type getType() {
    return null;
  }

}
