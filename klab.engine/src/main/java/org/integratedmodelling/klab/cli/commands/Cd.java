package org.integratedmodelling.klab.cli.commands;

import java.io.File;
import java.util.List;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;

public class Cd implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        if (((List<?>) call.getParameters().get("arguments")).size() > 0) {
            String arg = ((List<?>) call.getParameters().get("arguments")).get(0).toString();
            File newDir = Klab.INSTANCE.resolveFile(arg);
            if (newDir != null && newDir.isDirectory()) {
                Klab.INSTANCE.setWorkDirectory(newDir);
                return "work directory set to " + Klab.INSTANCE.getWorkDirectory().getAbsolutePath();
            } else {
                return arg + " does not exist or is not a directory";
            }
        }
        return "current work directory is " + Klab.INSTANCE.getWorkDirectory().getAbsolutePath();
    }

}
