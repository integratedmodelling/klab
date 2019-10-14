package org.integratedmodelling.controlcenter.runtime;

import java.io.File;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.apache.commons.exec.CommandLine;
import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IProduct;
import org.integratedmodelling.controlcenter.jre.JreModel;
import org.integratedmodelling.controlcenter.product.Instance;
import org.integratedmodelling.controlcenter.product.Product;
import org.integratedmodelling.controlcenter.utils.TimerService;
import org.integratedmodelling.klab.utils.OS;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;

public class EngineInstance extends Instance {

	/**
	 * For now, no setting for this one.
	 */
	static final int debugPort = 8000;
	private static final int POLL_INTERVAL_SECONDS = 3;
	int instancePort;
	int njars;
	int ndirs;
	AtomicBoolean online = new AtomicBoolean(false);
	AtomicReference<EngineInfo> engineInfo = new AtomicReference<>();

	public class EngineInfo {
		public String engineId;
		public String sessionId;
		public long upTime;
		public long bootTime;
		public long engineTime;
		public long totalMemory;
		public long freeMemory;
		public int processorCount;
	}

	public EngineInstance(Product product) {
		super(product);
	}

	protected String getInstanceUrl() {
		return "http://127.0.0.1:" + instancePort + "/modeler";
	}

	protected String getInstanceUrl(String fragment) {
		return "http://127.0.0.1:" + instancePort + "/modeler" + fragment;
	}

	@Override
	protected CommandLine getCommandLine(int build) {

		this.instancePort = ControlCenter.INSTANCE.getSettings().getEnginePort();

		CommandLine ret = new CommandLine(JreModel.INSTANCE.getJavaExecutable());
		ret.addArguments(getOptions(512, ControlCenter.INSTANCE.getSettings().getMaxEngineMemory(), true));

		if (ControlCenter.INSTANCE.getSettings().useDebug()) {
			ret.addArgument("-Xdebug");
			ret.addArgument("-Xbootclasspath/p:lib/jsr166.jar");
			ret.addArgument("-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=" + debugPort);
		}

		ret.addArgument("-Dserver-port=" + instancePort);

		String classpath = getClassPath(build);
		String mainclass = product.getBuild(build).properties.getProperty(IProduct.BUILD_MAINCLASS_PROPERTY);

		if (mainclass == null && njars == 1 && ndirs == 0) {
			ret.addArguments(new String[] { "-jar", classpath });
		} else if (mainclass != null) {
			ret.addArguments(new String[] { "-cp", getClassPath(build) });
			ret.addArgument(mainclass);
		} else {
			ControlCenter.INSTANCE.errorAlert(
					"Remote distribution error: main class is not defined for " + product.getName() + " product");
			ret = null;
		}

		if (ret != null) {
			ret.addArgument("-network");
		}

		return ret;
	}

	private String getClassPath(int build) {

		String ret = "";
		this.njars = 0;
		this.ndirs = 0;
		for (File file : product.getBuild(build).workspace.listFiles()) {
			if (file.toString().endsWith(".jar")) {
				this.njars++;
				ret += (ret.isEmpty() ? "" : OS.get().getClasspathSeparator()) + file.getName();
			} else if (file.isDirectory()) {
				this.ndirs++;
				ret += (ret.isEmpty() ? "" : OS.get().getClasspathSeparator()) + file.getName() + File.separator + "*";
			}
		}
		return ret;
	}

	@Override
	protected boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stop() {
		if (online.get()) {
			try {
				Unirest.get(getInstanceUrl("/engine/admin/shutdown")).asString();
			} catch (UnirestException e) {
				return false;
			}
		} else if (status.get() == Status.RUNNING) {
			// TODO NPE
			executor.getWatchdog().destroyProcess();
		}

		executor = null;
		return true;
	}

	private static String[] getOptions(int minMemM, int maxMemM, boolean isServer) {

		ArrayList<String> ret = new ArrayList<>();

		ret.add("-Xms" + minMemM + "M");
		ret.add("-Xmx" + maxMemM + "M");
		if (isServer) {
			ret.add("-server");
		}
		return ret.toArray(new String[ret.size()]);
	}

	@Override
	public void pollStatus(Consumer<Status> listener) {

		this.statusHandler = listener;

		ControlCenter.INSTANCE.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {

				IInstance.Status prev = getStatus();

				try {
					HttpResponse<JsonNode> response = Unirest.get(getInstanceUrl("/ping")).asJson();
					if (response.getStatus() == 200) {
						status.set(Status.RUNNING);
						online.set(true);
						JSONObject node = response.getBody().getObject();
						EngineInfo info = new EngineInfo();
						info.totalMemory = node.getLong("totalMemory");
						info.freeMemory = node.getLong("freeMemory");
						info.bootTime = node.getLong("bootTime");
						info.engineTime = node.getLong("requestTime");
						info.upTime = node.getLong("uptime");
						info.sessionId = node.get("localSessionId").toString();
						info.processorCount = node.getInt("processorCount");
						info.engineId = node.get("engineId").toString();
						engineInfo.set(info);
					} else {
						status.set(Status.STOPPED);
						online.set(false);
					}
				} catch (UnirestException e) {
					// org.apache.http.conn.HttpHostConnectException on engine off OR starting -
					// just keep previous status unless it was running
					if (status.get() == Status.RUNNING) {
						status.set(Status.STOPPED);
					}
				}

				if (status.get() != prev && statusHandler != null) {
					statusHandler.accept(status.get());
				}
			}

		}, 0, POLL_INTERVAL_SECONDS * 1000);

	}
}
