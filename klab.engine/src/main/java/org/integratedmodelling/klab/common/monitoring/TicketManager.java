package org.integratedmodelling.klab.common.monitoring;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.ITicketManager;
import org.integratedmodelling.klab.utils.FileCatalog;

public class TicketManager extends FileCatalog<Ticket> implements ITicketManager {

	public TicketManager(File file) {
		super(file, Ticket.class, Ticket.class);
	}

	private static final long serialVersionUID = -5634357931229167291L;

	@Override
	public void open(ITicket ticket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resolve(ITicket ticket, ITicket.Status status) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Collection<ITicket> get(Object... selectors) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITicket getTicket(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Object... selectors) {
		// TODO Auto-generated method stub
		
	}

}
