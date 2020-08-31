package org.integratedmodelling.klab.client.tickets;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.ITicket.Status;
import org.integratedmodelling.klab.api.runtime.ITicketManager;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.utils.NameGenerator;

public class TicketManager implements ITicketManager {

	private Map<String, Ticket> catalog;

	public TicketManager(File file) {
		catalog = Collections
				.synchronizedMap(new FileCatalog<Ticket>(file, Ticket.class, Ticket.class).setSynchronization(true));
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
		if (selectors == null) {
			catalog.clear();
			return;
		}

		List<Object> nontickets = new ArrayList<>();
		for (Object o : selectors) {
			if (o instanceof ITicket) {
				catalog.remove(((ITicket)o).getId());
			} else {
				nontickets.add(o);
			}
		}

		if (nontickets.size() > 0) {
			Set<String> ids = findIds(nontickets.toArray());
			for (String id : ids) {
				catalog.remove(id);
			}
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

	/**
	 * Update the contents based on the passed message: add if not there, update status otherwise
	 * 
	 * @param ticket
	 */
	public void update(TicketResponse.Ticket ticket) {
		
		if (catalog.containsKey(ticket.getId())) {
			Ticket t = catalog.get(ticket.getId());
			t.update(ticket);
			catalog.put(ticket.getId(), t);
		} else {
			catalog.put(ticket.getId(), Ticket.create(ticket.getId(), ticket));
		}
	}

	@Override
	public List<ITicket> getTickets() {
		return new ArrayList<>(catalog.values());
	}

}
