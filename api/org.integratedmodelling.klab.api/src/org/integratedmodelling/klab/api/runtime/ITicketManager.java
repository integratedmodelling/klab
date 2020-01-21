package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;

/**
 * Simple, persistent ticket manager. Used to coordinate multi-actor operations
 * such as resource publishing. A ticket manager is exposed by the runtime
 * system in the engine and is used by nodes (and probably hubs).
 * 
 * @author Ferd
 *
 */
public interface ITicketManager {

	/**
	 * Open a new ticket.
	 * 
	 * @param ticket
	 */
	ITicket open(Object... data);
	

	/**
	 * Return the ticket(s) correspondent to the passed selectors, which can be
	 * string IDs or any value(s) of the enums that describe a ticket.
	 * 
	 * @param selectors
	 * @return
	 */
	Collection<ITicket> get(Object... selectors);

	/**
	 * Get a specific ticket, or null.
	 * 
	 * @param id
	 * @return
	 */
	ITicket getTicket(String id);

	/**
	 * Remove all tickets matching selectors
	 */
	void remove(Object... selectors);

	/**
	 * Get all tickets resolved after the passed time.
	 * @param l
	 * @return
	 */
	Collection<ITicket> getResolvedAfter(long l);

}
