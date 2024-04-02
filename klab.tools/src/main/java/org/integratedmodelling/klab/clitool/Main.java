package org.integratedmodelling.klab.clitool;

import org.integratedmodelling.klab.clitool.console.SysConsole;
import org.integratedmodelling.klab.clitool.console.TermConsole;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.integratedmodelling.klab.utils.StringUtils;

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
