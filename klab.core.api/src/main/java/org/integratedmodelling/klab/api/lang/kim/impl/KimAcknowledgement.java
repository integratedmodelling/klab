package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;

import org.integratedmodelling.klab.api.lang.kim.KKimAcknowledgement;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;

public class KimAcknowledgement extends KimActiveStatement implements KKimAcknowledgement {

    private static final long serialVersionUID = -2269601151635547580L;

    private String urn;
    private String name;
    private KKimObservable observable;
    private List<KKimObservable> states;
    private String docstring;

    @Override
    public String getUrn() {
        return this.urn;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public KKimObservable getObservable() {
        return this.observable;
    }

    @Override
    public List<KKimObservable> getStates() {
        return this.states;
    }

    @Override
    public String getDocstring() {
        return this.docstring;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setObservable(KKimObservable observable) {
        this.observable = observable;
    }

    public void setStates(List<KKimObservable> states) {
        this.states = states;
    }

    public void setDocstring(String docstring) {
        this.docstring = docstring;
    }

}
