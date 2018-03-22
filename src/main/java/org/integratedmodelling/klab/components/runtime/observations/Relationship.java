package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class Relationship extends CountableObservation implements IRelationship {

  Subject source;
  Subject target;

  private Relationship(String name, Observable observable, Scale scale, RuntimeContext context) {
    super(name, observable, scale, context);
  }

  private static final long serialVersionUID = -3655402547302227307L;

  public static Relationship create(String name, IObservable observable, IScale scale, IObjectArtifact data,
      ISubject context, ISubject source, ISubject target, IMonitor monitor) {
    return null;
  }

  @Override
  public Subject getSource() {
    return source;
  }

  @Override
  public Subject getTarget() {
    return target;
  }

  @Override
  public Relationship next() {
    return (Relationship)super.next();
  }

}
