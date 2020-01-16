package org.integratedmodelling.klab.hub.nodes.controllers;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/nodes")
public class MongoNodeController {
	
	@Autowired
	NodeService nodeService;
	
	@GetMapping(value = "", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINSTRATOR')")
	public ResponseEntity<?> getNodes() {
		return new ResponseEntity<>(nodeService.getNodes(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{nodeName}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> updateNode(@PathVariable("nodeName") String nodeName, @RequestBody MongoNode node) {
		if(nodeName.equals(node.getNode())) {
			nodeService.updateNode(node);	
		} else {
			throw new BadRequestException("Node name does not match url");
		}
		return new ResponseEntity<>("The group has been updated successsfully", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{nodeName}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> delete(@PathVariable("nodeName") String nodeName,  @RequestBody MongoNode node) {
		if(nodeName.equals(node.getNode())) {
			nodeService.deleteNode(node);	
		} else {
			throw new BadRequestException("Group name does not match name");
		}
		return new ResponseEntity<>("The Groups has been deleted successsfully", HttpStatus.OK);
	}
	
	@GetMapping(value= "/{nodeName}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<Object> getGroup(@PathVariable("nodeName") String nodeName) {
		MongoNode node = nodeService.getNode(nodeName);
		return new ResponseEntity<>(node, HttpStatus.OK);		
	}
	
	@PostMapping(value="", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> createGroup(@RequestBody MongoNode node) {
		node = nodeService.createNode(node);
		return new ResponseEntity<>(node, HttpStatus.CREATED);
	}

}
