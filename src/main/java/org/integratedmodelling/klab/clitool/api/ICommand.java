package org.integratedmodelling.klab.clitool.api;

import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.api.runtime.ISession;

public interface ICommand {

  /**
   * 
   * @param call
   * @param session
   * @return
   * @throws Exception 
   */
  public Object execute(IKimFunctionCall call, ISession session) throws Exception;

}
