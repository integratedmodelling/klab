package org.integratedmodelling.klab.api.cli;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.runtime.ISession;

public interface ICommand {

  /**
   * 
   * @param call
   * @param session
   * @return the result of executing the command
   * @throws Exception 
   */
  public Object execute(IServiceCall call, ISession session);

}
