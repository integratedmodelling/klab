package org.integratedmodelling.klab.model;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.KimModel;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.utils.Pair;

public class Model implements IModel {

    private static final long serialVersionUID = 6405594042208542702L;
    
    Model(KimModel model) {
        super(model);
    }
    
    @Override
    public List<IObservable> getObservables() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Pair<String, IObservable>> getAttributeObservables(boolean addInherency) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Pair<String, IProperty>> getAttributeMetadata() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLocalNameFor(IObservable observable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isResolved() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInstantiator() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isReinterpreter() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAvailable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IDocumentation getDocumentation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IKimStatement getStatement() {
        // TODO Auto-generated method stub
        return null;
    }

}
