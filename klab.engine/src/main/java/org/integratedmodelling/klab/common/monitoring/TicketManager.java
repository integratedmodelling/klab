package org.integratedmodelling.klab.common.monitoring;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.ITicket.Status;
import org.integratedmodelling.klab.api.runtime.ITicketManager;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.integratedmodelling.klab.utils.NameGenerator;

public class TicketManager implements ITicketManager {

	private Map<String, Ticket> catalog;

	private final static int REMOTE_TICKET_CHECKING_PERIOD_SECONDS = 30;

	/*
	 * TODO add periodic checking of all node tickets that are still open.
	 */
	Timer timer;

	public TicketManager(File file) {
		this.catalog = Collections
				.synchronizedMap(new FileCatalog<Ticket>(file, Ticket.class, Ticket.class).setSynchronization(true));
		this.timer = new Timer(true);
		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				checkRemoteTickets();
			}
		}, 5 * 1000, REMOTE_TICKET_CHECKING_PERIOD_SECONDS * 1000);
	}

	protected void checkRemoteTickets() {

		List<Ticket> remoteOpen = new ArrayList<>();
		for (Ticket ticket : this.catalog.values()) {
			if (ticket.getStatus() == Status.OPEN && ticket.getData().containsKey("node")) {
				remoteOpen.add(ticket);
			}
		}

		for (Ticket ticket : remoteOpen) {
			INodeIdentity node = Network.INSTANCE.getNode(ticket.getData().get("node"));
			if (node != null) {
				try {
					TicketResponse.Ticket response = node.getClient().get(API.TICKET.INFO, TicketResponse.Ticket.class);
					// use catalog.get() for synchronization
					if (response.getStatus() != Status.OPEN) {
						Logging.INSTANCE
								.info("ticket " + response.getId() + " status changed to " + response.getStatus());
						catalog.get(ticket.getId()).update(response);
					}
				} catch (Throwable e) {
					// do nothing
				}
			}
		}

	}

	@Override
	public Collection<ITicket> get(Object... selectors) {
		List<ITicket> ret = new ArrayList<>();
		Set<String> ids = findIds(selectors);
		for (String id : ids) {
			ret.add(catalog.get(id));
		}
		return ret;
	}

	private Set<String> findIds(Object[] selectors) {
		Set<String> ret = new HashSet<>();
		for (Ticket ticket : catalog.values()) {
			if (ticket.matches(selectors)) {
				ticket.manager = this;
				ret.add(ticket.getId());
			}
		}
		return ret;
	}

	@Override
	public ITicket getTicket(String id) {
		Ticket ret = catalog.get(id);
		ret.manager = this;
		return ret;
	}

	@Override
	public void remove(Object... selectors) {
		Set<String> ids = findIds(selectors);
		for (String id : ids) {
			catalog.remove(id);
		}
	}

	@Override
	public ITicket open(Object... objects) {
		Ticket ticket = Ticket.create(NameGenerator.shortUUID(), objects);
		catalog.put(ticket.getId(), ticket);
		ticket.manager = this;
		return ticket;
	}

	void put(Ticket ticket) {
		catalog.put(ticket.getId(), ticket);
	}

	public static TicketResponse.Ticket encode(ITicket t) {
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

	@Override
	public Collection<ITicket> getResolvedAfter(long l) {
		Set<ITicket> ret = new HashSet<>();
		for (Ticket ticket : catalog.values()) {
			if (ticket.getStatus() != Status.OPEN && ticket.getResolutionDate() != null
					&& ticket.getResolutionDate().getTime() >= l) {
				ticket.manager = this;
				ret.add(ticket);
			}
		}
		return ret;
	}

}
