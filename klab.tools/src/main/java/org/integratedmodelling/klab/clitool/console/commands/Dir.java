package org.integratedmodelling.klab.clitool.console.commands;

import java.io.File;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class Dir implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {
        boolean recursive = call.getParameters().get("recursive", false);
        return listDirectory(Klab.INSTANCE.getWorkDirectory(), "", recursive);
    }

    private String listDirectory(File workDirectory, String prefix, boolean recursive) {
        String ret = "";
        for (File f : workDirectory.listFiles()) {
            ret += prefix + fixFilename(f) + "\n";
            if (recursive && f.isDirectory()) {
                ret += listDirectory(f, "   ", recursive);
            }
        }
        return ret;
    }

    private String fixFilename(File f) {
        String ret = MiscUtilities.getFileName(f.toString());
        if (f.isDirectory()) {
            ret += File.separator;
        }
        return ret;
    }

}
