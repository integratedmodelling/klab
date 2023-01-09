package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.rest.DataflowState.Status;

/**
 * Package sent to a stats service to document the result of an observation
 * query. Each "user-level" query (including those for models) can produce one
 * of these.
 * 
 * @author Ferd
 *
 */
public class ObservationResultStatistics {

	/**
	 * List of assets produced, including resolved observables, models, resources
	 * and exports. Although presented here in a flat list, assets are born nested,
	 * so times of execution only compound among assets of the same type. The
	 * nesting structure is not kept in the statistics although it appears in the
	 * ActivityBuilder that generated it.
	 */
	private List<ObservationAssetStatistics> assets = new ArrayList<>();
	/**
	 * Context ID. Never null. If root == true, this describes the first context
	 * observation with this ID. Otherwise it must refer to a previously notified
	 * one.
	 */
	private String contextId;

	/**
	 * Not null only in contexts and observations done with partial coverage.
	 */
	private ScaleStatistics scaleStatistics;

	private String nodeVersion;
	private String engineVersion;
	private String statsVersion;
	private List<String> scenarios = new ArrayList<>();
	private Status status;
	private String application;
	private String dataflow;
	private double dataflowComplexity;
	private double resolvedCoverage;

	/**
	 * Start and end time are in milliseconds from epoch, zulu time
	 */
	private long startTime;
	private long endTime;

	/**
	 * Total duration is start to end, redundant with the above
	 */
	private double durationSeconds;

	/**
	 * The engine this comes from. May be CLI or API if not an authorized remote
	 * engine.
	 */
	private String engineName;

	/**
	 * Observable or model/view URN.
	 */
	private String observable;

	/**
	 * True for any observation that created a context
	 */
	private boolean root;

	/**
	 * Observation name, not null only in contexts.
	 */
	private String observationName;

	/**
	 * If this is true, the query is an export and it will contain a single asset
	 * corresponding to the export itself.
	 */
	private boolean export;

	public List<ObservationAssetStatistics> getAssets() {
		return assets;
	}

	public void setAssets(List<ObservationAssetStatistics> assets) {
		this.assets = assets;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public ScaleStatistics getScaleStatistics() {
		return scaleStatistics;
	}

	public void setScaleStatistics(ScaleStatistics scaleStatistics) {
		this.scaleStatistics = scaleStatistics;
	}

	public String getNodeVersion() {
		return nodeVersion;
	}

	public void setNodeVersion(String nodeVersion) {
		this.nodeVersion = nodeVersion;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public void setEngineVersion(String engineVersion) {
		this.engineVersion = engineVersion;
	}

	public String getStatsVersion() {
		return statsVersion;
	}

	public void setStatsVersion(String statsVersion) {
		this.statsVersion = statsVersion;
	}

	public List<String> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<String> scenarios) {
		this.scenarios = scenarios;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public double getDurationSeconds() {
		return durationSeconds;
	}

	public void setDurationSeconds(double durationSeconds) {
		this.durationSeconds = durationSeconds;
	}

	public String getEngineName() {
		return engineName;
	}

	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}

	public String getObservable() {
		return observable;
	}

	public void setObservable(String observable) {
		this.observable = observable;
	}

	public String getObservationName() {
		return observationName;
	}

	public void setObservationName(String observationName) {
		this.observationName = observationName;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "ObservationResultStatistics [assets="
				+ (assets != null ? assets.subList(0, Math.min(assets.size(), maxLen)) : null) + ", contextId="
				+ contextId + ", scaleStatistics=" + scaleStatistics + ", nodeVersion=" + nodeVersion
				+ ", engineVersion=" + engineVersion + ", statsVersion=" + statsVersion + ", scenarios="
				+ (scenarios != null ? scenarios.subList(0, Math.min(scenarios.size(), maxLen)) : null) + ", status="
				+ status + ", startTime=" + startTime + ", endTime=" + endTime + ", durationSeconds=" + durationSeconds
				+ ", engineName=" + engineName + ", observable=" + observable + ", root=" + root + ", observationName="
				+ observationName + "]";
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getDataflow() {
		return dataflow;
	}

	public void setDataflow(String dataflow) {
		this.dataflow = dataflow;
	}

	public double getDataflowComplexity() {
		return dataflowComplexity;
	}

	public void setDataflowComplexity(double dataflowComplexity) {
		this.dataflowComplexity = dataflowComplexity;
	}

	public double getResolvedCoverage() {
		return resolvedCoverage;
	}

	public void setResolvedCoverage(double resolvedCoverage) {
		this.resolvedCoverage = resolvedCoverage;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

}
