package org.integratedmodelling.klab.clitool.console.commands;

import java.io.File;
import java.net.URL;
import java.util.List;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.engine.Engine;

public class Run implements ICommand {

  @Override
  public Object execute(IServiceCall call, ISession session) throws Exception {

    for (Object resource : (List<?>) call.getParameters().get("arguments")) {
      URL url = null;
      if (resource.toString().contains("://")) {
        url = new URL(resource.toString());
      } else if (new File(resource.toString()).exists()) {
        url = new File(resource.toString()).toURI().toURL();
      }
      if (url != null) {
        Engine engine = session.getParent(Engine.class);
        session.getMonitor().info(url + " -> " + engine.run(url).get());
      }
    }
    return null;
  }


}
