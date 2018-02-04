/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Projects;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.collections.ImmutableList;

/**
 * Result of a query. The get(n) method returns a model in order of insertion; the iterator returns
 * RANKED models. Model retrieval is lazy and the collection only holds a small amount of ranked
 * metadata until a model is wanted. The model retriever should (eventually) be able to integrate
 * models from any server, local or remote, creating the final models based on the server ID in
 * model data.
 * 
 * @author ferdinando.villa
 *
 */
public class ModelQueryResult extends ImmutableList<IModel>
    /* implements INetwork.DistributedOperation<List<Model>, List<Model>> */ {

  IPrioritizer<Model> comparator;
  ArrayList<Model> modelData = new ArrayList<>();
  boolean sorted = false;
  IMonitor monitor;
//  ModelQuery query;
//  RestTemplateHelper template;

  public class It implements Iterator<IModel> {

    Iterator<Model> _it;

    It() {
      if (!sorted) {
        Collections.sort(modelData, comparator);
        sorted = true;

        if (Configuration.INSTANCE.isDebuggingEnabled()) {
          if (modelData.size() > 0) {
            monitor.debug("---- SCORES ------");
            int n = 1;
            for (Model md : modelData) {
              monitor.debug(describeRanks(md, 2, n++));
            }
            monitor.debug("------------------");
          } else {
            monitor.debug("No results");
          }
        }
      }
      _it = modelData.iterator();
    }

    @Override
    public boolean hasNext() {
      return _it.hasNext();
    }

    @Override
    public IModel next() {
      return getModel(_it.next());
    }

    @Override
    public void remove() {
      throw new KlabRuntimeException("remove() in ObservationQueryResult iterator is unsupported");
    }
  }

  /**
   * This object is optimized for transparent, lazy retrieving and loading of actual models from
   * model metadata. We can call getModelData() if we want the ranked metadata instead. This will
   * require casting the List<IModel> coming from {@link ModelKbox query} to a ModelQueryResult
   * first.
   * 
   * @return the ranked model metadata, best match first.
   */
  public List<Model> getModelData() {
    if (!sorted) {
      Collections.sort(modelData, comparator);
      sorted = true;
    }
    return modelData;
  }

  private IModel getModel(Model md) {

    IKimObject ret = null;

    if (md == null) {
      return null;
    }

    if (md.getServerId() != null && Resources.INSTANCE.getModelObject(md.getName()) == null) {
      // load remote projects if necessary. After the call, the object should be
      // available locally.
      IProject remoteProject = Projects.INSTANCE.getProject(md.getProjectUrn());
      if (remoteProject == null) {
//        IServer node = KLAB.ENGINE.getNetwork().getNode(md.getServerId());
//        if (node != null) {
//          try {
//            remoteProject =
//                ResourceFactory.getProjectFromURN(node.getUrl(), md.getProjectUrn(), node.getId(),
//                    KLAB.WORKSPACE.getDeployLocation(), ((IModelingEngine) KLAB.ENGINE).getUser());
//            if (!(remoteProject instanceof IComponent)) {
//              KLAB.PMANAGER.loadProject(remoteProject.getId(),
//                  KLAB.MFACTORY.getRootParsingContext());
//            }
//          } catch (Exception e) {
//            throw new KlabRuntimeException(e);
//          }
//        } else {
//          throw new KlabRuntimeException(
//              "node " + md.getServerId() + " returned from remote query has become inaccessible");
//        }
      }
    }

    INamespace ns = Namespaces.INSTANCE.getNamespace(md.getNamespaceId());
    if (ns != null) {
      ret = ns.getObject(md.getId());
    }
    
    if (!(ret instanceof IModel)) {
      return null;
    }

    if (md.getDereifyingAttribute() != null) {
      IObservable obs = ((IModel)ret).getAttributeObservables().get(md.getDereifyingAttribute());
      if (obs != null) {
        throw new KlabRuntimeException("UNIMPLEMENTED! Attribute model");
//        ret = new KIMModel((KIMObserver) obs, (KIMModel) ret, md.getDereifyingAttribute(), monitor);
      } else {
        return null;
      }
    }

    /*
     * TODO wrap in RankedModel
     */
    
    return (IModel) ret;
  }

  public String describeRanks(Model md, int indent, int n) {

    String ret = "";
    String filler = StringUtils.spaces(indent);

    ret += filler + StringUtils.rightPad(n + ".", 4) + md.getName() + " ["
        + (md.getServerId() == null ? "local" : md.getServerId()) + "]\n";
    Map<String, Double> ranks = comparator.getRanks(md);
    for (String s : comparator.listCriteria()) {
      ret += filler + "  " + StringUtils.rightPad(s, 25) + " " + ranks.get(s) + "\n";
    }

    return ret;
  }

  public ModelQueryResult(IPrioritizer<Model> prioritizer, IMonitor monitor) {
    comparator = prioritizer;
    this.monitor = monitor;
  }

  @Override
  public boolean contains(Object arg0) {
    throw new KlabRuntimeException("contains() in ObservationQueryResult is unsupported");
  }

  @Override
  public IModel get(int arg0) {
    return getModel(modelData.get(arg0));
  }

  @Override
  public Iterator<IModel> iterator() {
    return new It();
  }

  @Override
  public int size() {
    return modelData.size();
  }

  @Override
  public Object[] toArray() {
    throw new KlabRuntimeException("toArray() in ObservationQueryResult is unsupported");
  }

  @Override
  public <T> T[] toArray(T[] arg0) {
    throw new KlabRuntimeException("toArray() in ObservationQueryResult is unsupported");
  }

//  @Override
//  public boolean acceptNode(IServer node) {
//    return node.provides(IResourceConfiguration.StaticResource.MODEL_QUERY);
//  }
//
//  @Override
//  public List<Model> executeCall(IServer node) {
//
//    if (template == null) {
//      template = RestTemplateHelper.newTemplate();
//    }
//
//    ModelQueryResponse result = template.with(node).post(node.getUrl() + API.QUERY_MODELS + ".json",
//        ((NodeClient) node).checkVersion(query), ModelQueryResponse.class);
//    return result.getModels();
//  }
//
//  @Override
//  public List<Model> merge(Collection<List<Model>> results) {
//
//    for (List<Model> result : results) {
//      for (Model md : result) {
//        if (!modelData.contains(md)) {
//          addModel(md);
//          ((ModelPrioritizer) comparator).registerRanks(md);
//        }
//      }
//    }
//
//    return modelData;
//  }

  public void addModel(Model md) {
    modelData.add(md);
    sorted = false;
  }

//  public void setQuery(ModelQuery query) {
//    this.query = query;
//  }

}
