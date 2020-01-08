package org.integratedmodelling.klab.node.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.node.resources.TicketService;
import org.integratedmodelling.klab.rest.TicketRequest;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured(Role.ENGINE)
public class TicketController {

	@Autowired
	TicketService ticketService;
	
	@GetMapping(API.TICKET.INFO)
	@ResponseBody
	public TicketResponse.Ticket getTicketInfo(@PathVariable String ticket) {
		ITicket t = ticketService.getTicket(ticket);
		return getReference(t);
	}
	
	@PostMapping(API.TICKET.QUERY)
	@ResponseBody
	public TicketResponse queryTickets(@RequestBody TicketRequest request) {
		
		return null;
	}

	private TicketResponse.Ticket getReference(ITicket t) {
		TicketResponse.Ticket ret = new TicketResponse.Ticket();
		ret.setId(t.getId());
		ret.getData().putAll(t.getData());
		ret.setPostDate(t.getPostDate().getTime());
		ret.setResolutionDate(t.getResolutionDate() == null ? 0 : t.getResolutionDate().getTime());
		ret.setStatus(t.getStatus());
		ret.setType(t.getType());
		ret.setStatusMessage(t.getStatusMessage());
		return ret;
	}


}
