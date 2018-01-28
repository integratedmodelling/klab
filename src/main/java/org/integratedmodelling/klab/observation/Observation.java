package org.integratedmodelling.klab.observation;

import java.util.Optional;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.runtime.IContext;
import org.integratedmodelling.klab.utils.NameGenerator;

public abstract class Observation implements IObservation {

    private static final long    serialVersionUID = -7645502752899232235L;

    private IScale             scale;
    private IObservable        observable;
    private String             id               = NameGenerator.shortUUID();
    private ISubject           observer;
    private IDirectObservation contextObservation;
    private IContext           context;

    protected Observation(IObservable observable, IScale scale, IContext context) {
        this.observable = observable;
        this.scale = scale;
        this.context = context;
    }
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public Optional<ISubject> getObserver() {
        return observer == null ? Optional.empty() : Optional.of(observer);
    }

    @Override
    public IObservable getObservable() {
        return observable;
    }

    @Override
    public IScale getScale() {
        return scale;
    }

    @Override
    public IDirectObservation getContextObservation() {
        return contextObservation;
    }

    @Override
    public IContext getContext() {
        return context;
    }

    @Override
    public boolean isSpatiallyDistributed() {
        return scale.isSpatiallyDistributed();
    }

    @Override
    public boolean isTemporallyDistributed() {
        return scale.isTemporallyDistributed();
    }

    @Override
    public boolean isTemporal() {
        return scale.getTime() != null;
    }

    @Override
    public boolean isSpatial() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ISpace getSpace() {
        // TODO Auto-generated method stub
        return null;
    }

}
