package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState.Mediator;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.observations.scale.time.ITimePeriod;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.Extent;

public class Time extends Extent implements ITime {

    public Time() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getScaleRank() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IConcept getDomainConcept() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCovered(int stateIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IExtent merge(IExtent extent, boolean force) throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConsistent() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int[] getDimensionSizes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getDimensionOffsets(int linearOffset, boolean rowFirst) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int locate(Locator locator) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getMultiplicity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IExtent union(IExtent other) throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean contains(IExtent o) throws KlabException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean overlaps(IExtent o) throws KlabException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean intersects(IExtent o) throws KlabException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getCoveredExtent() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ITime getExtent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITime getExtent(int stateIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITimePeriod collapse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITime intersection(IExtent other) throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITimeInstant getStart() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITimeInstant getEnd() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITimeDuration getStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITransition getTransition(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Extent copy() {
        // TODO Auto-generated method stub
        return null;
    }

}
