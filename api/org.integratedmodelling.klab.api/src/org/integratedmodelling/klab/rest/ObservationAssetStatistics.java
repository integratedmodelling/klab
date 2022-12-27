package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.rest.DataflowState.Status;

public class ObservationAssetStatistics {

	public enum Type {
		ResolvedObservable, Model, Resource, Export
	}

	private Type type;

	/**
	 * Model URN, resource URN, or observable (either computed or exported).
	 */
	private String name;

	/**
	 * Source server if not same that made the observation.
	 */
	private String source;

	private List<String> scenarios = new ArrayList<>();

	private Status status;

	/**
	 * Time is included in the total time for the parent asset or observation. Do
	 * not sum these up among assets of different type.
	 */
	private double computationTime;

	/**
	 * number of "passes" for this asset use within the computation it belongs to.
	 */
	private int computations;

	/**
	 * Number of scheduled executions if an actuator (type==Model), zero otherwise.
	 * Even in actuators zero is possible as the initialization step is not counted.
	 */
	private int schedules;

	/**
	 * Only filled in when relevant, currently only the bytes downloaded for
	 * exports.
	 */
	private long size;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public double getComputationTime() {
		return computationTime;
	}

	public void setComputationTime(double computationTime) {
		this.computationTime = computationTime;
	}

	public int getComputations() {
		return computations;
	}

	public void setComputations(int computations) {
		this.computations = computations;
	}

	public int getSchedules() {
		return schedules;
	}

	public void setSchedules(int schedules) {
		this.schedules = schedules;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
