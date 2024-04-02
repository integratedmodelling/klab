package org.integratedmodelling.klab.node.controllers;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
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
		return t == null ? null : TicketManager.encode(t);
	}
	
	/**
	 * TODO secure to ADMIN_ROLE when admins have it
	 */
	@GetMapping(API.TICKET.LIST)
	@ResponseBody
	public List<TicketResponse.Ticket> listTickets() {
		List<TicketResponse.Ticket> ret = new ArrayList<>();
		for (ITicket t : ticketService.getTickets()) {
			ret.add(TicketManager.encode(t));
		}
		return ret;
	}

	@PostMapping(API.TICKET.QUERY)
	@ResponseBody
	public TicketResponse queryTickets(@RequestBody TicketRequest request) {
		// TODO
		return null;
	}


}
