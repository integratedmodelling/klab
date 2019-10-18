package org.integratedmodelling.controlcenter.product;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.io.FileUtils;
import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IProduct;
import org.integratedmodelling.controlcenter.jre.JreModel;
import org.integratedmodelling.controlcenter.product.Distribution.SyncListener;
import org.integratedmodelling.controlcenter.product.Product.Build;
import org.integratedmodelling.controlcenter.runtime.ModelerInstance;
import org.integratedmodelling.klab.utils.OS;

public abstract class Instance implements IInstance {

	protected Product product;
	protected AtomicReference<Status> status = new AtomicReference<>(Status.UNKNOWN);
	protected DefaultExecutor executor;
	protected Consumer<Status> statusHandler;

	public Instance(Product product) {
		this.product = product;
	}

	@Override
	public IProduct getProduct() {
		return product;
	}
	
	public void setProduct(IProduct product) {
		this.product = (Product)product;
	}

	@Override
	public Status getStatus() {
		return status.get();
	}

	protected abstract CommandLine getCommandLine(int build);

	protected File getWorkingDirectory(int build) {
		return product.getBuild(build).workspace;
	}

	protected abstract boolean isRunning();

	@Override
	public boolean start(int build) {

		if (this instanceof ModelerInstance && OS.get() == OS.MACOS) {
			// we need to use Desktop, so no way to know if it is closed, we don't touch the
			// status
			try {
            	File executable = ((ModelerInstance)this).getExecutable(build);
				Desktop.getDesktop().open(executable);
			} catch (Throwable e) {
				ControlCenter.INSTANCE.errorAlert("Could not launch the Eclipse product. Please launch it manually in "
						+ product.getLocalWorkspace());
				return false;
			}
			return true;
		}

		CommandLine cmdLine = getCommandLine(build);

		/*
		 * assume error was reported
		 */
		if (cmdLine == null) {
			return false;
		}

		this.executor = new DefaultExecutor();
		this.executor.setWorkingDirectory(getWorkingDirectory(build));

		Map<String, String> env = new HashMap<>();
		env.putAll(System.getenv());

		status.set(Status.WAITING);
		if (this.statusHandler != null) {
			this.statusHandler.accept(status.get());
		}

		try {
			this.executor.execute(cmdLine, env, new ExecuteResultHandler() {

				@Override
				public void onProcessFailed(ExecuteException arg0) {
					arg0.printStackTrace();
					status.set(Status.ERROR);
					if (statusHandler != null) {
						statusHandler.accept(status.get());
					}
				}

				@Override
				public void onProcessComplete(int arg0) {
//					status.set(Status.STOPPED);
//					if (statusHandler != null) {
//						statusHandler.accept(status.get());
//					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			status.set(Status.ERROR);
		}

		return true;
	}

	@Override
	public boolean stop() {
		// does nothing - override
		return false;
	}

	@Override
	public List<Integer> getInstalledBuilds() {
		List<Integer> ret = new ArrayList<>();
		File ws = this.product.getLocalWorkspace();
		for (File bws : ws.listFiles()) {
			if (bws.isDirectory() && new File(bws + File.separator + "filelist.txt").exists()) {
				ret.add(Integer.parseInt(bws.getName()));
			}
		}

		/*
		 * most recent first
		 */
		ret.sort((o1, o2) -> o2.compareTo(o1));

		return ret;
	}

	@Override
	public Distribution download(int buildNumber, SyncListener listener) {

		Build build = this.product.getBuild(buildNumber);
		if (build != null) {

			int previous = -1;
			for (int n : getInstalledBuilds()) {
				if (n < buildNumber) {
					previous = n;
					break;
				}
			}

			if (previous > 0) {
				/*
				 * preload the worspace with the previous distribution for incremental download.
				 */
				File previousWorkspace = new File(this.product.getLocalWorkspace() + File.separator + previous);
				if (previousWorkspace.isDirectory()) {
					build.workspace.mkdirs();
					try {
						FileUtils.copyDirectory(previousWorkspace, build.workspace, true);
					} catch (IOException e) {
						// screw it
					}
				}
			}

			Distribution ret = new Distribution(build.getDownloadUrl(), build.workspace);
			ret.setListener(listener);
			ret.sync();
			return ret;
		}

		throw new RuntimeException("Internal error: no build for download");
	}

}
