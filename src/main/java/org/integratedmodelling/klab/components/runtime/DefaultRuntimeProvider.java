package org.integratedmodelling.klab.components.runtime;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.contextualizers.ExpressionResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.LiteralStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.UrnResolver;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStatusException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

/**
 * This component provides the default dataflow execution runtime and the associated services.
 * Simply dispatches a topologically sorted computation to a threadpool executor. The JGraphT-based
 * topological order built does not account for possible parallel actuators, which it should.
 * 
 * The initialization dataflow will build simple objects (essentially storage-only observations)
 * when the context is not temporal. If the context is temporal, it will create Akka actors for all
 * direct observations and prepare them for temporal contextualization.
 * 
 * @author Ferd
 *
 */
@Component(id = "runtime", version = Version.CURRENT)
public class DefaultRuntimeProvider implements IRuntimeProvider {

  ExecutorService executor =
      Executors.newFixedThreadPool(Configuration.INSTANCE.getDataflowThreadCount());

  @Override
  public Future<IObservationData> compute(IActuator actuator, IComputationContext context) throws KlabException {

    return executor.submit(new Callable<IObservationData>() {

      @Override
      public IObservationData call() throws Exception {

        Graph<IActuator, DefaultEdge> graph = createDependencyGraph(actuator);
        TopologicalOrderIterator<IActuator, DefaultEdge> sorter =
            new TopologicalOrderIterator<>(graph);
        IObservationData ret = null;

        while (sorter.hasNext()) {
          Actuator active = (Actuator) sorter.next();
          ret = active.compute(context.getTarget(), (IRuntimeContext) context);
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
      if (((Actuator) a).isReference()) {
        IActuator ref = catalog.get(a.getName());
        if (ref == null) {
          throw new KlabIllegalStatusException("referenced actuator not found");
        }
        graph.addEdge(ref, actuator);
      } else {
        /*
         * containment is a dependency only if there is a computation or mediation; otherwise
         * children are computable in parallel - which this implementation does not support.
         */
        if (!actuator.isComputed()) {
          graph.addVertex(a);
          graph.addEdge(a, actuator);
        }
        insertActuator(a, graph, catalog);
      }
    }
  }

  @Override
  public IComputationContext createRuntimeContext(IObservable target, IMonitor monitor) {
    return new RuntimeContext(target, monitor);
  }

  @Override
  public IServiceCall getServiceCall(IComputableResource resource) {
    if (resource.getServiceCall() != null) {
      return resource.getServiceCall();
    } else if (resource.getUrn() != null) {
      return UrnResolver.getServiceCall(resource.getUrn());
    } else if (resource.getExpression() != null) {
      return ExpressionResolver.getServiceCall(resource);
    } else if (resource.getLiteral() != null) {
      return LiteralStateResolver.getServiceCall(resource.getLiteral());
    }

    // temp
    throw new IllegalArgumentException("unsupported computable passed to getServiceCall()");
  }

  @Override
  public IStorage<?> distributeComputation(IStateResolver resolver, IStorage<?> data,
      IRuntimeContext context, IScale scale) {

    // TODO use a distributed loop unless the resolver implements some tag interface to notify
    // non-reentrant behavior
    // TODO if this is done, the next one must be local to each thread
    RuntimeContext ctx = new RuntimeContext((RuntimeContext) context);
    Collection<Pair<String, IStorage<?>>> variables = ctx.getStateDependentData();
    for (IScale state : scale) {
      data.set(state, resolver.resolve(data,
          variables.isEmpty() ? ctx : localizeContext(ctx, state, variables), state));
    }
    return data;

  }

  private IComputationContext localizeContext(RuntimeContext context, IScale state,
      Collection<Pair<String, IStorage<?>>> variables) {
    for (Pair<String, IStorage<?>> var : variables) {
      context.set(var.getFirst(), var.getSecond().get(state));
    }
    return context;
  }


}
