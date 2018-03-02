package org.integratedmodelling.klab.components.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IRuntimeContext;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStatusException;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

/**
 * This component provides the default dataflow execution runtime and the associated services.
 * 
 * @author Ferd
 *
 */
@Component(id = "runtime", version = Version.CURRENT)
public class DefaultRuntimeProvider implements IRuntimeProvider {

  ExecutorService executor = Executors.newFixedThreadPool(Configuration.INSTANCE.getDataflowThreadCount());
  
  @Override
  public Future<IArtifact> compute(IActuator actuator, IRuntimeContext context, IMonitor monitor)
      throws KlabException {

    return executor.submit(new Callable<IArtifact>() {

      @Override
      public IArtifact call() throws Exception {
        Graph<IActuator, DefaultEdge> graph = createDependencyGraph(actuator);
        TopologicalOrderIterator<IActuator, DefaultEdge> sorter = new TopologicalOrderIterator<>(graph);
        IArtifact ret = null;
        while (sorter.hasNext()) {
          @SuppressWarnings("unchecked")
          Actuator<IArtifact> active = (Actuator<IArtifact>) sorter.next();
          ret = active.compute((DirectObservation) context.getRoot(), monitor);
          if (context.getRoot() == null && ret instanceof ISubject) {
            context.setRootSubject((ISubject)ret);
          }
        }
        return ret;
      }
    });
    
  }

  private Graph<IActuator, DefaultEdge> createDependencyGraph(IActuator actuator) {
    DefaultDirectedGraph<IActuator, DefaultEdge> ret =
        new DefaultDirectedGraph<>(DefaultEdge.class);
    insertActuator(actuator, ret, new HashMap<>());
    if (Configuration.INSTANCE.isDebuggingEnabled()) {
      Graphs.show(ret);
    }
    return ret;
  }

  private void insertActuator(IActuator actuator,
      DefaultDirectedGraph<IActuator, DefaultEdge> graph, Map<String, IActuator> catalog) {

    graph.addVertex(actuator);
    catalog.put(actuator.getName(), actuator);
    
    for (IActuator a : actuator.getActuators()) {
      if (((Actuator<?>)a).isReference()) {
        IActuator ref = catalog.get(a.getName());
        if (ref == null) {
          throw new KlabIllegalStatusException("referenced actuator not found");
        }
        graph.addEdge(ref, actuator);
      } else {
        insertActuator(a, graph, catalog);
      }
    }
    
  }

  @Override
  public IRuntimeContext createRuntimeContext() {
    return new RuntimeContext();
  }
  
}
