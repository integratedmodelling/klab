package org.integratedmodelling.klab.hub.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.hub.manager.KlabNodeManager;
import org.integratedmodelling.klab.hub.nodes.KlabNode;
import org.integratedmodelling.klab.hub.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/nodes")
@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
public class KNodeController {
	@Autowired
	KlabNodeManager klabNodeManager;
	
	@Autowired
	LicenseService licenseManager;
	
	@GetMapping(value = "")
	public ResponseEntity<Object> getNodes() {
		JSONObject resp = new JSONObject();
		resp.appendField("Nodes", klabNodeManager.getNodes());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@GetMapping(value = "", params="names")
	public ResponseEntity<Object> getNodeNames() {
		JSONObject resp = new JSONObject();
		resp.appendField("Node Names", klabNodeManager.getNodeNames());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateNode(@PathVariable("id") String id, @RequestBody KlabNode node) {
		klabNodeManager.updateNodeGroups(id, node);
		return new ResponseEntity<>("The node has been updated successsfully", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteNode(@PathVariable("id") String id) {
		klabNodeManager.deleteNode(id);
		return new ResponseEntity<>("The Node has been deleted successsfully", HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getNode(@PathVariable("id") String id) {
		KlabNode node = klabNodeManager.getNode(id);
		return new ResponseEntity<>(node, HttpStatus.OK);		
	}

	@GetMapping(value= "/{id}", params = "certificate")
	public void generateNodeCertFile(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		KlabNode node = klabNodeManager.getNode(id);
		byte[] certFileContent = licenseManager.generateCert(node);
		String certFileString = String.format("attachment; filename=%s", licenseManager.get_NODE_CERT_FILE_NAME());
		response.setHeader("Content-disposition", certFileString);
		response.setContentType("text/plain;charset=utf-8");
		response.setContentLength(certFileContent.length);
		IOUtils.copy(new ByteArrayInputStream(certFileContent), response.getOutputStream());
		response.flushBuffer();
		IOUtils.closeQuietly(response.getOutputStream());
	}
	
	@PostMapping(value = "", produces = "application/json")
	public ResponseEntity<Object> createNode(@RequestBody KlabNode node) {
		klabNodeManager.createNode(node.getNode(), node);
		return new ResponseEntity<>(node, HttpStatus.CREATED);
	}
}
