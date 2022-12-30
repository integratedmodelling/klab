package org.integratedmodelling.klab.k;

import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.CliRuntime;
import org.integratedmodelling.klab.clitool.CliStartupOptions;
import org.integratedmodelling.klab.clitool.console.SysConsole;
import org.integratedmodelling.klab.clitool.console.TermConsole;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.stats.StatsComponent;

/**
 * A CLI-driven k.LAB modeler.
 * 
 * @author ferdinando.villa
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {

		CliStartupOptions options = new CliStartupOptions();
		options.initialize(args);

		Klab.INSTANCE.setStatisticsLocalHandler((obs) -> {
			Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
			if (stc != null) {
				StatsComponent stats = stc.getImplementation(StatsComponent.class);
				if (stats != null) {
					stats.submit(obs, CliRuntime.INSTANCE.getSession().getUser().getUsername(),
							StringUtil.join(CliRuntime.INSTANCE.getSession().getUser().getGroups().stream()
									.map((d) -> d.getId()).toList(), ","));
				}
			}
		});

		if (options.isHelp()) {
			System.out.println(new EngineStartupOptions().usage());
			System.exit(0);
		}

		if (options.getArguments().length == 0) {
			// default
			options.setNetwork(true);
			TermConsole console = new TermConsole();
			console.start(options);
		} else if (options.isTesting()) {
			SysConsole console = new SysConsole();
			ISession session = CliRuntime.INSTANCE.initialize(console, options);
			int exitCode = 0;
			exitCode = Actors.INSTANCE.runAllTests(options.getArguments(), session, options.getOutputFile());
			CliRuntime.INSTANCE.shutdown();
			System.exit(exitCode);

		} else {
			SysConsole console = new SysConsole();
			ISession session = CliRuntime.INSTANCE.initialize(console, options);
			int exitCode = 0;
			for (String argument : options.getArguments()) {
				exitCode += Actors.INSTANCE.run(argument, session);
			}
			CliRuntime.INSTANCE.shutdown();
			System.exit(exitCode);
		}
	}
}
