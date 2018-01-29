package org.integratedmodelling.klab.engine.runtime;

import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.resolution.ResolutionScope;
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

  public ResolutionScope getRootScope() {
    return null;
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
