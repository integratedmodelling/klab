package org.integratedmodelling.klab.hub.network;

import java.util.Properties;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sun.istack.NotNull;

@Document(collection="DockerConfiguration")
public abstract class DockerConfiguration {
	@Id
	String id;
	
	@NotNull
	String dockerHost;
	
	boolean tlsVerify;
	
	String certPath;
	
	String configPath;
	
	String apiVersion;
	
	String registryUrl;
	
	String regUsername;
	
	String regEmail;
	
	String password;
	
	private Set<String> networks;
	
	private Properties properties;
	
	private Set<DockerVolume> volumes;

	public String getId() {
		return id;
	}

	public String getDockerHost() {
		return dockerHost;
	}

	public void setDockerHost(String dockerHost) {
		this.dockerHost = dockerHost;
	}

	public boolean isTlsVerify() {
		return tlsVerify;
	}

	public void setTlsVerify(boolean tlsVerify) {
		this.tlsVerify = tlsVerify;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}

	public String getRegUsername() {
		return regUsername;
	}

	public void setRegUsername(String regUsername) {
		this.regUsername = regUsername;
	}

	public String getRegEmail() {
		return regEmail;
	}

	public void setRegEmail(String regEmail) {
		this.regEmail = regEmail;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
