package org.integratedmodelling.klab.hub.network.commands;

import org.integratedmodelling.klab.hub.network.DockerConfiguration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;

public class CreateContainerAndRun {
	
	public CreateContainerAndRun(DockerConfiguration config, DockerClient client) {
		super();
		this.config = config;
		this.client = client;
	}

	private DockerConfiguration config;
	private DockerClient client;
	
	
	public String exec() {
		if (config.getDependsOn() != null) {
			new CheckContainerDependencies(this.client, config.getDependsOn()).exec();
		}
		CreateContainerResponse resp = new CreateContainer(client, config).exec();
		client.startContainerCmd(resp.getId()).exec();
		return resp.getId();
		
	}

}
