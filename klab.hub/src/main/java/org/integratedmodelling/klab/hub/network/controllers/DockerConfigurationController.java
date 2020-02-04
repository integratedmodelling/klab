package org.integratedmodelling.klab.hub.network.controllers;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
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
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;



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
		
		DockerNode docker = (DockerNode) dockerRepo.findById(id)
				.orElseThrow(() -> new BadRequestException("Could not match node to one in database."));
		
		DockerClientConfig clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
				  .withDockerHost(docker.getDockerHost())
				  .build();
		
		DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
				  .withReadTimeout(1000)
				  .withConnectTimeout(1000)
				  .withMaxTotalConnections(100)
				  .withMaxPerRouteConnections(10);
		
		DockerClient dockerClient = DockerClientBuilder.getInstance(clientConfig)
				  .withDockerCmdExecFactory(dockerCmdExecFactory)
				  .build();
		
		
		ExposedPort port = ExposedPort.parse(Integer.toString(docker.getPort()));
		
		Ports portBindings = new Ports();
		portBindings.bind(port, Binding.bindPort(docker.getPort()));
		
		List<String> entry = new ArrayList<String>();
		entry.add("java");
		entry.add("-jar");
		entry.add("klab.node.jar");
		entry.add("-Xmx" + docker.getJvmMax() + "M");
		docker.getProperties().forEach((v,k) -> entry.add(v.toString()+"="+k.toString()));
		
		CreateContainerResponse container = dockerClient.createContainerCmd(docker.getImage())
				   .withExposedPorts(port)
				   .withHostConfig(newHostConfig().withPortBindings(portBindings))
				   .withName(docker.getNode().getNode()+"_from_Hub")
				   .withEntrypoint(entry)
				   .exec();
		
		dockerClient.startContainerCmd(container.getId()).exec();
		return new ResponseEntity<>(docker,HttpStatus.CREATED);
	}

}
