package org.integratedmodelling.klab.hub.network;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.network.commands.CreateNodeContainer;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
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
			CreateContainerResponse resp = cmd.exec(nodeConfig.getNode().getNode());
			return resp;
		} else {
			return null;
		}
	}
	
	public String createAndDeploy(DockerConfiguration config) {
		if (config.getDependsOn() != null) {
			if(!checkContainerDependencies(config.getDependsOn())) {
				;
			}
		}
		CreateContainerResponse resp = createContainer(config);
		client.startContainerCmd(resp.getId());
		return resp.getId();
	}
	
	private boolean checkContainerDependencies (Set<String> containers) {
		List<Container> hostContainers = client.listContainersCmd().exec();
		Map<String, Integer> cMap = containers.stream().collect(Collectors.toMap(keyMapper, valueMapper))
		boolean running[] = new boolean[containers.size()];
		containers.forEach(name -> {
			for (Container c : hostContainers) {
				for (String cName : c.getNames()) {
					if (cName.equals(name)) {
						if (c.getStatus().toLowerCase().equals("running")) {
							
						}
					}
				}
			}
		});
	}

}
