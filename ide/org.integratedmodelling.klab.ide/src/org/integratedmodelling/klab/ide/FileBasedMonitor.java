package org.integratedmodelling.klab.ide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.integratedmodelling.klab.Configuration;

public class FileBasedMonitor {

	long delay;
	private File stopFile;
	private File activeFile;
	// if true, the file is there but in error
	private boolean error;
	private AtomicBoolean closing = new AtomicBoolean(false);

	public FileBasedMonitor(int checkSeconds) {
		this.stopFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + ".mstop");
		this.activeFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + ".mactive");
		try {
			Files.deleteIfExists(this.stopFile.toPath());
		} catch (IOException e) {
			this.error = true;
		}
		this.delay = checkSeconds * 1000;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					Files.delete(activeFile.toPath());
					Files.delete(stopFile.toPath());
				} catch (IOException e) {
				}
			}
		});
	}

	public class RepeatingJob extends Job {

		public RepeatingJob() {
			super("");
		}

		protected IStatus run(IProgressMonitor monitor) {

			if (!closing.get()) {
				if (!activeFile.exists()) {
					try (OutputStream out = new FileOutputStream(activeFile)) {
						out.write("out".getBytes());
					} catch (Exception e) {
					}
				} else {
					// check the date of the file vs. date of startup
					try {
						activeFile.setLastModified(System.currentTimeMillis());
					} catch (Throwable t) {
						// screw it
					}
				}
			}

			if (!error && stopFile.exists()) {
				closing.set(true);
				try {
					Files.delete(activeFile.toPath());
					Files.delete(stopFile.toPath());
				} catch (IOException e) {
				}
				Display.getDefault().asyncExec(() -> PlatformUI.getWorkbench().close());
			} else if (!closing.get()) {
				schedule(delay);
			}

			return Status.OK_STATUS;
		}

	}

	public void start(long delay) {
		new RepeatingJob().schedule(delay);
	}

}
