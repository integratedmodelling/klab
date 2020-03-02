package org.integratedmodelling.klab.hub.network.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.network.DockerEntryList;
import org.integratedmodelling.klab.hub.network.DockerNode;
import org.integratedmodelling.klab.hub.network.DockerPorts;
import org.integratedmodelling.klab.hub.network.DockerVolume;
import org.integratedmodelling.klab.hub.network.ListDockerContainersCmd;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.api.model.Volume;

public class CreateNodeContainer {
	
	private DockerClient client;
	private DockerNode config;
	
	public CreateNodeContainer(DockerClient client, DockerNode config) {
		super();
		this.client = client;
		this.config = config;
	}
	
	public CreateContainerResponse exec(String name) {
		String containerName = name; 
		Collection<String> c = new HashSet<>();
		c.add("/"+containerName);
		
        List<Container> containers = new ListDockerContainersCmd(client).filter(c);
		if(!containers.isEmpty()) {
			throw new BadRequestException("A container by that name is already running or waiting");
		}
		
		CreateContainerCmd cmd = client.createContainerCmd(config.getImage())
				.withName(containerName);
		
		HostConfig hostConfig = new HostConfig();
		
		if(!config.getVolumes().isEmpty()) {
			List<Bind> binds = new ArrayList<>();
			for(DockerVolume v: config.getVolumes()) {
				Volume volume = new CreateDockerVolume(v, client).exec();
				binds.add(new Bind(v.getName(), volume));
			}
			hostConfig.withBinds(binds);
		}
		
	
		if(config.getPorts() != null) {
			DockerPorts dp = config.getPorts();
			Ports pb = new Ports();
			List<ExposedPort> exposedPorts = new ArrayList<>();
			int index = 0;
			for (int port : dp.getExposed()) {
				ExposedPort p = ExposedPort.parse(Integer.toString(port));
				exposedPorts.add(p);
				if(!dp.getHostBinding().isEmpty()) {
					if(dp.getHostBinding().get(index) != null) {
						pb.bind(p, Binding.bindPort(dp.getHostBinding().get(index)));
					}
				}
			}
			if(!exposedPorts.isEmpty()) {
				cmd.withExposedPorts(exposedPorts);
			}
			hostConfig.withPortBindings(pb);
		}
		
		if(config.getProperties() != null) {
			List<String> entryCmd = new DockerEntryList().getFromConfig(config);
			cmd.withEntrypoint(entryCmd);
		}
		
		cmd.withHostConfig(hostConfig);
		CreateContainerResponse container = cmd.exec();
		
		return container;
		
	}
	
	
}
