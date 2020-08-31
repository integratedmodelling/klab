package org.integratedmodelling.klab.hub.network.commands;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.network.DockerVolume;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateVolumeResponse;
import com.github.dockerjava.api.model.Volume;

public class CreateDockerVolume {

	private DockerVolume volume;
	private DockerClient client;

	public CreateDockerVolume(DockerVolume v, DockerClient client) {
		this.volume = v;
		this.client = client;
	}

	public Volume exec() {
        CreateVolumeResponse createVolumeResponse = client.createVolumeCmd().withName(volume.getName())
                .withDriver("local").exec();
        if(createVolumeResponse != null) {
        	return new Volume(volume.getPath());
        } else {
        	throw new BadRequestException("Unable to create docker volume: " + volume );
        }
	}

}
