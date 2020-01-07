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
	 * Create a new ticket with an arbitrary unique ID. The parameters define the
	 * ticket (type, status and key/pair data).
	 * 
	 * @param objects
	 */
	ITicket create(Object... objects);

	/**
	 * Open a new ticket. Must create it first.
	 * 
	 * @param ticket
	 */
	void open(ITicket ticket);

	/**
	 * 
	 * @param ticket
	 * @param status
	 */
	void resolve(ITicket ticket, ITicket.Status status);

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

}
