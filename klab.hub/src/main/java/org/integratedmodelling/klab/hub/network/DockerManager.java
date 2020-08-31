package org.integratedmodelling.klab.hub.network;

import org.integratedmodelling.klab.hub.network.commands.CheckContainerDependencies;
import org.integratedmodelling.klab.hub.network.commands.CreateNodeContainer;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public enum DockerManager {
	INSTANCE;
	
	private DockerClient client;
	
	
	public void runDockerContainer(DockerConfiguration config) {
		
	}

	public DockerClient getClient() {
		return client;
	}

	public void configureClient(DockerHostConfig config) {
		DefaultDockerClientConfig dockerConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
				.withDockerHost(config.getDockerHost())
				.build();

		this.client = DockerClientBuilder.getInstance(dockerConfig).build();
	}
	
	public CreateContainerResponse createContainer(DockerConfiguration config) {
		if (config.getClass() == DockerNode.class) {
			DockerNode nodeConfig = (DockerNode) config;
			CreateNodeContainer cmd = new CreateNodeContainer(this.client, nodeConfig);
			CreateContainerResponse resp = cmd.exec(nodeConfig.getNode().getName());
			return resp;
		} else {
			return null;
		}
	}
	
	public String createAndDeploy(DockerConfiguration config) {
		
		if (config.getDependsOn() != null) {
			new CheckContainerDependencies(this.client, config.getDependsOn()).exec();
		}
		
		CreateContainerResponse resp = createContainer(config);
		client.startContainerCmd(resp.getId());
		return resp.getId();
	}

}
