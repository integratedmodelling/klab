package org.integratedmodelling.klab.extensions.groovy.sets;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.IRelationship
import org.integratedmodelling.klab.api.observations.IState
import org.integratedmodelling.klab.extensions.groovy.model.DirectObservation
import org.integratedmodelling.klab.extensions.groovy.model.Observation
import org.integratedmodelling.klab.extensions.groovy.model.Relationship
import org.integratedmodelling.klab.extensions.groovy.model.State


public abstract class AbstractObservationSet extends GroovyObjectSupport {

	Object adaptee;
	Observation context;
	Context contextType;
	AbstractObservationSet parentProxy;
	boolean resolved = false;
	
	public enum Context {
		OBJECT, COLLECTION
	}

	public AbstractObservationSet(Observation context, Context ctx) {
		this.context = context;
		this.contextType = ctx;
		this.adaptee = this;
	}

	public AbstractObservationSet(AbstractObservationSet parent) {
		this.context = parent.context;
		this.contextType = parent.contextType;
		this.parentProxy = parent;
		this.adaptee = this;
	}
    
    protected AbstractObservationSet() {}

	def resolve() {
		if (!resolved) {
			resolved = true;
			adaptee = process(parentProxy == null ? adaptee : parentProxy.getResult(adaptee));
				if (adaptee instanceof Collection && ((Collection)adaptee).size() == 1) {
					adaptee = ((Collection)adaptee).iterator().next();
				}
		}
		return adaptee;
	}
    
    protected abstract Object process(Object result);
    
    def getResult(Object object) {
        if (resolved) {
            return adaptee;
        }
        return process(object);
    }
    
	def invokeMethod(String name, Object args) {
		try {
			return super.invokeMethod(name, args);
		} catch (MissingMethodException e) {
			return InvokerHelper.invokeMethod(resolve(), name, args);
		}
	}
    
    def pick() {
        return pick(1);
    }
    	
	def pick(int nItems) {
        
        Object ret = resolve();
        if (ret == null) {
            return nItems == 1 ? null : new ArrayList<Observation>();
        }

        if (ret instanceof Observation) {
            if (nItems > 1) {
                return Collections.singletonList(ret);
            }
            return ret;
        }

        if (ret instanceof Collection<?>) {
            ret = CollectionUtils.pick((List<?>)ret, nItems);
            if (ret.size() == 1) {
                ret = ((Collection<?>)ret).iterator().next();
            }
        }
        
		return ret;
	}
	
    def wrapIfNecessary(Object obj) {
        if (!(obj instanceof Observation)) {
            if (obj instanceof IRelationship) {
                obj = new Relationship(obj, context.binding);
            } else if (obj instanceof IDirectObservation) {
                obj = new DirectObservation(obj, context.binding);
            } else if (obj instanceof IState) {
                obj = new State(obj, context.binding);
            }
        }
        return obj; 
    }

    def first() {
        Object ret = resolve();
        if (ret instanceof Collection) {
            ret = ((Collection)ret).size() == 0 ? null : ((Collection)ret).iterator().next();
        }
        return ret;
    }
        
	public Collection<Object> all() {
		Object ret = resolve();
		if (ret == null) {
			return new ArrayList<>();
		}
		if (!(ret instanceof Collection)) {
			return Collections.singletonList(ret);
		}
		return ret;
	}
	
	public Object where(Object closure) {
		return new FilteredSet(this, adaptee, closure);
	}

	public Object within(Object[] args) {
        return new SpatialSet(parentProxy: this, context: context, type: SpaceRel.WITHIN, args: args);
	}
	
	public Object inside(Object[] args) {
        return new SpatialSet(parentProxy: this, context: context, type: SpaceRel.INSIDE, args: args);
	}

    public Object outside(Object[] args) {
        return new SpatialSet(parentProxy: this, context: context, type: SpaceRel.OUTSIDE, args: args);
    }

	public Object near(Object[] args) {
        return new SpatialSet(parentProxy: this, context: context, type: SpaceRel.NEAR, args: args);
	}

	public Object far(Object[] args) {
        return new SpatialSet(parentProxy: this, context: context, type: SpaceRel.FAR, args: args);
	}
	
	public Object locatedWithin(Object[] args) {
	}
	
	public Object locatedOutside(Object[] args) {
	}

	public Object occurredBefore(Object[] args) {
	}
	
	public Object occurredAfter(Object[] args) {
	}

	public Object occurredBetween(Object[] args) {
	}

	public Object occurredAt(Object[] args) {
	}
    
	public Iterator iterator() {
        Object ret = resolve();
		return InvokerHelper.asIterator(ret);
	}
}
