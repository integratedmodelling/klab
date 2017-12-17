///*******************************************************************************
// * Copyright (C) 2007, 2015:
// * 
// * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
// * other authors listed in @author annotations
// *
// * All rights reserved. This file is part of the k.LAB software suite, meant to enable
// * modular, collaborative, integrated development of interoperable data and model
// * components. For details, see http://integratedmodelling.org.
// * 
// * This program is free software; you can redistribute it and/or modify it under the terms
// * of the Affero General Public License Version 3 or any later version.
// *
// * This program is distributed in the hope that it will be useful, but without any
// * warranty; without even the implied warranty of merchantability or fitness for a
// * particular purpose. See the Affero General Public License for more details.
// * 
// * You should have received a copy of the Affero General Public License along with this
// * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
// * 330, Boston, MA 02111-1307, USA. The license is also available at:
// * https://www.gnu.org/licenses/agpl.html
// *******************************************************************************/
//package org.integratedmodelling.klab.persistence;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//
//import org.integratedmodelling.klab.API;
//import org.integratedmodelling.klab.api.engine.IServer;
//import org.integratedmodelling.klab.api.model.IKimObject;
//import org.integratedmodelling.klab.api.observations.IObservation;
//import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
//import org.integratedmodelling.klab.common.view.View.Observation;
//import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
//import org.integratedmodelling.klab.utils.collections.ImmutableList;
//
///**
// * Stub for a merging observation query result handler.
// * 
// * @author ferdinando.villa
// *
// */
//public class ObservationQueryResult extends ImmutableList<IObservation>
//        implements INetwork.DistributedOperation<List<Observation>, List<Observation>> {
//
//    ArrayList<Observation>            observationBeans = new ArrayList<>();
//    boolean                           sorted    = false;
//    IMonitor                          monitor;
//    RestTemplateHelper                template;
//    private ObservationQuery query;
//
//    public class It implements Iterator<IObservation> {
//
//        Iterator<Observation> _it;
//
//        It() {
//            // if (!sorted) {
//            // Collections.sort(modelData, comparator);
//            // sorted = true;
//            //
//            // if (KLAB.CONFIG.isDebug()) {
//            // if (modelData.size() > 0) {
//            // monitor.debug("---- SCORES ------");
//            // int n = 1;
//            // for (Model md : modelData) {
//            // monitor.debug(describeRanks(md, 2, n++));
//            // }
//            // monitor.debug("------------------");
//            // } else {
//            // monitor.debug("No results");
//            // }
//            // }
//            // }
//            _it = observationBeans.iterator();
//        }
//
//        @Override
//        public boolean hasNext() {
//            return _it.hasNext();
//        }
//
//        @Override
//        public IObservation next() {
//            return getModel(_it.next());
//        }
//
//        @Override
//        public void remove() {
//            throw new KlabRuntimeException("remove() in ObservationQueryResult iterator is unsupported");
//        }
//    }
//
//    /**
//     * This object is optimized for transparent, lazy retrieving and loading of actual
//     * models from model metadata. We can call getModelData() if we want the ranked
//     * metadata instead. This will require casting the List<IModel> coming from
//     * {@link ModelKbox query} to a ModelQueryResult first.
//     * 
//     * @return the ranked model metadata, best match first.
//     */
//    public List<Observation> getModelData() {
//        return observationBeans;
//    }
//
//    private IObservation getModel(Observation observation) {
//
//        IObservation ret = null;
//
//        if (observation == null) {
//            return null;
//        }
//
////        if (observation.getServerId() != null && KLAB.MMANAGER.findModelObject(observation.getName()) == null) {
////            // load remote projects if necessary. After the call, the object should be
////            // available locally.
////            IProject remoteProject = KLAB.PMANAGER.getProject(observation.getProjectUrn());
////            if (remoteProject == null) {
////                INode node = KLAB.ENGINE.getNetwork().getNode(observation.getServerId());
////                if (node != null) {
////                    try {
////                        remoteProject = ResourceFactory
////                                .getProjectFromURN(node.getUrl(), observation.getProjectUrn(), node
////                                        .getId(), KLAB.WORKSPACE
////                                                .getDeployLocation(), ((IModelingEngine) KLAB.ENGINE)
////                                                        .getUser());
////                        if (!(remoteProject instanceof IComponent)) {
////                            KLAB.PMANAGER
////                                    .loadProject(remoteProject.getId(), KLAB.MFACTORY
////                                            .getRootParsingContext());
////                        }
////                    } catch (Exception e) {
////                        throw new KlabRuntimeException(e);
////                    }
////                } else {
////                    throw new KlabRuntimeException("node " + observation.getServerId()
////                            + " returned from remote query has become inaccessible");
////                }
////            }
////        }
////
////        INamespace ns = KLAB.MMANAGER.getNamespace(observation.getNamespaceId());
////        if (ns != null) {
////            ret = ns.getModelObject(observation.getId());
////        }
////        if (!(ret instanceof IModel)) {
////            return null;
////        }
//
//        return (IObservation) ret;
//    }
//
//    public ObservationQueryResult(IMonitor monitor) {
//        this.monitor = monitor;
//    }
//
//    @Override
//    public boolean contains(Object arg0) {
//        throw new KlabRuntimeException("contains() in ObservationQueryResult is unsupported");
//    }
//
//    @Override
//    public IObservation get(int arg0) {
//        return getModel(observationBeans.get(arg0));
//    }
//
//    @Override
//    public Iterator<IObservation> iterator() {
//        return new It();
//    }
//
//    @Override
//    public int size() {
//        return observationBeans.size();
//    }
//
//    @Override
//    public Object[] toArray() {
//        throw new KlabRuntimeException("toArray() in ObservationQueryResult is unsupported");
//    }
//
//    @Override
//    public <T> T[] toArray(T[] arg0) {
//        throw new KlabRuntimeException("toArray() in ObservationQueryResult is unsupported");
//    }
//
//    @Override
//    public boolean acceptNode(IServer node) {
//        return node.provides(IResourceConfiguration.StaticResource.MODEL_QUERY);
//    }
//
//    @Override
//    public List<Observation> executeCall(IServer node) {
//
//        if (template == null) {
//            template = RestTemplateHelper.newTemplate();
//        }
//
//        ObservationQueryResponse result = template.with(node)
//                .post(node.getUrl() + API.QUERY_MODELS + ".json", query, ObservationQueryResponse.class);
//        return result.getObservations();
//    }
//
//    @Override
//    public List<Observation> merge(Collection<List<Observation>> results) {
//
//        for (List<Observation> result : results) {
//            for (Observation md : result) {
//                if (!observationBeans.contains(md)) {
//                    addObservationBean(md);
//                }
//            }
//        }
//
//        return observationBeans;
//    }
//
//    public void addObservationBean(Observation md) {
//        observationBeans.add(md);
//        sorted = false;
//    }
//
//    public void setQuery(ObservationQuery query) {
//        this.query = query;
//    }
//
//}
