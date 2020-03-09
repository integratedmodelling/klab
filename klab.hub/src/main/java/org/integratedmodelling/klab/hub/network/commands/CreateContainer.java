package org.integratedmodelling.klab.hub.network.commands;

import org.integratedmodelling.klab.hub.network.DockerConfiguration;
import org.integratedmodelling.klab.hub.network.DockerNode;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;

public class CreateContainer {
	public CreateContainer(DockerClient client, DockerConfiguration config) {
		super();
		this.client = client;
		this.config = config;
	}
	private DockerClient client;
	private DockerConfiguration config;
	
	public CreateContainerResponse exec() {
		if (config.getClass() == DockerNode.class) {
			DockerNode nodeConfig = (DockerNode) config;
			CreateNodeContainer cmd = new CreateNodeContainer(this.client, nodeConfig);
			CreateContainerResponse resp = cmd.exec(nodeConfig.getNode().getNode());
			return resp;
		} else {
			return null;
		}
	}
	

}
