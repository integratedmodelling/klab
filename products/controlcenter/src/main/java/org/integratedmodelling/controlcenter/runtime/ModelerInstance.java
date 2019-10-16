package org.integratedmodelling.controlcenter.runtime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.TimerTask;
import java.util.function.Consumer;

import org.apache.commons.exec.CommandLine;
import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.product.Instance;
import org.integratedmodelling.controlcenter.product.Product;
import org.integratedmodelling.klab.utils.OS;

public class ModelerInstance extends Instance {

	private static final int POLL_INTERVAL_SECONDS = 5;

	public ModelerInstance(Product product) {
		super(product);
	}

	public File getExecutable(int build) {
		if (OS.get() == OS.MACOS) {
			return new File(product.getLocalWorkspace() + File.separator + build + File.separator + "kmodeler.app");
		}
		if (OS.get() == OS.UNIX) {
			File file = new File(product.getLocalWorkspace() + File.separator + build + File.separator + "k.Modeler");
			file.setExecutable(true);
			return file;
		}
		return new File(product.getLocalWorkspace() + File.separator + build + File.separator + "k.Modeler"
				+ (OS.get() == OS.WIN ? ".exe" : ""));
	}

	@Override
	protected CommandLine getCommandLine(int build) {

		CommandLine cmdLine = new CommandLine(getExecutable(build));
		cmdLine.addArgument("-data");
		cmdLine.addArgument(ControlCenter.INSTANCE.getSettings().getKlabWorkspace().toString());
		return cmdLine;

	}

	@Override
	protected boolean isRunning() {
		File active = new File(ControlCenter.INSTANCE.getWorkdir() + File.separator + ".mactive");
		return active.exists() && (System.currentTimeMillis() - active.lastModified()) < 10000;
	}
	
	@Override
	public boolean stop() {
		boolean ret = isRunning();
		File stopfile = new File(ControlCenter.INSTANCE.getWorkdir() + File.separator + ".mstop");
		try (OutputStream out = new FileOutputStream(stopfile)) {
			out.close();
		} catch (IOException e) {
		} 
		return ret;
	}

	@Override
	public void pollStatus(Consumer<Status> listener) {

		this.statusHandler = listener;

		ControlCenter.INSTANCE.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {

				IInstance.Status prev = getStatus();
				if (isRunning()) {
					status.set(Status.RUNNING);
				} else if (status.get() == Status.RUNNING) {
					status.set(Status.STOPPED);
				}
				
				if (status.get() != prev && statusHandler != null) {
					statusHandler.accept(status.get());
				}
			}

		}, 100, POLL_INTERVAL_SECONDS * 1000);
	}

}
