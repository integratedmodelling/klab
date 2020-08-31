package org.integratedmodelling.klab.hub.network;

import java.util.Properties;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sun.istack.NotNull;

@Document(collection="DeploymentConfiguration")
public abstract class DockerConfiguration {
	@Id
	String id;
	
	@DBRef
	private DockerHostConfig hostConfig;
	
	private Set<String> networks;
	
	private Properties properties;
	
	private Set<DockerVolume> volumes;
	
	private DockerPorts ports;
	
	private Set<String> dependsOn;
	
	@NotNull
	private String image;

	public String getId() {
		return id;
	}
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Set<DockerVolume> getVolumes() {
		return volumes;
	}

	public void setVolumes(Set<DockerVolume> volumes) {
		this.volumes = volumes;
	}

	public Set<String> getNetworks() {
		return networks;
	}

	public void setNetworks(Set<String> networks) {
		this.networks = networks;
	}

	public DockerPorts getPorts() {
		return ports;
	}

	public void setPorts(DockerPorts ports) {
		this.ports = ports;
	}
	
	public DockerHostConfig getHostConfig() {
		return hostConfig;
	}

	public void setHostConfig(DockerHostConfig hostConfig) {
		this.hostConfig = hostConfig;
	}

	public Set<String> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(Set<String> dependsOn) {
		this.dependsOn = dependsOn;
	}
	
	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}

}
