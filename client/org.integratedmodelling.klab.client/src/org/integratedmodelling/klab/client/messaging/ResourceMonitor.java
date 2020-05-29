package org.integratedmodelling.klab.client.messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.ITicket.Status;
import org.integratedmodelling.klab.api.runtime.ITicket.Type;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Collect client tickets and responses from engines to keep a catalog of the
 * resources we published and we attempted to publish.
 * 
 * @author Ferd
 *
 */
public class ResourceMonitor {

	public final static String URN_FIELD = "urn";
	public final static String LOCAL_URN_FIELD = "localurn";
	public final static String USER_FIELD = "user";
	public final static String DATE_POSTED_FIELD = "date"; // java Date
	public final static String MESSAGE_FIELD = "message";
	public final static String DATE_RESOLVED_FIELD = "date"; // java Date, may be null
	public final static String TICKET_FIELD = "ticket"; // this is the node ticket when resolved
	public final static String NODE_FIELD = "node";
	public final static String STATUS_FIELD = "status"; // current ITicket.Status enum

	/**
	 * For each local resource which we attempted to publish, keep the resource data
	 * with node, ticket ID, correspondent public resource ID, submitting user and
	 * date. Indexed by local resource ID.
	 */
	Map<String, IParameters<String>> publishResources = Collections.synchronizedMap(new HashMap<>());

	public synchronized void add(ITicket ticket) {

		if (ticket.getType() == Type.ResourceSubmission) {

			// remote urn after node fixes
			String urn = ticket.getData().get(URN_FIELD);
			String node = ticket.getData().get(NODE_FIELD);
			String local = ticket.getData().get("resource");
			String tid = ticket.getData().get(TICKET_FIELD);
			Status status = ticket.getStatus();
			String message = ticket.getStatusMessage();
			Date timePosted = ticket.getPostDate();
			Date timeResolved = ticket.getResolutionDate();

			if (urn == null || local == null || status == null) {
				return;
			}

			/*
			 * compile the data
			 */
			Map<String, Object> data = new HashMap<>();

			data.put(URN_FIELD, urn);
			data.put(NODE_FIELD, node);
			data.put(TICKET_FIELD, tid);
			data.put(DATE_POSTED_FIELD, timePosted);
			data.put(STATUS_FIELD, status);
			data.put(LOCAL_URN_FIELD, local);
			if (timeResolved != null) {
				data.put(DATE_RESOLVED_FIELD, timeResolved);
			}
			if (message != null) {
				data.put(MESSAGE_FIELD, message);
			}

			publishResources.put(local, Parameters.wrap(data));
		}

	}

	/**
	 * Return the status for the latest publication attempt for the passed local
	 * resource, or null if publication was never attempted. If not null, the return
	 * value is the resource data containing all the fields above.
	 * 
	 * @param localResourceUrn
	 * @return
	 */
	public synchronized IParameters<String> getStatus(String localResourceUrn) {
		return publishResources.get(localResourceUrn);
	}

	/**
	 * Return all the resources published on this platform, including pending and
	 * failed (check the status field in the map). Index the resource by their 
	 * public URNs.
	 * 
	 * @return
	 */
	public List<Pair<String, IParameters<String>>> getPublished() {
		List<Pair<String, IParameters<String>>> ret = new ArrayList<>();
		for (IParameters<String> data : publishResources.values()) {
			ret.add(new Pair<>(data.get(URN_FIELD, String.class), data));
		}
		Collections.sort(ret, new Comparator<Pair<String, IParameters<String>>>() {
			@Override
			public int compare(Pair<String, IParameters<String>> o1, Pair<String, IParameters<String>> o2) {
				return o1.getFirst().compareTo(o2.getFirst());
			}
		});
		return ret;
	}

}
