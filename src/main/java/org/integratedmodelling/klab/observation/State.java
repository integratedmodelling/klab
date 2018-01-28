package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.IContext;

public class State extends Observation implements IState {

    private State(IObservable observable, IScale scale, IContext context) {
        super(observable, scale, context);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -7075415960868285693L;
    
    public static State create(String name, IObservable observable, ISubject context) {
        return null;
    }
    
    @Override
    public IStorage<?> getStorage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getValueCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ITime getTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getValue(Locator locator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConstant() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDynamic() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IState as(IObserver observer) {
        // TODO Auto-generated method stub
        return null;
    }

}
