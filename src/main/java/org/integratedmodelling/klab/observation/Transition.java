package org.integratedmodelling.klab.observation;

import java.util.Collection;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;
import org.integratedmodelling.klab.utils.InstanceIdentifier;

public class Transition extends Scale implements ITransition {

  int                timeIndex = -1;
  InstanceIdentifier scaleIdentifier;

  static Transition initialization = new Transition();
  
  private Transition() {}

  public Transition(IScale scale, int timeOffset, boolean agentSurvives) {

    this.scaleIdentifier = ((Scale) scale).identifier;

    // /*
    // * should never get a null time, but you know.
    // */
    // period = (ITimePeriod) (scale.getTime() == null ? null :
    // scale.getTime().getExtent(timeOffset));
    // this.agentSurvives = scale.getTime() != null && scale.getTime().getMultiplicity() <
    // (timeOffset - 1);
    //
    // // prevents lots of calculations later.
    // this.timeIndex = timeOffset;
    //
    // /*
    // * recreate the scale using all extents and the agentState.getTimePeriod for time. Keep
    // * pointer to parent scale.
    // */
    // IExtent[] exts = new IExtent[scale.getExtentCount()];
    // int i = 0;
    // for (IExtent ext : scale) {
    // if (ext instanceof ITemporalExtent) {
    // exts[i++] = period;
    // } else {
    // exts[i++] = ext;
    // }
    // }
    // initialize(exts);
    //
    // this.scale = scale;
  }

  @Override
  public int getDimensionCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isAll() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public double getWeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public IConcept getExtent() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean agentSurvives() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isLast() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getTimeIndex() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Collection<IObservation> getModifiedObservations() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ITransition previous() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ITransition previous(Object locator) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ITransition next() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isInitialization() {
    return timeIndex < 0;
  }

  public static ITransition initialization() {
    return initialization;
  }

  public int hashCode() {
    long value = scaleIdentifier == null ? -1L : scaleIdentifier.getValue();
    value *= timeIndex;
    return (int)(value ^ (value >>> 32));
  }

  public boolean equals(Object o) {
    if (o instanceof Transition) {
      if (((Transition) o).timeIndex == -1 && timeIndex == -1) {
        return true;
      } else if (((Transition) o).scaleIdentifier != null && this.scaleIdentifier != null) {
        // they should come from the same scale
        return ((Transition) o).timeIndex == timeIndex
            && ((Transition) o).scaleIdentifier.equals(this.scaleIdentifier);
      }
    }
    return false;
  }

}
