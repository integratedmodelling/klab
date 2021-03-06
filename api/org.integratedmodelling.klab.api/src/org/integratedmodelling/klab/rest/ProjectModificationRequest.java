package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

public class ProjectModificationRequest {

	public static final String SCENARIO_OPTION = "scenario";
	public static final String PRIVATE_OPTION = "private";
	public static final String LIBRARY_OPTION = "library";

	private String projectId;
	private String assetId;
	private String scriptName;
	private String scriptPath;
	private String scriptType;
	
	private Map<String, String> parameters = new HashMap<>();

	public ProjectModificationRequest() {
	}

	public ProjectModificationRequest(String projectId, String assetId) {
		this.projectId = projectId;
		this.assetId = assetId;
	}

	/**
	 * ID of the project where the modification is being made.
	 * 
	 * @return project
	 */
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * If the asset being created is a script, pass the desired name here
	 * 
	 * @return name for the script annotation
	 */
	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * The ID for the assed being deleted or created (namespace, scenario, resource)
	 * 
	 * @return id
	 */
	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getScriptType() {
		return scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

}
