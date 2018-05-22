package org.integratedmodelling.klab.data.encoding;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;

/**
 * A builder that encodes the data into a protobuf object to send over the
 * network and reconstruct at the client end. The build() step is normally not
 * called here, but uses the {@link RemoteData} class for completeness and
 * testing.
 * 
 * @author ferdinando.villa
 *
 */
public class EncodingDataBuilder implements IKlabData.Builder {

	IComputationContext context;
	
	public EncodingDataBuilder(IComputationContext context) {
		this.context = context;
	}
	
	@Override
	public Builder startState(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(double doubleValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(float floatValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(int intValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(long longValue) {
		// TODO Auto-generated method stub

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
	public Builder setProperty(String property, Object object) {
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
		return new RemoteData(buildEncoded(), (IRuntimeContext) context);
	}

	@Override
	public void add(boolean booleanValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(IConcept conceptValue) {
		// TODO Auto-generated method stub
		
	}

}
