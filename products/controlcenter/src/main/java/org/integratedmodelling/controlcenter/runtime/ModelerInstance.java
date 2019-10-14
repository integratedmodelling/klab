package org.integratedmodelling.controlcenter.runtime;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.apache.commons.exec.CommandLine;
import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IInstance.Status;
import org.integratedmodelling.controlcenter.product.Instance;
import org.integratedmodelling.controlcenter.product.Product;
import org.integratedmodelling.controlcenter.runtime.EngineInstance.EngineInfo;
import org.integratedmodelling.controlcenter.utils.TimerService;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;

public class ModelerInstance extends Instance {

	private static final int POLL_INTERVAL_SECONDS = 5;

	public ModelerInstance(Product product) {
		super(product);
	}

	@Override
	protected CommandLine getCommandLine(int build) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void pollStatus(Consumer<Status> listener) {

		this.statusHandler = listener;

		ControlCenter.INSTANCE.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {

				IInstance.Status prev = getStatus();

//				try {
//					HttpResponse<JsonNode> response = Unirest.get(getInstanceUrl("/ping")).asJson();
//					if (response.getStatus() == 200) {
//						status.set(Status.RUNNING);
//						online.set(true);
//						JSONObject node = response.getBody().getObject();
//						EngineInfo info = new EngineInfo();
//						info.totalMemory = node.getLong("totalMemory");
//						info.freeMemory = node.getLong("freeMemory");
//						info.bootTime = node.getLong("bootTime");
//						info.engineTime = node.getLong("requestTime");
//						info.upTime = node.getLong("uptime");
//						info.sessionId = node.get("localSessionId").toString();
//						info.processorCount = node.getInt("processorCount");
//						info.engineId = node.get("engineId").toString();
//						engineInfo.set(info);
//					} else {
//						status.set(Status.STOPPED);
//						online.set(false);
//					}
//				} catch (UnirestException e) {
//					// org.apache.http.conn.HttpHostConnectException on engine off OR starting -
//					// just keep previous status unless it was running
//					if (status.get() == Status.RUNNING) {
//						status.set(Status.STOPPED);
//					}
//				}

				if (status.get() != prev && statusHandler != null) {
					statusHandler.accept(status.get());
				}
			}

		}, 100, POLL_INTERVAL_SECONDS * 1000);
	}

}
