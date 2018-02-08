package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.engine.runtime.ConfigurationDetector;
import org.integratedmodelling.klab.observation.Subject;
import com.google.common.eventbus.EventBus;

/**
 * A runtime context is installed in the root subject to keep track of what happens
 * during contextualization.
 * 
 * TODO Agent graphs, schedules etc should be here.
 * 
 * @author ferdinando.villa
 *
 */
public class RuntimeContext {

  IProvenance provenance;
  EventBus eventBus;
  ConfigurationDetector configurationDetector;
  
  public RuntimeContext(Subject subject) {
    
  }
  
  public IProvenance getProvenance() {
    return null;
  }
  
  public EventBus getEventBus() {
    return null;
  }
  
  public ConfigurationDetector getConfigurationDetector() {
    return null;
  }

}
