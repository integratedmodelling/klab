package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;

import org.integratedmodelling.klab.api.lang.ActionTrigger;
import org.integratedmodelling.klab.api.lang.KContextualizable;
import org.integratedmodelling.klab.api.lang.kim.KKimAction;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;

public class KimAction extends KimStatement implements KKimAction {

    private static final long serialVersionUID = -6208591665170784114L;

    private Type type;
    private ActionTrigger trigger;
    private String targetStateId;
    private List<KKimConcept> triggeringEvents;
    private List<KContextualizable> computation;

    @Override
    public KKimAction.Type getType() {
        return this.type;
    }

    @Override
    public ActionTrigger getTrigger() {
        return this.trigger;
    }

    @Override
    public String getTargetStateId() {
        return this.targetStateId;
    }

    @Override
    public List<KKimConcept> getTriggeringEvents() {
        return this.triggeringEvents;
    }

    @Override
    public List<KContextualizable> getComputation() {
        return this.computation;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTrigger(ActionTrigger trigger) {
        this.trigger = trigger;
    }

    public void setTargetStateId(String targetStateId) {
        this.targetStateId = targetStateId;
    }

    public void setTriggeringEvents(List<KKimConcept> triggeringEvents) {
        this.triggeringEvents = triggeringEvents;
    }

    public void setComputation(List<KContextualizable> computation) {
        this.computation = computation;
    }

}
