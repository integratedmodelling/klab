package org.integratedmodelling.klab.hub.network.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.hub.exception.BadRequestException;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

public class CheckContainerDependencies {
	
	public CheckContainerDependencies(DockerClient client, Set<String> dependencies) {
		super();
		this.client = client;
		this.dependencies = dependencies;
	}

	private DockerClient client;
	private Set<String> dependencies;
	
	public boolean exec() {
		List<Container> hostContainers = client.listContainersCmd().exec();
		Set<String> up = new HashSet<>();
		hostContainers.forEach((Container  c) -> {
			if(c.getStatus().equals("running")) {
				for(String name : c.getNames()) {
					dependencies.forEach((String need) -> {
						if(need.equals(name)) {
							up.add(need);
						}
					});
				}
			}
        });
		
		dependencies.removeAll(up);
		
		if(dependencies.isEmpty()) {
			return true;
		} else {
			throw new BadRequestException("No running container(s) by name: " + dependencies.toString());
		}
	}

}
