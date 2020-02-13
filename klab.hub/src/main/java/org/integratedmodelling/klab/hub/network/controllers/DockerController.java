package org.integratedmodelling.klab.hub.network.controllers;

import org.integratedmodelling.klab.hub.network.DockerConfiguration;
import org.integratedmodelling.klab.hub.repository.DockerConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

import java.util.ArrayList;
import java.util.List;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;

@RestController
@RequestMapping("/api/v2/network")
public class DockerController {

	private DockerConfigurationRepository repo;
	
	@Autowired
	DockerController(DockerConfigurationRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping(value = "node", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINSTRATOR')")
	public ResponseEntity<?> getNodes() {
		DockerConfiguration config = repo.findAll().get(0);
//			DockerClientConfig clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
//					  .withDockerHost(config.getDockerHost())
//					  .build();
//			
//			DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//					  .withReadTimeout(1000)
//					  .withConnectTimeout(1000)
//					  .withMaxTotalConnections(100)
//					  .withMaxPerRouteConnections(10);
//			
//			DockerClient dockerClient = DockerClientBuilder.getInstance(clientConfig)
//					  .withDockerCmdExecFactory(dockerCmdExecFactory)
//					  .build();
//			
//			
//			
//			ExposedPort port = ExposedPort.parse("8287");
//			Binding.bindPort(8287);
//			Ports portBindings = new Ports();
//			portBindings.bind(port, Binding.bindPort(8287));
//			List<String> entry = new ArrayList();
//			entry.add("ECHO java -jar");
//			entry.add("$NODE");
//			entry.add("-Dspring.config.location=$CONFIG");
//			entry.add("-cert $CERT");
//			entry.add("-Xmx2048M");
//			entry.add("-Dklab.node.submitting=FUCKERS");
//			
//			CreateContainerResponse container = dockerClient.createContainerCmd("node:un_development")
//					   .withExposedPorts(port)
//					   .withHostConfig(newHostConfig().withPortBindings(portBindings))
//					   .withName("node_hub")
//					   .withEntrypoint("java", "-jar", "klab.node.jar")
//					   .exec();
//			
//			dockerClient.startContainerCmd(container.getId()).exec();
		return null;
	}
	
	@GetMapping(value = "deployed-containers", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getRunningContainers() {
		
		return null;
		
	}
	
}
