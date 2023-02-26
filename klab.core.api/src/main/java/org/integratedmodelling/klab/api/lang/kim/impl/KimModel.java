package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.api.lang.KContextualizable;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimModel;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;

public class KimModel extends KimActiveStatement implements KKimModel {

    private static final long serialVersionUID = -6068429551009652469L;

    @Override
    public Optional<KKimConcept> getReinterpretingRole() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimObservable> getDependencies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimObservable> getObservables() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getResourceUrns() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isLearningModel() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInterpreter() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAbstract() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInactive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInstantiator() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Object> getInlineValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KContextualizable> getContextualization() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDocstring() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isSemantic() {
        // TODO Auto-generated method stub
        return false;
    }

}
