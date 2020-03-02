package org.integratedmodelling.klab.hub.network.controllers;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.network.DockerManager;
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


@RestController
@RequestMapping("/api/v2/network/configurations")
public class DockerDeploymentController {
	
	private MongoNodeRepository nodeRepo;
	private DockerConfigurationRepository dockerRepo;
	
	
	@Autowired
	public DockerDeploymentController(MongoNodeRepository nodeRepo,
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
		
		DockerManager.INSTANCE.configureClient(config.getHostConfig());
		
		String containerId = DockerManager.INSTANCE.createAndDeploy(config);
		
		return new ResponseEntity<>(containerId,HttpStatus.CREATED);
	}

}
