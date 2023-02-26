package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;

import org.integratedmodelling.klab.api.lang.ActionTrigger;
import org.integratedmodelling.klab.api.lang.KContextualizable;
import org.integratedmodelling.klab.api.lang.kim.KKimAction;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;

public class KimAction extends KimStatement implements KKimAction {

	private static final long serialVersionUID = -6208591665170784114L;

    @Override
    public KKimAction.Type getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ActionTrigger getTrigger() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTargetStateId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimConcept> getTriggeringEvents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KContextualizable> getComputation() {
        // TODO Auto-generated method stub
        return null;
    }
}
