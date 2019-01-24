package org.integratedmodelling.mca.model;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.mca.api.ICriterion;

public class Criterion implements ICriterion {

	IObservable observable;
	IState state;
	Type type;
	DataType dataType;
	
	public Criterion(IState state, Type type) {
		this.state = state;
		this.observable = state.getObservable();
		this.type = type;
		setDataType();
	}

	public Criterion(IObservable observable, Type type) {
		this.observable = observable;
		this.type = type;
		setDataType();
	}

	private void setDataType() {
		switch(observable.getArtifactType()) {
		case BOOLEAN:
			this.dataType = DataType.BINARY;
			break;
		case CONCEPT:
			if (!this.observable.is(IKimConcept.Type.ORDERING)) {
				throw new KlabValidationException("categorical data are only admitted in MCA assessments if they are orderings");
			}
			this.dataType = DataType.ORDINAL;
			break;
		case NUMBER:
			this.dataType = DataType.RATIO;
			break;
		default:
			break;
		
		}
	}

	@Override
	public String getName() {
		return observable.getLocalName();
	}
	
	@Override
	public IObservable getObservable() {
		return observable;
	}

	@Override
	public boolean isDistributed() {
		return state != null && state.getScale().isSpatiallyDistributed();
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public IState getState() {
		return state;
	}

	@Override
	public DataType getDataType() {
		return dataType;
	}

}
