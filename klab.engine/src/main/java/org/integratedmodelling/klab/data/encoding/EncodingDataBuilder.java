package org.integratedmodelling.klab.data.encoding;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;

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

	KlabData.Builder builder = KlabData.newBuilder();
	KlabData.State.Builder stateBuilder = null;
	KlabData.Object.Builder objectBuilder = null;
	EncodingDataBuilder parent = null;
	
	public EncodingDataBuilder() {
	}

	public EncodingDataBuilder(EncodingDataBuilder root) {
		this.parent = root;
		this.builder = root.builder;
		this.stateBuilder = root.stateBuilder;
		this.objectBuilder = root.objectBuilder;
	}

	@Override
	public Builder startState(String name) {
		EncodingDataBuilder ret = new EncodingDataBuilder(this);
		ret.stateBuilder = KlabData.State.newBuilder();
		ret.stateBuilder.setName(name);
		return ret;
	}

	@Override
	public Builder finishState() {
		if (this.parent.objectBuilder != null) {
			this.parent.objectBuilder.addStates(this.stateBuilder.build());
		} else {
			this.parent.builder.addStates(stateBuilder.build());
		}
		return this.parent;
	}

	@Override
	public Builder startObject(String artifactName, String objectName, IGeometry scale) {
		EncodingDataBuilder ret = new EncodingDataBuilder(this);
		ret.objectBuilder = KlabData.Object.newBuilder();
		ret.objectBuilder.setName(objectName);
		ret.objectBuilder.setGeometry(scale.encode());
		return ret;
	}

	@Override
	public Builder finishObject() {
		this.parent.builder.addObjects(this.objectBuilder.build());
		return this.parent;
	}

	@Override
	public Builder withMetadata(String property, Object object) {
		if (this.stateBuilder != null) {
			this.stateBuilder.putMetadata(property, object.toString());
		} else if (this.objectBuilder != null) {
			this.objectBuilder.putMetadata(property, object.toString());
		}
		return this;
	}

	@Override
	public Builder addNotification(INotification notification) {
		// TODO Auto-generated method stub
		return this;
	}

	public KlabData buildEncoded() {
		return builder.build();
	}

	@Override
	public IKlabData build() {
		throw new IllegalStateException("build() should not be called on an encoding builder");
	}

	@Override
	public void add(Object value) {
		if (this.stateBuilder != null) {
			if (value instanceof Number) {
				this.stateBuilder.addDoubledata(((Number)value).doubleValue());
			} else if (value instanceof Boolean) {
				this.stateBuilder.addBooleandata((Boolean)value);
			} else if (value instanceof String) {
				this.stateBuilder.addTabledata(getTableValue((String)value, this.builder));
			}
		}
	}

	private int getTableValue(String value, KlabData.Builder builder) {
		// TODO update lookup table 
		return 0;
	}

	@Override
	public void add(Object value, ILocator offset) {
		// TODO Auto-generated method stub
		
	}
}
