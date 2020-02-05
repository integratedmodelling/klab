package org.integratedmodelling.klab.hub.network;

import java.util.Collection;
import java.util.List;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

public class ListDockerContainersCmd {
	
	private DockerClient client;
	

	public ListDockerContainersCmd(DockerClient client) {
		super();
		this.client = client;
	}
	
	public List<Container> filter(Collection<String> testLabel) {
        List<Container> containers = client.listContainersCmd()
                .withNameFilter(testLabel)
                .withShowAll(true)
                .exec();
        return containers;
	}
	
	public List<Container> getAll() {
        List<Container> containers = client.listContainersCmd()
                .withShowAll(true)
                .exec();
        return containers;
	}

}
