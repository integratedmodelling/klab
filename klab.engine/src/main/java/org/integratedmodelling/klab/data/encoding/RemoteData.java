package org.integratedmodelling.klab.data.encoding;

import java.util.List;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;

/**
 * A data object builder that is initialized with a protobuf object
 * coming from a network response.
 * 
 * @author ferdinando.villa
 *
 */
public class RemoteData implements IKlabData {

	public RemoteData(KlabData data, IRuntimeContext context) {
		
	}

	@Override
	public List<IState> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDirectObservation> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<INotification> getNotifications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasErrors() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
