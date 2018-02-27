package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimAction.Type;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;

class Action implements IAction {

  Trigger trigger;
  Type type;
  
  public Action(IKimAction action) {
    this.trigger = action.getTrigger();
    this.type = action.getType();
  }

  @Override
  public Trigger getTrigger() {
    return trigger;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public List<IServiceCall> getComputation(ITransition transition) {
    List<IServiceCall> ret = new ArrayList<>();
    return ret;
  }

}