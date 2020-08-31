package org.integratedmodelling.klab.hub.network;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

@Component
public class Docker {
	
	private DockerClient client;
	
	public DockerClient getClient() {
		return client;
	}

	public void configureClient(DockerHostConfig config) {
		DefaultDockerClientConfig dockerConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
				.withDockerHost(config.getDockerHost())
				.build();

		client = DockerClientBuilder.getInstance(dockerConfig).build();
	}
	
	

}
