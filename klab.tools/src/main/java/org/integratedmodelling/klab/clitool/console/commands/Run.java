package org.integratedmodelling.klab.clitool.console.commands;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

public class Run implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		List<?> arguments = call.getParameters().get("arguments", List.class);

		if (arguments.size() > 0 && arguments.get(0).toString().equals("demo")) {
			if (arguments.size() == 1) {
				String ret = "";
				for (String test : new Reflections("kim.demos", new ResourcesScanner())
						.getResources(Pattern.compile(".*\\.kim"))) {
					if (test.endsWith(".kim")) {
						ret += (ret.isEmpty() ? "" : "\n") + "   " + MiscUtilities.getURLBaseName(test) + " (" + test + ")";
					}
				}
				return "Available demos:\n" + ret;
			} else {
				for (int i = 1; i < arguments.size(); i++) {
					String arg = arguments.get(i).toString();
					if (!arg.endsWith(".kim")) {
						arg += ".kim";
					}
					URL url = getClass().getClassLoader().getResource("kim.demos/" + arg);
					Engine engine = session.getParentIdentity(Engine.class);
					session.getMonitor().info(url + " -> " + engine.run(url).get());
				}
				return null;
			}
		}

		for (Object resource : arguments) {
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
