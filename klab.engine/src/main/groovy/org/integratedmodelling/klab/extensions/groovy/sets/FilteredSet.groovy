package org.integratedmodelling.klab.extensions.groovy.sets
//
//class FilteredSet extends AbstractObservationSet {
//
//	Closure closure;
//	
//	public FilteredSet(AbstractObservationSet parent, Object adaptee, Closure closure) {
//		super(parent);
//		this.adaptee = adaptee;
//		this.closure = closure;
//	}
//
//	@Override
//	protected Object process(Object adapteet) {
//        
//        def ret = new ArrayList<Object>();
//        for (Object o : adapteet) {
//            if (this.closure(o)) {
//                ret.add(o);
//            }
//        }
//		return ret;
//	}
//
//}
