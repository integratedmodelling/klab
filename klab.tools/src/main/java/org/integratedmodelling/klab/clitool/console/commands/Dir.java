package org.integratedmodelling.klab.clitool.console.commands;

import java.io.File;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;

public class Dir implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws Exception {
    	boolean recursive = call.getParameters().get("recursive", false);
    	return listDirectory(Klab.INSTANCE.getWorkDirectory(), "", recursive);
    }

	private String listDirectory(File workDirectory, String prefix, boolean recursive) {
		String ret = "";
		for (File f : workDirectory.listFiles()) {
			ret += prefix + f + "\n";
			if (recursive && f.isDirectory()) {
				ret += listDirectory(f, "   ", recursive);
			}
		}
		return ret;
	}

}
