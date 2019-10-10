package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.kim.api.IKimActiveStatement;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.Action;
import org.integratedmodelling.kim.kim.ActionSpecification;
import org.integratedmodelling.kim.kim.ValueAssignment;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;

public class KimAction extends KimStatement implements IKimAction {

  private static final long serialVersionUID = 2207705809739594438L;

  Type type;
  Trigger trigger;
  String targetStateId;
  List<IContextualizable> computation = new ArrayList<>();
  String language;
  List<IKimConcept> triggeringEvents = new ArrayList<>();

  public KimAction(ActionSpecification statement, IKimStatement parent) {
    super(statement, parent);
  }

  public KimAction(KimAction action) {
    // just copy the trigger and events
    this.trigger = action.trigger;
    this.triggeringEvents.addAll(action.triggeringEvents);
  }

  public void set(Action statement, Mode resolutionMode) {

    if (statement.isAway()) {
      this.type = Type.DESTROY;
    } else if (statement.isDo()) {
      this.type = Type.DO;
    } else if (statement.isIntegrate()) {
      this.type = Type.INTEGRATE;
    } else if (statement.isMove()) {
      this.type = Type.MOVE;
    } else if (statement.isSet()) {
      this.type = Type.SET;
    }

    ComputableResource condition = null;
    if (statement.getCondition() != null) {
      condition = new ComputableResource(statement.getCondition(), getParent());
      if (statement.isConditionNegative()) {
        condition.setNegated(true);
      }
    }

    for (ValueAssignment vass : statement.getAssignments()) {
      this.computation.add(new ComputableResource(vass, condition, resolutionMode, getParent(), this.trigger));
    }
    for (ValueAssignment vass : statement.getExecuted()) {
        this.computation.add(new ComputableResource(vass, condition, resolutionMode, getParent(), this.trigger));
      }

  }

  @Override
  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public Trigger getTrigger() {
    return trigger;
  }

  public void setTrigger(Trigger trigger) {
    this.trigger = trigger;
  }

  @Override
  public String getTargetStateId() {
    return targetStateId;
  }

  public void setTargetStateId(String targetStateId) {
    this.targetStateId = targetStateId;
  }

  @Override
  public List<IKimConcept> getTriggeringEvents() {
    return triggeringEvents;
  }

  public void setTriggeringEvents(List<IKimConcept> triggeringEvents) {
    this.triggeringEvents = triggeringEvents;
  }

  public Collection<KimNotification> validate(IKimActiveStatement context) {
    List<KimNotification> ret = new ArrayList<>();
    // TODO check appropriateness of use of all actions
    return ret;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public List<IContextualizable> getComputation() {
    return computation;
  }


}
