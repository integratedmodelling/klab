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
import org.integratedmodelling.klab.api.runtime.ITicketManager;
import org.integratedmodelling.klab.client.utils.FileCatalog;
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

}
