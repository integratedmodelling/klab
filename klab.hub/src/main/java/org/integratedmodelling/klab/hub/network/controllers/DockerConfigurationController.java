package org.integratedmodelling.klab.hub.network.controllers;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.network.CreateNodeContainer;
import org.integratedmodelling.klab.hub.network.DockerNode;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.repository.DockerConfigurationRepository;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;


@RestController
@RequestMapping("/api/v2/network/configurations")
public class DockerConfigurationController {
	
	private MongoNodeRepository nodeRepo;
	private DockerConfigurationRepository dockerRepo;
	
	
	@Autowired
	public DockerConfigurationController(MongoNodeRepository nodeRepo,
			DockerConfigurationRepository dockerRepo) {
		super();
		this.nodeRepo = nodeRepo;
		this.dockerRepo = dockerRepo;
	}


	@PostMapping(value = "node", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> createNodeConfiguration(@RequestBody DockerNode config)  {
		MongoNode node = nodeRepo
				.findByNodeIgnoreCase(config.getNode().getNode())
				.orElseThrow(() -> new BadRequestException("Could not match node to one in database."));
		config.setNode(node);
		
		dockerRepo.insert(config);		
		return new ResponseEntity<>(config,HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/node/{id}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> launchNodeDocker(@PathVariable("id") String id)  {
		
		DockerNode config = (DockerNode) dockerRepo.findById(id)
				.orElseThrow(() -> new BadRequestException("Could not match node configuration id to one in database."));
		
		DefaultDockerClientConfig dockerConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
				.withDockerHost(config.getDockerHost())
				.build();
		
		DockerClient client = DockerClientBuilder.getInstance(dockerConfig)
				  .build();
		
		CreateContainerResponse resp = new CreateNodeContainer(client, config).exec();
		client.startContainerCmd(resp.getId()).exec();
		
		return new ResponseEntity<>(resp,HttpStatus.CREATED);
	}

}
