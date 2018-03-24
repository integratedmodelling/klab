package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.observations.scale.time.ITimePeriod;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.Extent;
import org.integratedmodelling.klab.observation.Scale.Mediator;

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
    public long size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IExtent union(ITopologicallyComparable<?> other) throws KlabException {
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
    public ITimePeriod collapse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITime intersection(ITopologicallyComparable<?> other) throws KlabException {
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
    public Extent copy() {
        // TODO Auto-generated method stub
        // TODO REMEMBER TO ALSO COPY THE SCALE ID
        return null;
    }

    @Override
    public Time getExtent(long stateIndex) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public IServiceCall getKimSpecification() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public boolean isCovered(long stateIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long[] getDimensionSizes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] getDimensionOffsets(long linearOffset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getCoverage() {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public Type getType() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public boolean isRegular() {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public int getDimensionality() {
      // TODO Auto-generated method stub
      return 0;
    }

//    @Override
//    public long locate(Locator locator) {
//      // TODO Auto-generated method stub
//      return 0;
//    }

    @Override
    public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public ITime at(ILocator locator) {
      // TODO Auto-generated method stub
      return null;
    }

//    @Override
//    public long getOffset(ILocator index) {
//      // TODO Auto-generated method stub
//      return 0;
//    }

    @Override
    public long getOffset(long[] dimOffsets) {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public long[] shape() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public long getOffset(ILocator index) {
      // TODO Auto-generated method stub
      return 0;
    }

}
