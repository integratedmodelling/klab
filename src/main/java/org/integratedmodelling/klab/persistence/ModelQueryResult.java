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
//package org.integratedmodelling.engine.modelling.kbox;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.integratedmodelling.api.configuration.IResourceConfiguration;
//import org.integratedmodelling.api.engine.IModelingEngine;
//import org.integratedmodelling.api.metadata.IModelMetadata;
//import org.integratedmodelling.api.modelling.IModel;
//import org.integratedmodelling.api.modelling.IModelObject;
//import org.integratedmodelling.api.modelling.INamespace;
//import org.integratedmodelling.api.modelling.IObserver;
//import org.integratedmodelling.api.modelling.resolution.IModelPrioritizer;
//import org.integratedmodelling.api.monitoring.IMonitor;
//import org.integratedmodelling.api.network.API;
//import org.integratedmodelling.api.network.IComponent;
//import org.integratedmodelling.api.network.INetwork;
//import org.integratedmodelling.api.network.IServer;
//import org.integratedmodelling.api.project.IProject;
//import org.integratedmodelling.collections.ImmutableList;
//import org.integratedmodelling.common.beans.Model;
//import org.integratedmodelling.common.beans.requests.ModelQuery;
//import org.integratedmodelling.common.beans.responses.ModelQueryResponse;
//import org.integratedmodelling.common.client.NodeClient;
//import org.integratedmodelling.common.client.RestTemplateHelper;
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.kim.KIMModel;
//import org.integratedmodelling.common.kim.KIMObserver;
//import org.integratedmodelling.common.resources.ResourceFactory;
//import org.integratedmodelling.common.utils.StringUtils;
//import org.integratedmodelling.engine.modelling.resolver.ModelPrioritizer;
//import org.integratedmodelling.exceptions.KlabRuntimeException;
//
///**
// * Result of a query. The get(n) method returns a model in order of insertion; the
// * iterator returns RANKED models. Model retrieval is lazy and the collection only holds a
// * small amount of ranked metadata until a model is wanted. The model retriever should
// * (eventually) be able to integrate models from any server, local or remote, creating the
// * final models based on the server ID in model data.
// * 
// * @author ferdinando.villa
// *
// */
//public class ModelQueryResult extends ImmutableList<IModel>
//        implements INetwork.DistributedOperation<List<Model>, List<Model>> {
//
//    IModelPrioritizer<IModelMetadata> comparator;
//    ArrayList<Model>                  modelData = new ArrayList<>();
//    boolean                           sorted    = false;
//    IMonitor                          monitor;
//    ModelQuery                        query;
//    RestTemplateHelper                template;
//
//    public class It implements Iterator<IModel> {
//
//        Iterator<Model> _it;
//
//        It() {
//            if (!sorted) {
//                Collections.sort(modelData, comparator);
//                sorted = true;
//
//                if (KLAB.CONFIG.isDebug()) {
//                    if (modelData.size() > 0) {
//                        monitor.debug("---- SCORES ------");
//                        int n = 1;
//                        for (Model md : modelData) {
//                            monitor.debug(describeRanks(md, 2, n++));
//                        }
//                        monitor.debug("------------------");
//                    } else {
//                        monitor.debug("No results");
//                    }
//                }
//            }
//            _it = modelData.iterator();
//        }
//
//        @Override
//        public boolean hasNext() {
//            return _it.hasNext();
//        }
//
//        @Override
//        public IModel next() {
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
//    public List<Model> getModelData() {
//        if (!sorted) {
//            Collections.sort(modelData, comparator);
//            sorted = true;
//        }
//        return modelData;
//    }
//
//    private IModel getModel(Model md) {
//
//        IModelObject ret = null;
//
//        if (md == null) {
//            return null;
//        }
//        
//        if (md.getServerId() != null && KLAB.MMANAGER.findModelObject(md.getName()) == null) {
//            // load remote projects if necessary. After the call, the object should be
//            // available locally.
//            IProject remoteProject = KLAB.PMANAGER.getProject(md.getProjectUrn());
//            if (remoteProject == null) {
//                IServer node = KLAB.ENGINE.getNetwork().getNode(md.getServerId());
//                if (node != null) {
//                    try {
//                        remoteProject = ResourceFactory
//                                .getProjectFromURN(node.getUrl(), md.getProjectUrn(), node.getId(), KLAB.WORKSPACE
//                                        .getDeployLocation(), ((IModelingEngine) KLAB.ENGINE)
//                                                .getUser());
//                        if (!(remoteProject instanceof IComponent)) {
//                            KLAB.PMANAGER
//                                    .loadProject(remoteProject.getId(), KLAB.MFACTORY
//                                            .getRootParsingContext());
//                        }
//                    } catch (Exception e) {
//                        throw new KlabRuntimeException(e);
//                    }
//                } else {
//                    throw new KlabRuntimeException("node " + md.getServerId()
//                            + " returned from remote query has become inaccessible");
//                }
//            }
//        }
//
//        INamespace ns = KLAB.MMANAGER.getNamespace(md.getNamespaceId());
//        if (ns != null) {
//            ret = ns.getModelObject(md.getId());
//        }
//        if (!(ret instanceof IModel)) {
//            return null;
//        }
//        
//        if (md.getDereifyingAttribute() != null) {
//            IObserver obs = ((KIMModel) ret).getAttributeObserver(md.getDereifyingAttribute());
//            if (obs != null) {
//                ret = new KIMModel((KIMObserver) obs, (KIMModel) ret, md.getDereifyingAttribute(), monitor);
//            } else {
//                return null;
//            }
//        }
//
//        return (IModel) ret;
//    }
//
//    public String describeRanks(Model md, int indent, int n) {
//
//        String ret = "";
//        String filler = StringUtils.spaces(indent);
//
//        ret += filler + StringUtils.rightPad(n + ".", 4) + md.getName() + " ["
//                + (md.getServerId() == null ? "local" : md.getServerId()) + "]\n";
//        Map<String, Object> ranks = comparator.getRanks(md);
//        for (String s : comparator.listCriteria()) {
//            ret += filler + "  " + StringUtils.rightPad(s, 25) + " "
//                    + ranks.get(s) + "\n";
//        }
//
//        return ret;
//    }
//
//    public ModelQueryResult(IModelPrioritizer<IModelMetadata> prioritizer, IMonitor monitor) {
//        comparator = prioritizer;
//        this.monitor = monitor;
//    }
//
//    @Override
//    public boolean contains(Object arg0) {
//        throw new KlabRuntimeException("contains() in ObservationQueryResult is unsupported");
//    }
//
//    @Override
//    public IModel get(int arg0) {
//        return getModel(modelData.get(arg0));
//    }
//
//    @Override
//    public Iterator<IModel> iterator() {
//        return new It();
//    }
//
//    @Override
//    public int size() {
//        return modelData.size();
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
//    public List<Model> executeCall(IServer node) {
//
//        if (template == null) {
//            template = RestTemplateHelper.newTemplate();
//        }
//
//        ModelQueryResponse result = template.with(node)
//                .post(node.getUrl() + API.QUERY_MODELS + ".json", ((NodeClient) node)
//                        .checkVersion(query), ModelQueryResponse.class);
//        return result.getModels();
//    }
//
//    @Override
//    public List<Model> merge(Collection<List<Model>> results) {
//
//        for (List<Model> result : results) {
//            for (Model md : result) {
//                if (!modelData.contains(md)) {
//                    addModel(md);
//                    ((ModelPrioritizer)comparator).registerRanks(md);
//                }
//            }
//        }
//
//        return modelData;
//    }
//
//    public void addModel(Model md) {
//        modelData.add(md);
//        sorted = false;
//    }
//
//    public void setQuery(ModelQuery query) {
//        this.query = query;
//    }
//
//}
