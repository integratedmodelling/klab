package org.integratedmodelling.klab.hub.network;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.integratedmodelling.klab.hub.exception.BadRequestException;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;

public class CreateNodeContainer {
	
	private DockerClient client;
	private DockerNode config;
	
	public CreateNodeContainer(DockerClient client, DockerNode config) {
		super();
		this.client = client;
		this.config = config;
	}
	
	public CreateContainerResponse exec() {
		String containerName = config.getNode().getNode(); 
		Collection<String> c = new HashSet<>();
		c.add("/"+containerName);
		
        List<Container> containers = new ListDockerContainersCmd(client).filter(c);
		if(!containers.isEmpty()) {
			throw new BadRequestException("A container by that name is already running or waiting");
		}
		
		ExposedPort port = ExposedPort.parse(Integer.toString(config.getPort()));
		Ports portBindings = new Ports();
		portBindings.bind(port, Binding.bindPort(config.getPort()));
		
		List<String> entryCmd = new DockerEntryList().getFromConfig(config);
		
		CreateContainerResponse container = client.createContainerCmd(config.getImage())
				   .withExposedPorts(port)
				   .withHostConfig(newHostConfig().withPortBindings(portBindings))
				   .withName(containerName)
				   .withEntrypoint(entryCmd)
				   .exec();
		return container;
		
	}
	
	
	
}
