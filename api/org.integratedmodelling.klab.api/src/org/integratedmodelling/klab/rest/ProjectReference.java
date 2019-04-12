package org.integratedmodelling.klab.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectReference {

	private File rootPath;
	private String name;
	private List<LocalResourceReference> localResources = new ArrayList<>();
	
	public ProjectReference() {
	}

	public File getRootPath() {
		return rootPath;
	}

	public void setRootPath(File rootPath) {
		this.rootPath = rootPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LocalResourceReference> getLocalResources() {
		return localResources;
	}

	public void setLocalResources(List<LocalResourceReference> localResources) {
		this.localResources = localResources;
	}

	@Override
	public String toString() {
		return name;
	}
}
