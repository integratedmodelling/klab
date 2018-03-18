package org.integratedmodelling.klab.data;

import java.util.HashMap;
import java.util.Map;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;

public class ObjectData extends ObservationData implements IObjectData {

  String name;
  Map<String, IObservationData> data = new HashMap<>();
  ILocator locator;

  public static IObjectData create(String name, IObservable semantics) {
    return new ObjectData(name, semantics);
  }

  public ObjectData(String name, IObservable semantics) {
    super(semantics, null);
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public IObservationData get(String name) {
    return data.get(name);
  }


}
