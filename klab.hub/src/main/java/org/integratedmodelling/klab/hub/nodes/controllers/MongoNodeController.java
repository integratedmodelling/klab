package org.integratedmodelling.klab.hub.nodes.controllers;

import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
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

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/v2/nodes")
public class MongoNodeController {
	
	private NodeService nodeService;
	
	@Autowired
	MongoNodeController(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	
	@GetMapping(value = "", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<?> getNodes() {
		JSONObject resp = new JSONObject();
		resp.appendField("nodes", nodeService.getAll());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{nodeName}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> updateNode(@PathVariable("nodeName") String nodeName, @RequestBody MongoNode node) {
		if(nodeName.equals(node.getName())) {
			nodeService.update(node);	
		} else {
			throw new BadRequestException("Node name does not match url");
		}
		return new ResponseEntity<>("The group has been updated successsfully", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{nodeName}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> deleteNode(@PathVariable("nodeName") String nodeName) {
		nodeService.delete(nodeName);
		return new ResponseEntity<>("The Node has been deleted successsfully", HttpStatus.OK);
	}
	
	@GetMapping(value= "/{nodeName}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<Object> getNode(@PathVariable("nodeName") String nodeName) {
		JSONObject resp = new JSONObject();
		resp.appendField("node", nodeService.getByName(nodeName));
		return new ResponseEntity<>(resp, HttpStatus.OK);		
	}
	
	@PostMapping(value="", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> createNode(@RequestBody MongoNode node) {
		node = nodeService.create(node);
		return new ResponseEntity<>(node, HttpStatus.CREATED);
	}

}
