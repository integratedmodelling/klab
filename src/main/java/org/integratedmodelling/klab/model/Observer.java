package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.owl.Observable;

public class Observer extends KimObject implements IObserver {

  private static final long serialVersionUID = 2777161073171784334L;

  private Observable        observable;
  private String            name;
  private Namespace         namespace;
  private Behavior          behavior;
  private List<IObservable> states           = new ArrayList<>();

  public Observer(IKimObserver statement, Namespace namespace, Monitor monitor) {
    super(statement);
    this.observable = Observables.INSTANCE.declare(statement.getObservable(), monitor);
    /*
     * resolving the observable for an acknowledged observation is always optional.
     */
    this.observable.setOptional(true);
    this.namespace = namespace;
    this.name = statement.getName();
    this.behavior = new Behavior(statement.getBehavior(), this);
  }

  public String toString() {
    return "[" + getName() + "]";
  }

  @Override
  public String getId() {
    return name;
  }

  @Override
  public String getName() {
    return namespace.getId() + "." + getId();
  }

  @Override
  public Namespace getNamespace() {
    return namespace;
  }

  @Override
  public Behavior getBehavior() {
    return behavior;
  }

  @Override
  public Observable getObservable() {
    return observable;
  }

  @Override
  public List<IObservable> getStates() {
    return states;
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Observer && ((Observer) obj).getName().equals(getName());
  }

  @Override
  public List<IServiceCall> getComputation(ITransition transition) {
    List<IServiceCall> ret = new ArrayList<>();
    for (Trigger trigger : Dataflows.INSTANCE.getActionTriggersFor(transition)) {
      for (IAction action : behavior.getActions(trigger)) {
        ret.addAll(action.getComputation(transition));
      }
    }
    return ret;
  }


}
