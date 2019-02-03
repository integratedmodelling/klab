package org.integratedmodelling.klab.data.storage;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.scale.Scale;

public class ConceptSingletonStorage extends AbstractSingletonStorage<IConcept> implements IKeyHolder {

	private IDataKey dataKey;

	public ConceptSingletonStorage(IObservable observable, Scale scale) {
		super(observable, scale);
	}

	@Override
	protected IConcept setValue(Object value) {
		return (IConcept) value;
	}

	@Override
	public Type getType() {
		return Type.CONCEPT;
	}

	@Override
	public IDataKey getDataKey() {
		return dataKey;
	}

	@Override
	public void setDataKey(IDataKey key) {
		this.dataKey = key;
	}
    
	@SuppressWarnings("unchecked")
    @Override
    public <T> T aggregate(IGeometry geometry, Class<? extends T> cls) {
        if (!IConcept.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("cannot return a concept state as a " + cls);
        }
        return (T)value;
    }

}
