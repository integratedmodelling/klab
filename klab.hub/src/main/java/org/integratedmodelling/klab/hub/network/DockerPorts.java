package org.integratedmodelling.klab.hub.network;

import java.util.List;

public class DockerPorts {
	private List<Integer> exposed;
	private List<Integer> hostBinding;
	
	public List<Integer> getExposed() {
		return exposed;
	}
	public void setExposed(List<Integer> exposed) {
		this.exposed = exposed;
	}
	public List<Integer> getHostBinding() {
		return hostBinding;
	}
	public void setHostBinding(List<Integer> hostBinding) {
		this.hostBinding = hostBinding;
	}
}
