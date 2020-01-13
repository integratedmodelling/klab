package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.ITicketManager;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements ITicketManager {

	ITicketManager delegate = new TicketManager(
			new File(Configuration.INSTANCE.getDataPath() + File.separator + "ntickets.json"));

	@Override
	public ITicket open(Object... data) {
		return delegate.open(data);
	}

	@Override
	public Collection<ITicket> get(Object... selectors) {
		return delegate.get(selectors);
	}

	@Override
	public ITicket getTicket(String id) {
		return delegate.getTicket(id);
	}

	@Override
	public void remove(Object... selectors) {
		delegate.remove(selectors);
	}

	@Override
	public Collection<ITicket> getResolvedAfter(long l) {
		return delegate.getResolvedAfter(l);
	}

}
