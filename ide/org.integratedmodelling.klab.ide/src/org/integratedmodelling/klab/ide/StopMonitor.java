package org.integratedmodelling.klab.ide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.PlatformUI;
import org.integratedmodelling.klab.Configuration;

public class StopMonitor {

	long delay;
	private File stopFile;
	private File activeFile;
	// if true, the file is there but in error
	private boolean error;

	public StopMonitor(int checkSeconds) {
		this.stopFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + ".mstop");
		this.activeFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + ".mactive");
		try {
			Files.deleteIfExists(this.stopFile.toPath());
		} catch (IOException e) {
			this.error = true;
		}
		this.delay = checkSeconds * 1000;
//		Runtime.getRuntime().addShutdownHook();
	}
	
	public class RepeatingJob extends Job {

		public RepeatingJob() {
			super("");
		}

		protected IStatus run(IProgressMonitor monitor) {

			if (!activeFile.exists()) {
				try (OutputStream out = new FileOutputStream(activeFile)) {
					out.write("out".getBytes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// check the date of the file vs. date of startup
			}
			
			if (!error && stopFile.exists()) {
				try {
					Files.delete(activeFile.toPath());
					Files.delete(stopFile.toPath());
				} catch (IOException e) {
				}
				PlatformUI.getWorkbench().close();
			} else {
				schedule(delay);
			}

			return Status.OK_STATUS;
		}

	}

	public void start(long delay) {
		new RepeatingJob().schedule(delay);
	}

}
