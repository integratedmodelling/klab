package org.integratedmodelling.klab.api.model;

import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimAction.Type;
import org.integratedmodelling.klab.api.resolution.IComputable;

/**
 * Action execution is deferred to dataflow through their {@link IComputable} identity.
 * 
 * @author ferdinando.villa
 *
 */
public interface IAction extends IComputable {

  /**
   * 
   * @return
   */
  Trigger getTrigger();

  /**
   * 
   * @return
   */
  Type getType();
  
}
