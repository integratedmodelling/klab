package org.integratedmodelling.klab.data;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IObservable;

public class ObjectData extends ObservationData implements IObjectData {

    String name;
    Map<String, IObservationData> catalog = new HashMap<>();
    
    public ObjectData(String name, IObservable semantics) {
        super(semantics);
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public IObservationData get(String name) {
        return catalog.get(name);
    }

}
