package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.scale.Scale;

public class LocalData implements IKlabData {

	IState state;
	IObservation object;
	List<INotification> notifications = new ArrayList<>();
	boolean error = false;

	static Set<String> reservedFields = null;

	static {
		reservedFields = new HashSet<>();
		reservedFields.add("objects");
		reservedFields.add("geometry");
		reservedFields.add("name");
	}

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
						Object o = doubles.next();
						// yes, they do this, mixed in with doubles.
						if ("NaN".equals(o)) {
							o = null;
						}
						target.set(locator, o);
					}
				}

			}
		} else if (data.containsKey("objects")) {

			for (Object object : ((List<?>) data.get("objects"))) {

				Map<?, ?> obj = (Map<?, ?>) object;

				IScale scale = Scale.create(Geometry.create(obj.get("geometry").toString()));

				/*
				 * Gather states from model IDs and prepare to record them later.
				 */
				Set<String> interpreted = new HashSet<>();
				IObjectArtifact output = null;

				if (context.getTargetSemantics().is(Type.RELATIONSHIP)) {

					IDirectObservation source = null; // TODO
					IDirectObservation target = null; // TODO
					output = context.newRelationship(context.getTargetSemantics(), obj.get("name").toString(), scale,
							source, target, extractMetadata(obj, interpreted));
				} else {

					output = context.newObservation(context.getTargetSemantics(), obj.get("name").toString(), scale,
							extractMetadata(obj, interpreted));
				}

				if (this.object == null) {
					this.object = (IObservation) output;
				} else {
					if (!(this.object instanceof ObservationGroup)) {
						IObservation obs = this.object;
						this.object = new ObservationGroup((Observable) context.getTargetSemantics(),
								(Scale) context.getScale(), (IRuntimeScope) context,
								context.getTargetSemantics().getArtifactType());
						((ObservationGroup) this.object).chain(obs);
					}
					((Artifact) this.object).chain(output);
				}

			}

		}
	}

	private IMetadata extractMetadata(Map<?, ?> obj, Set<String> interpreted) {
		Metadata ret = new Metadata();
		for (Object k : obj.keySet()) {
			if (!reservedFields.contains(k.toString())
					&& !(interpreted != null && interpreted.contains(k.toString()))) {
				ret.put(k.toString(), obj.get(k));
			}
		}
		return ret;
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
