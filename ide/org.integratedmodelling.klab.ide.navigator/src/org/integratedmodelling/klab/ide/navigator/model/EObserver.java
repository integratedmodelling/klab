package org.integratedmodelling.klab.ide.navigator.model;

import java.util.List;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimObserver;

public class EObserver extends EKimObject implements IKimObserver {

	private static final long serialVersionUID = -5755690442793814545L;

	IKimObserver delegate;
	
	EObserver(IKimObserver statement) {
		super(statement);
		this.delegate = statement;
	}
	
	public String getName() {
		return delegate.getName();
	}

	public IKimBehavior getBehavior() {
		return delegate.getBehavior();
	}

	public IKimObservable getObservable() {
		return delegate.getObservable();
	}

	public List<IKimAnnotation> getAnnotations() {
		return delegate.getAnnotations();
	}

	public List<IKimObservable> getStates() {
		return delegate.getStates();
	}

    @Override
    public String getDocstring() {
        return delegate.getDocstring();
    }

}
