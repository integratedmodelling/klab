package org.integratedmodelling.klab.extensions.groovy.sets
//
//import org.integratedmodelling.klab.api.knowledge.IConcept
//import org.integratedmodelling.klab.api.observations.IDirectObservation
//import org.integratedmodelling.klab.api.observations.IState
//import org.integratedmodelling.klab.components.geospace.api.ISpatialIndex
//import org.integratedmodelling.klab.extensions.groovy.model.Concept
//import org.integratedmodelling.klab.extensions.groovy.model.DirectObservation
//import org.integratedmodelling.klab.extensions.groovy.model.Observation
//import org.integratedmodelling.klab.extensions.groovy.model.Trajectory
//
//class SpatialSet extends AbstractObservationSet {
//
//    SpaceRel type;
//    Object[] args;
//
//    IState costLayer = null;
//    ISpatialIndex index = null;
////    SpatialNetwork network = null;
//
//    def along(Object concept) {
//        
////        if (concept instanceof Concept) {            
////            this.context << concept;
////            concept = ((Concept)concept).concept;            
////        }
////        
////        if (NS.isThing(concept)) {
////            /*
////             * TODO this must use a hash of configurations stored in the context by concept.
////             * Also, configs such as Terrain must remember their constituent qualities and 
////             * make them available to the cost function.
////             */
////            this.network = new SpatialNetwork(context.obs, concept);
////        } else if (NS.isQuality(concept)) {
////            costLayer = (this.context << concept);
////        }
//        return this;
//    }
//
//    /**
//     * Can only be used after calling along() with a quality
//     * argument; specifies a closure that computes the cost 
//     * along the cost layer. If cost is Double.POSITIVE_INFINITY,
//     * the area is an impassable obstacle. Closure takes as argument
//     * ('it') the value of the cost layer in each point.
//     * 
//     * @param closure
//     * @return
//     */
//    def withCost(Object closure) {
////        if (costLayer == null) {
////            throw new KlabRuntimeException("Cannot add cost functions without a cost layer ('along' a quality)");
////        }
//        return this;
//    }
//
//    def within(double meters) {
//        // TODO
//        return this;
//    }
//
//    def beyond(double meters) {
//        // TODO
//        return this;
//    }
//
//    def across(IConcept concept) {
//                
//        if (concept instanceof Concept) {
//            concept = ((Concept)concept).concept;
//        }
//        return this;
//    }
//
//    def inside(Object container) {
//        // TODO
//        return this;
//    }
//
//    def outside(Object container) {
//        // TODO
//        return this;
//    }
//
//    @Override
//    protected Object process(Object result) {
//
//        // find an observation in arguments
//        IDirectObservation source = null;
//        if (args != null) {
//            for (Object o : args) {
//                if (o instanceof IDirectObservation) {
//                    source = (IDirectObservation)o;
//                    break;
//                } else if (o instanceof DirectObservation) {
//                    source = ((DirectObservation)o).unwrap();
//                    break;
//                }
//            }
//        }
//
//        /*
//         * arrange or filter the received objects according to
//         * spatial relationships
//         */
//        if (result instanceof Collection) {
////            if (this.network != null) {
////
////                // route through network. Can be quite costly as we need to build a distance matrix across it.
////                def ret = [];
////
////                for (Object o : ((Collection<?>)result)) {
////                    org.integratedmodelling.engine.geospace.layers.Trajectory t =
////                            this.network.route(source, ((Observation)o).unwrap());
////                    if (t != null) {
////                        ret.add(new SpatialObsProxy(((Observation)o).unwrap(), context.binding, t.getLength(),
////                                new org.integratedmodelling.thinklab.script.Trajectory(t, context)));
////                    }
////                }
////
////                if (type == SpaceRel.NEAR || type == SpaceRel.FAR) {
////                    ret = 
////                        type == SpaceRel.NEAR ?
////                            ret.toSorted {a, b -> a.distance <=> b.distance } :
////                            ret.toSorted {a, b -> b.distance <=> a.distance };
////                }
////                return ret;
////
////            } else if (costLayer != null) {
////                // TODO - this is the A* algorithm on a potentially very large
////                // graph created by irregular meshing of the cost layer.
////            } else {
////                if (index == null) {
////                    index = new SpatialIndex(context.obs.getScale().getSpace());
////                    for (Object o : ((Collection<?>)result)) {
////                        index.add(((Observation)o).unwrap());
////                    }
////                }
////
////                if (type == SpaceRel.NEAR || type == SpaceRel.FAR) {
////
////                    List<Object> ret = new ArrayList<>();
////
////                    if (source != null) {
////                        List<Pair<IDirectObservation, Double>> dists = index.getNear(source);
////                        if (type == SpaceRel.FAR) {
////                            Collections.reverse(dists);
////                        }
////
////                        for (Pair<IDirectObservation, Double> o : dists) {
////                            ret.add(new SpatialObsProxy(o.getFirst(), context.binding, o.getSecond(), new Trajectory(source, o.getFirst(), context)));
////                        }
////
////                        return ret;
////                    }
////                } // TODO other types
////            }
//        } else if (result instanceof Observation) {
//            return new SpatialObsProxy(result.unwrap(), context.binding);
//        }
//
//        // whatever
//        return result;
//    }
//
//    /**
//     * Wrapper for returned observations, gives access to trajectories and
//     * distances defining the spatial set they come from.
//     * 
//     * @author ferdinando.villa
//     *
//     */
//    class SpatialObsProxy extends DirectObservation {
//
//        double distance;
//        Trajectory trajectory;
//
//        public SpatialObsProxy(IDirectObservation obs, Binding binding) {
//            super(obs, binding);
//        }
//
//        public SpatialObsProxy(IDirectObservation obs, Binding binding, double distance) {
//            super(obs, binding);
//            this.distance = distance;
//        }
//
//        public SpatialObsProxy(IDirectObservation obs, Binding binding, double distance, Trajectory trajectory) {
//            super(obs, binding);
//            this.distance = distance;
//            this.trajectory = trajectory;
//        }
//
//
//        def getTrajectory() {
//            return trajectory;
//        }
//
//        def getDistance() {
//            return distance;
//        }
//    }
//}
