package org.integratedmodelling.klab.hub.network.controllers;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.network.DockerHostConfig;
import org.integratedmodelling.klab.hub.repository.DockerHostConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/network/docker-config")
public class DockerHostController {
	
	private DockerHostConfigurationRepository repo;
	
	@Autowired
	public DockerHostController(DockerHostConfigurationRepository repo) {
		super();
		this.repo = repo;
	}
	
	@PostMapping(value="", produces= "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> createHostConfiguration(@RequestBody DockerHostConfig config) {
		config = repo.insert(config);
		return new ResponseEntity<>(config, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", produces= "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> updateHostConfiguration(@PathVariable String id, @RequestBody DockerHostConfig config) {
		repo.findById(id)
			.filter(c -> c.getId().equals(config.getId()))
			.orElseThrow(()-> new BadRequestException("No host configuiration by that id found"));
		repo.save(config);
		return new ResponseEntity<>(config, HttpStatus.CREATED);
	}

}
