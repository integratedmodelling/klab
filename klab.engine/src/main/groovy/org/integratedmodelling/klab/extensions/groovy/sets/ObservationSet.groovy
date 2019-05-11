package org.integratedmodelling.klab.extensions.groovy.sets


import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation
import org.integratedmodelling.klab.exceptions.KlabValidationException
import org.integratedmodelling.klab.extensions.groovy.model.Concept
import org.integratedmodelling.klab.extensions.groovy.model.Observation
import org.integratedmodelling.klab.extensions.groovy.model.State
import org.integratedmodelling.klab.utils.StringUtil


/**
 * Selector is at the root of a filter chain, return by select() and family on 
 * a direct observation. Pick one or more observations or use the wrapped collection.
 * 
 * @author Ferd
 *
 */
class ObservationSet extends AbstractObservationSet {

    int nItems = 1;
    Object[] args;
    String[] match;
    boolean recursive;
    boolean children;

    @Override
    protected Object process(Object result) {

        List<Object> ret = new ArrayList<>();

        for (Object obs : ((DirectObservation)(context.unwrap())).retrieve(new ArrayList<IObservation>(), recursive, children, processArgs())) {
            if (match != null && obs instanceof IDirectObservation) {
                boolean go = false;
                for (String m : match) {
                      if (StringUtil.matchWildcards(((IDirectObservation)obs).getName(), m)) {
                          go = true;
                          break;
                      }
                }
                if (!go) { 
                    continue;
                }    
            }
            ret.add(wrapIfNecessary(obs));
        }
        return ret;
    }
    
    def asBoolean() {
        resolve();
        if (adaptee == null || (adaptee instanceof Collection && ((Collection)adaptee).size() == 0)) {
            return false;
        }
        return true;
    }
    
    def resolveToObservationCollection() {
        resolve();
        def ret = new ArrayList<IObservation>();
        if (adaptee != null) {
            if (adaptee instanceof Observation) {
                ret.add(((Observation)adaptee).obs);
            } else if (adaptee instanceof Collection) {
                for (object in adaptee) {
                    if (object instanceof Observation) {
                        ret.add(((Observation)object).obs);
                    } else if (object instanceof IObservation) {
                        ret.add(object);
                    }
                }
            }
        }
        return ret;
    }
    
    Object asType(Class cls) {
        if (Number.isAssignableFrom(cls) || IConcept.isAssignableFrom(cls) || Concept.isAssignableFrom(cls)) {
            Object o = pick(1);
            if (!(o instanceof State)) {
                throw new KlabValidationException("result of select() can only be cast to scalar values when the result is a single state");
            }
            return ((State)o).asType(cls);
        }
        return super.asType(cls);
    }
    
    def processArgs() {
        List<Object> ret = new ArrayList<>();
        for (Object o : args) {
            if (o instanceof Collection) {
                for (Object oo : ((Collection<?>)o)) {
                    ret.add(oo instanceof Concept ? ((Concept)oo).concept : oo);
                }
            } else {
                ret.add(o instanceof Concept ? ((Concept)o).concept : o);
            }
        }
        return ret.toArray();
    }
    
    /**
     * Use simple wildcard matching 
     * @param match
     * @return
     */
    def like(String ... match) {
        this.match = match;
        return this;
    }
}
