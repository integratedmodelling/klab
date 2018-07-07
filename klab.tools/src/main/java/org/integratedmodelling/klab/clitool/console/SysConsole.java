package org.integratedmodelling.klab.clitool.console;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.clitool.api.IConsole;

public class SysConsole implements IConsole {

	@Override
	public void grabCommandLine(String prompt, String endCommand, CommandListener listener) {
	}

	@Override
	public void error(Object e) {
		Logging.INSTANCE.error(e);
	}

	@Override
	public void warning(Object e) {
		Logging.INSTANCE.warn(e);
	}

	@Override
	public void info(Object e, String infoClass) {
		Logging.INSTANCE.info(e);
	}

	@Override
	public void echo(Object string) {
		System.out.print(string);
	}
	
	@Override
	public void scream(Object string) {
		System.out.print(string);
	}

	@Override
	public void outputResult(String input, Object ret) {
		System.out.println(input + ": " + ret.toString());
	}

	@Override
	public void reportCommandResult(String input, boolean ok) {

	}

	@Override
	public void setPrompt(String s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableInput() {
		// TODO Auto-generated method stub

	}

}
