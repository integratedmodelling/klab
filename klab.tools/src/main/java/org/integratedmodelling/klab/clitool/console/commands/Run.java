package org.integratedmodelling.klab.clitool.console.commands;

import java.io.File;
import java.net.URL;
import java.util.List;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public class Run implements ICommand {

  @Override
  public Object execute(IServiceCall call, ISession session) throws Exception {

    for (Object resource : (List<?>) call.getParameters().get("arguments")) {
      URL url = null;
      if (resource.toString().contains("://")) {
        url = new URL(resource.toString());
      } else {
          File file = Klab.INSTANCE.resolveFile(resource.toString());
          if (file != null) {
              url = file.toURI().toURL();
          } else {
              throw new KlabIOException("file " + resource + " was not found");
          }
      }
      if (url != null) {
        Engine engine = session.getParentIdentity(Engine.class);
        session.getMonitor().info(url + " -> " + engine.run(url).get());
      }
    }
    return null;
  }


}
