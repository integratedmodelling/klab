package org.integratedmodelling.klab.data.storage;

import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.scale.Scale;

public class BooleanSingletonStorage extends AbstractSingletonStorage<Boolean> {

    public BooleanSingletonStorage(IObservable observable, Scale scale) {
        super(observable, scale);
    }

    @Override
    protected Boolean setValue(Object value) {
        return (Boolean) value;
    }

    @Override
    public Type getType() {
        return Type.BOOLEAN;
    }

    @Override
    public IDataKey getDataKey() {
        return null;
    }

}
