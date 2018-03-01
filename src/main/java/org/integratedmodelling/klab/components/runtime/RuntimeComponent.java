package org.integratedmodelling.klab.components.runtime;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
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
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.resolution.RuntimeContext;
import org.jgrapht.DirectedGraph;
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
public class RuntimeComponent implements IRuntimeProvider {

  // TODO configure
  private ExecutorService executor = Executors.newFixedThreadPool(10);

  @Override
  public Future<IArtifact> compute(IActuator actuator, IRuntimeContext context, IMonitor monitor)
      throws KlabException {

    FutureTask<IArtifact> ret = new FutureTask<IArtifact>(new Callable<IArtifact>() {

      @Override
      public IArtifact call() throws Exception {
        DirectedGraph<IActuator, DefaultEdge> graph = createDependencyGraph(actuator);
        TopologicalOrderIterator<IActuator, DefaultEdge> sorter = new TopologicalOrderIterator<>(graph);
        IArtifact ret = null;
        while (sorter.hasNext()) {
          @SuppressWarnings("unchecked")
          Actuator<IArtifact> active = (Actuator<IArtifact>) sorter.next();
          ret = active.compute((DirectObservation) context.getRoot(), monitor);
        }
        return ret;
      }
    });
    
    executor.execute(ret);
    
    return ret;
  }

  private DirectedGraph<IActuator, DefaultEdge> createDependencyGraph(IActuator actuator) {
    DefaultDirectedGraph<IActuator, DefaultEdge> ret =
        new DefaultDirectedGraph<>(DefaultEdge.class);
    return ret;
  }

  @Override
  public IRuntimeContext createRuntimeContext(ISubject rootSubject) {
    return new RuntimeContext((Subject) rootSubject);
  }

}
