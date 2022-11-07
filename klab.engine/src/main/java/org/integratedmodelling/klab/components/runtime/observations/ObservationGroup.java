package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.observations.IPattern;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Range;

/**
 * The initial artifact for an instantiated group of objects. Starts empty but with an observable.
 * Can be sorted according to comparators installed by contextualizers. The sorting can be made
 * subjective.
 * 
 * @author ferdinando.villa
 */
public class ObservationGroup extends CountableObservation implements IObservationGroup {

    private IArtifact.Type atype;
    private List<IArtifact> artifacts = new ArrayList<>();
    boolean sorted = false;
    private Comparator<IArtifact> comparator = null;
    private Map<Collection<IConcept>, Map<IConcept, Boolean>> predicateMatchCache = Collections.synchronizedMap(new HashMap<>());

    /**
     * Only set in temporal views of event groups.
     */
    Range temporalHorizon = null;
    private List<IArtifact> currentArtifacts = null;

    protected ObservationGroup(ObservationGroup other) {
        super(other);
        this.atype = other.atype;
        this.artifacts = other.artifacts;
        this.sorted = other.sorted;
        this.comparator = other.comparator;
    }

    public ObservationGroup(Observable observable, Scale scale, IRuntimeScope context, IArtifact.Type type) {
        super(observable.getName(), observable, scale, context);
        this.atype = type;
        IIdentity identity = context.getMonitor().getIdentity();
//        if (identity instanceof AbstractTask) {
//            setGenerator(((AbstractTask< ? >) identity).getActivity());
//        }
    }

    @Override
    public DirectObservation at(ILocator locator) {

        if (locator instanceof ITime && getObservable().is(IKimConcept.Type.EVENT)) {
            ObservationGroup ret = new ObservationGroup(this);
            ret.temporalHorizon = Range.create(((ITime) locator).getStart().getMilliseconds(),
                    ((ITime) locator).getEnd().getMilliseconds());
            ret.subset();
            return ret;
        }

        /*
         * every other situation should be continuants, so no rescaling (although if we eventually
         * respond to at() in continuants, we may need to lazily apply it to the entire group).
         */

        return this;
    }

    private void subset() {
        currentArtifacts = new ArrayList<>();
        for (IArtifact artifact : artifacts) {
            // TODO
            Range erange = Range.create(((IEvent) artifact).getScale().getTime().getStart().getMilliseconds(),
                    ((IEvent) artifact).getScale().getTime().getEnd().getMilliseconds());
            if (erange.overlaps(temporalHorizon)) {
                currentArtifacts.add(((Event) artifact).locate(erange.intersection(temporalHorizon)));
            }
        }
    }

    @Override
    public IArtifact.Type getType() {
        return atype;
    }

    @Override
    public boolean isEmpty() {
        return artifacts.isEmpty();
    }

    @Override
    public Iterator<IArtifact> iterator() {
        if (currentArtifacts != null) {
            return currentArtifacts.iterator();
        }
        sort();
        return artifacts.iterator();
    }

    private void sort() {
        if (comparator != null && !sorted) {
            artifacts.sort(comparator);
            sorted = true;
        }
    }

    @Override
    public int groupSize() {
        return artifacts.size();
    }

    @Override
    public IArtifact getGroupMember(int n) {
        return artifacts.size() > n ? artifacts.get(n) : null;
    }

    @Override
    public void chain(IArtifact data) {
        chain(data, false);
    }

    public void chain(IArtifact data, boolean notify) {
        artifacts.add(data);
        ((Observation) data).setGroup(this);
        sorted = false;
        if (notify) {
            ObservationChange change = requireStructureChangeEvent();
            change.setTimestamp(-1);
            change.setNewSize(this.groupSize());
        }
    }

    public void setComparator(Comparator<IArtifact> comparator) {
        this.comparator = comparator;
    }

//    @Override
    public void setObserver(IObserver observer) {
        this.observer = observer;
    }
    
    /**
     * Return a group view that checks the type of every observation for having all the passed
     * predicates. Heavily cached for speed. Cache is kept in the main group so more views can reuse
     * it.
     * 
     * @param predicates
     * @return
     */
    public IObservationGroup queryPredicates(final Collection<IConcept> predicates) {

        return new ObservationGroupView(getObservable(), this, (artifact) -> {
            IConcept type = ((IObservation) artifact).getObservable().getType();
            Map<IConcept, Boolean> cache = predicateMatchCache.get(predicates);
            if (cache == null) {
                cache = Collections.synchronizedMap(new HashMap<>());
                predicateMatchCache.put(predicates, cache);
            }
            Boolean ret = cache.get(type);
            if (ret == null) {
                Collection<IConcept> attrs = Traits.INSTANCE.getAttributes(type);
                ret = true;
                for (IConcept predicate : predicates) {
                    boolean hasIt = false;
                    for (IConcept attribute : attrs) {
                        if (((RuntimeScope) getScope()).cached_is(attribute, predicate)) {
                            hasIt = true;
                            break;
                        }
                    }
                    if (!hasIt) {
                        ret = false;
                        break;
                    }
                }
                cache.put(type, ret);
            }
            return ret;
        });
    }
}
