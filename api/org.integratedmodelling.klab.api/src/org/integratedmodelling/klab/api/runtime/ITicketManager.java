package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;

/**
 * Simple, persistent ticket manager. Used to coordinate multi-actor operations
 * such as resource publishing. A ticket manager is exposed by the runtime
 * system in the engine.
 * 
 * @author Ferd
 *
 */
public interface ITicketManager {

	void open(ITicket ticket);

	void resolve(ITicket ticket, ITicket.Status status);

	/**
	 * Return the ticket(s) correspondent to the passed selectors, which can be
	 * string IDs or any value(s) of the enums that describe a ticket.
	 * 
	 * @param selectors
	 * @return
	 */
	Collection<ITicket> get(Object... selectors);
	
	ITicket getTicket(String id);
	
	/**
	 * Remove all tickets matching selectors
	 */
	void remove(Object... selectors);

}
