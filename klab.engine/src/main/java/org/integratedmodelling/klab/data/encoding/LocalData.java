package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.rest.INotification;

public class LocalData implements IKlabData {

	IState state;
	IObservation object;
	List<INotification> notifications = new ArrayList<>();
	boolean error = false;

	public LocalData(LocalDataBuilder builder) {
		if (builder.state != null) {
			this.state = builder.state;
		}
		if (builder.observation != null) {
			this.object = builder.observation;
		}
		for (INotification notification : builder.notifications) {
			if (notification.getLevel().equals(Level.SEVERE.getName())) {
				error = true;
			}
			notifications.add(notification);
		}
	}

	/**
	 * Used after a remote resource/contextualize call. Passes the JSON map
	 * equivalent in structure to the protobuf message from a node.
	 * 
	 * @param data
	 * @param context
	 */
	public LocalData(Map<?, ?> data, IContextualizationScope context) {

		if (data.containsKey("states")) {
			for (Object s : (Iterable<?>) data.get("states")) {

				Map<?, ?> state = (Map<?, ?>) s;
				IState target = null;
				if ("result".equals(state.get("name"))) {
				
					target = context.getTargetArtifact() instanceof IState ? (IState) context.getTargetArtifact()
							: null;
					this.state = target;

				} else {
					IArtifact artifact = context.getArtifact(state.get("name").toString());
					if (artifact instanceof IState) {
						target = (IState) artifact;
					}
				}

				if (target == null) {
					throw new IllegalStateException("cannot establish state target for node resource");
				}

				Iterator<?> doubles = state.containsKey("doubledata")
						? ((Iterable<?>) state.get("doubledata")).iterator()
						: null;

				for (ILocator locator : target.getScale()) {
					if (doubles != null) {
						target.set(locator, doubles.next());
					}
				}

			}
		} else if (data.containsKey("objects")) {
			// TODO Auto-generated constructor stub
		}
	}

	@Override
	public List<INotification> getNotifications() {
		return notifications;
	}

	@Override
	public IArtifact getArtifact() {
		return state == null ? object : state;
	}

	@Override
	public boolean hasErrors() {
		return error;
	}

}
