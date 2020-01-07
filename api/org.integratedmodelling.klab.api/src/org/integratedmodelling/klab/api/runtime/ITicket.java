package org.integratedmodelling.klab.api.runtime;

import java.util.Date;
import java.util.Map;

/**
 * A persistent task description with a status, managed by a
 * {@link ITicketManager}. Each ticket has a type, a status, dates of posting
 * and resolution, and a set of parameters.
 * 
 * @author Ferd
 *
 */
public interface ITicket {

	enum Type {
		ResourceSubmission,
	}

	enum Status {
		OPEN, RESOLVED, ERROR
	}
	
	/**
	 * Refresh the ticket from the manager after changes.
	 * 
	 */
	void refresh();
	
	/**
	 * Delete the ticket from the manager.
	 * 
	 */
	void delete();
	
	/**
	 * Resolve the ticket and update in the manager.
	 * 
	 */
	void resolve();
	
	/**
	 * Resolve the ticket as error and update in the manager.
	 */
	void error(String status);

	/**
	 * Ticket has a permanent, unique ID. In the ticket manager, it's assigned
	 * explicitly.
	 * 
	 * @return
	 */
	String getId();

	/**
	 * Immutable post date.
	 * 
	 * @return
	 */
	Date getPostDate();

	/**
	 * Date of resolution (closing or error), or null. May go back to null if ticket
	 * is reopened. For now, no history is kept.
	 * 
	 * @return
	 */
	Date getResolutionDate();

	/**
	 * Ticket status.
	 * 
	 * @return
	 */
	Status getStatus();

	/**
	 * Ticket type.
	 * 
	 * @return
	 */
	Type getType();

	/**
	 * Arbitrary data. All strings for good behavior across network endpoints.
	 * 
	 * @return
	 */
	Map<String, String> getData();

	/**
	 * If the ticket status was caused by a condition or exception, or if any
	 * warning occurred, the status message should return an explanatory message.
	 * Otherwise it should return an empty string.
	 * 
	 * @return
	 */
	String getStatusMessage();

	/**
	 * Update and save.
	 */
	void update(Object... objects);

}
