package org.integratedmodelling.mca.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.IStakeholder;

public class Stakeholder implements IStakeholder {

    private boolean             objective;
    private String              name;
    private Map<String, Double> weights = new HashMap<>();
    private IObservable         observable;
    private ISubject  subject;

    public Stakeholder(String name) {
        this.name = name;
    }
    
    public Stakeholder(IObservable observable) {
        this.observable = observable;
    }

    public Stakeholder(ISubject subject) {
        this.subject = subject;
        this.name = subject.getName();
    }

    @Override
    public ISubject getSubject() {
        return subject;
    }

    @Override
    public List<IAlternative> getAlternatives(ILocator locator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void rankAlternatives(ILocator locator) throws KlabException {
        // TODO Auto-generated method stub

    }

    // @Override
    public Map<String, Double> getWeights() {
        return weights;
    }

    @Override
    public boolean canValue(IAlternative alternative) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isObjective() {
        return objective;
    }

    public void setObjective(boolean b) {
        this.objective = b;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setWeight(String criterion, double weight) {
        this.weights.put(criterion, weight);
    }

    public IObservable getObservable() {
        return this.observable;
    }

    public void setObservable(IObservable oobs) {
        this.observable = oobs;
    }

    public void setSubject(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public double getWeight(ICriterion criterion) {
        // TODO Auto-generated method stub
        return 0;
    }

}
