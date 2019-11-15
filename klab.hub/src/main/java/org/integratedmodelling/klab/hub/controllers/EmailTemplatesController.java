package org.integratedmodelling.klab.hub.controllers;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;


@RestController
@RequestMapping("/api/groups")
@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
public class EmailTemplatesController {

	@Autowired
	KlabGroupService klabGroupService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Object> getGroups() {
		return new ResponseEntity<>(klabGroupService.getGroups(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, params="names")
	@PreAuthorize("hasRole('ROLE_ENGINE') or hasRole('ROLE_USER')")
	public ResponseEntity<Object> getGroupNames() {
		JSONObject resp = new JSONObject();
		resp.appendField("Groups", klabGroupService.getGroupNames());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateGroup(@PathVariable("id") String id, @RequestBody KlabGroup group) {
		klabGroupService.updateGroup(id, group);
		return new ResponseEntity<>("The group has been updated successsfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		klabGroupService.deleteGroup(id);
		return new ResponseEntity<>("The Groups has been deleted successsfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getGroup(@PathVariable("id") String id) {
		KlabGroup group = klabGroupService.getGroup(id).orElseThrow(() -> new BadRequestException("No group by that name found"));
		return new ResponseEntity<>(group, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> createGroup(@RequestBody KlabGroup group) {
		klabGroupService.createGroup(group.getId(), group);
		return new ResponseEntity<>(group, HttpStatus.CREATED);
	}
}
