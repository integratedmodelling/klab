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
		OPEN, CLOSED, ERROR
	}

	String getId();

	Date getPostDate();

	/**
	 * Date of resolution (closing or error), or null.
	 * 
	 * @return
	 */
	Date getResolutionDate();

	Status getStatus();

	Type getType();

	Map<String, String> getData();

	/**
	 * If the ticket status was caused by a condition or exception, or if any
	 * warning occurred, the status message should return an explanatory message.
	 * Otherwise it should return an empty string.
	 * 
	 * @return
	 */
	String getStatusMessage();

}
