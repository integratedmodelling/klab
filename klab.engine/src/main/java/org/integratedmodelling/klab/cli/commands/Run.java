package org.integratedmodelling.klab.cli.commands;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.reflections8.Reflections;
import org.reflections8.scanners.ResourcesScanner;

public class Run implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		List<?> arguments = call.getParameters().get("arguments", List.class);
		String visProp = System.getProperty("visualize");

		if (arguments.size() > 0 && arguments.get(0).toString().equals("demo")) {
			if (arguments.size() == 1) {
				String ret = "";
				for (String test : new Reflections("kim.demos", new ResourcesScanner())
						.getResources(Pattern.compile(".*\\.kim"))) {
					if (test.endsWith(".kim")) {
						ret += (ret.isEmpty() ? "" : "\n") + "   " + MiscUtilities.getURLBaseName(test) + " (" + test
								+ ")";
					}
				}
				return "Available demos:\n" + ret;
			} else {
				String ret = "";
				for (int i = 1; i < arguments.size(); i++) {

					String arg = arguments.get(i).toString();

					if (!arg.endsWith(".kim")) {
						arg += ".kim";
					}

					URL url = getClass().getClassLoader().getResource("kim.demos/" + arg);

					if (call.getParameters().get("visualize", false)) {
						System.setProperty("visualize", "true");
						System.setProperty("waitForKey", "false");
					}

					String uname = MiscUtilities.getURLBaseName(url.toString());
					try {
                        ret += (ret.isEmpty() ? "" : "\n") + "[S " + session.getId() + "] " + uname + " -> "
                        		+ session.run(url).get();
                    } catch (Exception e) {
                        throw new KlabException(e);
                    }

					if (call.getParameters().get("visualize", false)) {
						System.clearProperty("waitForKey");
						if (visProp == null) {
							System.clearProperty("visualize");
						} else {
							System.setProperty("visualize", visProp);
						}
					}
				}
				return ret;
			}
		}

		String ret = "";
		for (Object resource : arguments) {

			URL url = null;
			if (resource.toString().contains("://")) {
				try {
                    url = new URL(resource.toString());
                } catch (MalformedURLException e) {
                    throw new KlabException(e);
                }
			} else {
				File file = Klab.INSTANCE.resolveFile(resource.toString());
				if (file != null) {
					try {
                        url = file.toURI().toURL();
                    } catch (MalformedURLException e) {
                        throw new KlabException(e);
                    }
				} else {
					throw new KlabIOException("file " + resource + " was not found");
				}
			}

			if (url != null) {

				if (call.getParameters().get("visualize", false)) {
					System.setProperty("waitForKey", "false");
					System.setProperty("visualize", "true");
				}

				String uname = MiscUtilities.getURLBaseName(url.toString());
				try {
                    ret += "[S " + session.getId() + "] " + uname + " -> " + session.run(url).get();
                } catch (Exception e) {
                    throw new KlabException(e);
                }

				if (call.getParameters().get("visualize", false)) {
					System.clearProperty("waitForKey");
					if (visProp == null) {
						System.clearProperty("visualize");
					} else {
						System.setProperty("visualize", visProp);
					}
				}
			}
		}
		return ret;
	}

}
