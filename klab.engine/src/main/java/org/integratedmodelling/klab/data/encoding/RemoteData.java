package org.integratedmodelling.klab.data.encoding;

import java.util.List;

import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

/**
 * A data object initialized with a protobuf object encoded by a remote builder
 * and obtained through a REST call.
 * 
 * @author ferdinando.villa
 *
 */
public class RemoteData implements IKlabData {

	KlabData data;
	
	public RemoteData(KlabData data, IRuntimeScope context) {
		this.data = data;
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

	@Override
	public IArtifact getArtifact() {
		// TODO Auto-generated method stub
		return null;
	}

}
