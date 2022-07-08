package org.integratedmodelling.klab.extensions.groovy
//
//import org.integratedmodelling.klab.api.observations.IObservation
//import org.integratedmodelling.klab.api.observations.scale.IExtent
//import org.integratedmodelling.klab.api.observations.scale.IScale
//import org.integratedmodelling.klab.exceptions.KlabUnimplementedException
//import org.integratedmodelling.klab.extensions.groovy.model.Extent
//import org.integratedmodelling.klab.extensions.groovy.model.Observation
//import org.integratedmodelling.klab.extensions.groovy.model.Transition
//import org.integratedmodelling.klab.scale.Scale
//
//class Utils {
//
//    /**
//     * Extract anything relevant from a list of objects and make a 
//     * scale out of it. Duplicate extents are unioned.
//     * 
//     * @param objs extents, scales, observations, raw or Groovy-wrapped
//     * @return a IScale (not Groovy-wrapped)
//     */
//    public static IScale extractScale(Object[] objs) {
//
//        IScale scale =  new Scale();
//
//        if (objs != null) {
//            for (Object o : objs) {
//
//                if (o instanceof Observation) {
//                    o = ((Observation)o).obs;
//                }
//                if (o instanceof IObservation) {
//                    o = ((IObservation)o).getScale();
//                }
//                if (o instanceof org.integratedmodelling.klab.extensions.groovy.model.Scale) {
//                    o = ((org.integratedmodelling.klab.extensions.groovy.model.Scale)o).scale;
//                }
//
//                if (o instanceof IExtent) {
//                    ((Scale)scale).mergeExtent((IExtent)o, true);
//                } else if (o instanceof Extent) {
//                    ((Scale)scale).mergeExtent(((Extent)o).extent, true);
//                } else if (o instanceof Transition) {
//					throw new KlabUnimplementedException("groovy.Transition");
//                    ((Scale)scale).mergeExtent(((Transition)o).transition.getTime(), true);
//                } else if (o instanceof IScale) {
//                    for (IExtent ext : ((IScale)o)) {
//                        ((Scale)scale).mergeExtent(ext, true);
//                    }
//                } else if (o instanceof Extentual) {
//                    for (IExtent ext : ((Extentual)o).getExtents()) {
//                        ((Scale)scale).mergeExtent(ext, true);
//                    }
//                }
//            }
//        }
//
//        return scale;
//    }
//
//    static public class Args {
//        Map<Object, Object> named;
//        List<Object> unnamed;
//    }
//
//    /**
//     * Separate the parameters of a variable-args function into the fixed parameters and
//     * a map of the named parameters. If no named parameters were supplied, return an empty
//     * map for them.
//     * 
//     * @param args
//     * @return
//     */
//    static public Args separateNamedArguments(Object[] args) {
//
//        Map<?,?> map = null;
//        List<Object> list = new ArrayList<>();
//
//        if (args != null) {
//            int i = 0;
//            for (Object o : args) {
//                if (i == 0 && o instanceof Map) {
//                    map = (Map)o;
//                } else {
//                    list.add(o);
//                }
//                i++;
//            }
//        }
//
//        return new Args(named: (map == null ? new HashMap<String,Object>() : map), unnamed: list);
//    }
//}
