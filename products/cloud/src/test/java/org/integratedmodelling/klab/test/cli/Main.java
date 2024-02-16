package org.integratedmodelling.klab.test.cli;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.clitool.CliRuntime;
import org.integratedmodelling.klab.clitool.CliStartupOptions;
import org.integratedmodelling.klab.clitool.console.SysConsole;
import org.integratedmodelling.klab.clitool.console.TermConsole;
import org.integratedmodelling.klab.engine.EngineStartupOptions;

/**
 * A CLI-driven k.LAB modeler. Same as the main for the product, but in test scope so
 * it can use instrumentation and enable debugging outputs.
 * 
 * @author ferdinando.villa
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {

		CliStartupOptions options = new CliStartupOptions();
		// default
		options.setNetwork(true);
		options.initialize(args);

		if (options.isHelp()) {
			System.out.println(new EngineStartupOptions().usage());
			System.exit(0);
		}

		if (options.getArguments().length == 0) {
			TermConsole console = new TermConsole();
			console.start(options);
		} else {
			SysConsole console = new SysConsole();
			CliRuntime.INSTANCE.initialize(console, options);
			String command = StringUtils.join(options.getArguments(), ' ');
			if (!command.trim().isEmpty()) {
				CliRuntime.INSTANCE.getCommandProcessor().processCommand(command);
			}
			CliRuntime.INSTANCE.shutdown();
		}
	}
}
