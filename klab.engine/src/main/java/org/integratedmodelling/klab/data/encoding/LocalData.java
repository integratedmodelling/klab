package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;

public class LocalData implements IKlabData {

	IState state;
	IDirectObservation object;
	List<INotification> notifications = new ArrayList<>();
	
	public LocalData(IArtifact artifact) {
		if (artifact instanceof IState) {
			this.state = (IState)artifact;
		} else if (artifact instanceof IDirectObservation) {
			this.object = (IDirectObservation)artifact;
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
		// TODO Auto-generated method stub
		return false;
	}

	
}
