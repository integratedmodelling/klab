package org.integratedmodelling.klab.observation.states;

import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;

public class Storage<T> implements IStorage<T> {

    @Override
    public T get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void set(int index, Object value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void set(Object data, Locator... locators) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isDynamic() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getMin() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getMax() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isProbabilistic() {
        // TODO Auto-generated method stub
        return false;
    }

}
