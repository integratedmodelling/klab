package org.integratedmodelling.klab.data.encoding;

import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

/**
 * A builder that encodes the data into a Protobuf object which will be sent
 * over the network for reconstruction by a matching {@link IKlabData} object at the
 * client end. The build() step is normally not called here, but uses the
 * {@link RemoteData} class for completeness and testing.
 * 
 * @author ferdinando.villa
 *
 */
public class EncodingDataBuilder implements IKlabData.Builder {

	IContextualizationScope context;

	public EncodingDataBuilder(IContextualizationScope context) {
		this.context = context;
	}

	@Override
	public Builder startState(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder finishState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder startObject(String artifactName, String objectName, IScale scale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder finishObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder withMetadata(String property, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder addNotification(INotification notification) {
		// TODO Auto-generated method stub
		return null;
	}

	public KlabData buildEncoded() {
		return null;
	}

	@Override
	public IKlabData build() {
		return new RemoteData(buildEncoded(), (IRuntimeScope) context);
	}

	@Override
	public void add(Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Object value, long offset) {
		// TODO Auto-generated method stub
		
	}
}
