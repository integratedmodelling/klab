package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

public class State extends Observation implements IState {

    private State(Observable observable, Scale scale, IMonitor monitor) {
        super(observable, scale, monitor);
    }

    private static final long serialVersionUID = -7075415960868285693L;
    
    public static State create(String name, IObservable observable, ISubject context, IMonitor monitor) {
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
    public State as(IObservable observable) {
        // TODO Auto-generated method stub
        return null;
    }
   
    @Override
    public State next() {
      // TODO Auto-generated method stub
      return null;
    }
}
