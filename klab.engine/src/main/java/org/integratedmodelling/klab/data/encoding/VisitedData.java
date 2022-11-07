package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.components.runtime.artifacts.ObjectArtifact;
import org.integratedmodelling.klab.components.runtime.artifacts.StorageDataArtifact;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder.Descriptor;
import org.integratedmodelling.klab.scale.Scale;

public class VisitedData implements IKlabData {

	IDataArtifact state = null;
	ObjectArtifact object = null;
	Metadata metadata = new Metadata();
	List<INotification> notifications = new ArrayList<>();
	boolean errors = false;
	IConcept semantics;

	public VisitedData(VisitingDataBuilder builder) {

		for (Descriptor state : builder.states) {
			if (state.storage != null) {
				this.state = new StorageDataArtifact(state.storage);
			}
		}

		for (Descriptor object : builder.objects) {
			if (this.object == null) {
				this.object = new ObjectArtifact(object.name,
						object.scale instanceof IScale ? (IScale) object.scale : Scale.create(object.scale),
						object.metadata);
			} else {
				this.object.chain(new ObjectArtifact(object.name,
						object.scale instanceof IScale ? (IScale) object.scale : Scale.create(object.scale),
						object.metadata));
			}
		}

		for (INotification notification : builder.notifications) {
			if (notification.getLevel().equals(Level.SEVERE.toString())) {
				errors = true;
			}
			this.notifications.add(notification);
		}

	}

	@Override
	public IArtifact getArtifact() {
		return state == null ? object : state;
	}

	@Override
	public Type getArtifactType() {
		return state == null ? (object == null ? Type.VOID : object.getType()) : state.getType();
	}

	@Override
	public List<INotification> getNotifications() {
		return notifications;
	}

	@Override
	public int getObjectCount() {
		return object == null ? 0 : object.groupSize();
	}

	@Override
	public int getStateCount() {
		return state == null ? 0 : state.groupSize();
	}

	@Override
	public boolean hasErrors() {
		return errors;
	}

	@Override
	public IScale getObjectScale(int i) {
		return object == null ? null : (IScale) object.getGroupMember(i).getGeometry();
	}

	@Override
	public String getObjectName(int i) {
		return object == null ? null : ((ObjectArtifact) object.getGroupMember(i)).getName();
	}

	@Override
	public IMetadata getObjectMetadata(int i) {
		return object == null ? null : object.getGroupMember(i).getMetadata();
	}

	@Override
	public IConcept getSemantics() {
		return semantics;
	}

    @Override
    public IMetadata getMetadata() {
        return metadata;
    }

}
