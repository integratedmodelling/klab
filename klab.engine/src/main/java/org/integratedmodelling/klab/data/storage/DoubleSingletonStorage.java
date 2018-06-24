package org.integratedmodelling.klab.data.storage;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.scale.Scale;

public class DoubleSingletonStorage extends AbstractSingletonStorage<Double> {

	public DoubleSingletonStorage(IObservable observable, Scale scale) {
		super(observable, scale);
	}

	@Override
	protected Double setValue(Object value) {
		// TODO Auto-generated method stub
		return value instanceof Number ? ((Number) value).doubleValue() : convert(value);
	}

	private double convert(Object value) {
		// TODO distribution, promotion
		return Double.NaN;
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

}
