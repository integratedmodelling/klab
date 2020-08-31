package org.integratedmodelling.klab.hub.network;

public class DockerVolume {
	
	private String name;
	private Boolean persistant;
	private String path;
	
	public String getName() {
		return name;
	}
	public DockerVolume setName(String name) {
		this.name = name;
		return this;
	}
	public Boolean getPersistant() {
		return persistant;
	}
	public DockerVolume setPersistant(Boolean persistant) {
		this.persistant = persistant;
		return this;
	}
	public String getPath() {
		return path;
	}
	public DockerVolume setPath(String path) {
		this.path = path;
		return this;
	}

}
