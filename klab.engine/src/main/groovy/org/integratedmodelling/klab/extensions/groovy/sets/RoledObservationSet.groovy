package org.integratedmodelling.klab.extensions.groovy.sets
//
//import org.integratedmodelling.klab.api.knowledge.IConcept
//import org.integratedmodelling.klab.api.observations.IDirectObservation
//import org.integratedmodelling.klab.api.observations.IObservation
//import org.integratedmodelling.klab.components.runtime.observations.DirectObservation
//
///**
// * Selector is at the root of a filter chain, return by select() and family on 
// * a direct observation. Pick one or more observations or use the wrapped collection.
// * 
// * @author Ferd
// *
// */
//class RoledObservationSet extends ObservationSet {
//
//    IConcept role;
////    @Deprecated
////    IObservation roleContext;
//
//    @Override
//    protected Object process(Object result) {
//
//        List<Object> ret = new ArrayList<>();
//        if (role) {
//            for (IObservation obs : ((DirectObservation)(context.unwrap())).retrieveAll(recursive)) {
//                /*if (roleContext == null) {
//                    if (Roles.getRoles(obs.getObservable().getSemantics().getType()).contains(role)) {
//                        ret.add(wrapIfNecessary(obs));
//                    }
//                } else{*/ 
//                    if (obs instanceof IDirectObservation && NS.contains(role, ((IDirectObservation)roleContext).getRolesFor(obs))) {
//                        ret.add(wrapIfNecessary(obs));
//                    }
////                }
//            }
//        }
//        return ret;
//    }
//}
