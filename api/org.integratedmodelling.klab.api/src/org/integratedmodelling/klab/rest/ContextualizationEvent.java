package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.utils.OS;

public class ContextualizationEvent {

	public static class Model {
		
		private String id;
		private boolean error;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean isError() {
			return error;
		}

		public void setError(boolean error) {
			this.error = error;
		}

	}

	public static class Resource {
		
		private String urn;
		private String node;
		private boolean error;
		
		public String getUrn() {
			return urn;
		}

		public void setUrn(String urn) {
			this.urn = urn;
		}

		public boolean isError() {
			return error;
		}

		public void setError(boolean error) {
			this.error = error;
		}

		/**
		 * @return the node
		 */
		public String getNode() {
			return node;
		}

		/**
		 * @param node the node to set
		 */
		public void setNode(String node) {
			this.node = node;
		}
		
	}

	public static class Observation {
		
		private String observable;
		private Mode mode;
		private long utcStart;
		private long utcEnd;
		private long size;
		private boolean succeeded;
		private boolean interactive;
		private List<Model> models = new ArrayList<>();
		private List<Resource> resources = new ArrayList<>();

		public String getObservable() {
			return observable;
		}

		public void setObservable(String observable) {
			this.observable = observable;
		}

		public long getUtcStart() {
			return utcStart;
		}

		public void setUtcStart(long utcStart) {
			this.utcStart = utcStart;
		}

		public long getUtcEnd() {
			return utcEnd;
		}

		public void setUtcEnd(long utcEnd) {
			this.utcEnd = utcEnd;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public boolean isSucceeded() {
			return succeeded;
		}

		public void setSucceeded(boolean succeeded) {
			this.succeeded = succeeded;
		}

		public List<Model> getModels() {
			return models;
		}

		public void setModels(List<Model> models) {
			this.models = models;
		}

		public List<Resource> getResources() {
			return resources;
		}

		public void setResources(List<Resource> resources) {
			this.resources = resources;
		}

		public boolean isInteractive() {
			return interactive;
		}

		public void setInteractive(boolean interactive) {
			this.interactive = interactive;
		}

		public Mode getMode() {
			return mode;
		}

		public void setMode(Mode mode) {
			this.mode = mode;
		}

	}

	private String uid;
	private String eid;
	private String cid; // may be continuing a previous event if cid is the same.
	private String version;
	private String build;
	private String branch;
	private OS os;
	private int errorCount;
	private int warningCount;
	private List<Double> boundingBox = new ArrayList<>(4);
	private ITime.Resolution timeResolution;
	private ITime.Type timeType;
	private long timeStart;
	private long timeEnd;
	private long steps;
	private long engineUptime;
	private long sessionUptime;
	private int contextCount;
	private List<String> exceptions = new ArrayList<>();
	private List<Observation> tasks = new ArrayList<>();

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getWarningCount() {
		return warningCount;
	}

	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}

	public List<Double> getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(List<Double> boundingBox) {
		this.boundingBox = boundingBox;
	}

	public ITime.Resolution getTimeResolution() {
		return timeResolution;
	}

	public void setTimeResolution(ITime.Resolution timeResolution) {
		this.timeResolution = timeResolution;
	}

	public ITime.Type getTimeType() {
		return timeType;
	}

	public void setTimeType(ITime.Type timeType) {
		this.timeType = timeType;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}

	public long getSteps() {
		return steps;
	}

	public void setSteps(long steps) {
		this.steps = steps;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}

	public List<Observation> getTasks() {
		return tasks;
	}

	public void setTasks(List<Observation> tasks) {
		this.tasks = tasks;
	}

	public long getEngineUptime() {
		return engineUptime;
	}

	public void setEngineUptime(long engineUptime) {
		this.engineUptime = engineUptime;
	}

	public long getSessionUptime() {
		return sessionUptime;
	}

	public void setSessionUptime(long sessionUptime) {
		this.sessionUptime = sessionUptime;
	}

	public int getContextCount() {
		return contextCount;
	}

	public void setContextCount(int contextCount) {
		this.contextCount = contextCount;
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

}
