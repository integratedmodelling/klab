package org.integratedmodelling.klab.engine.runtime.code;

import java.util.ArrayList;
import java.util.Collection;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public abstract class Expression implements IExpression {

  // A dummy context to use when we don't have a context to pass
  private static class Context extends Parameters implements IComputationContext {

    private IMonitor monitor;
    private INamespace namespace;
    
    Context(IMonitor monitor) {
      this.monitor = monitor;
    }

    Context(IMonitor monitor, INamespace namespace) {
      this.monitor = monitor;
      this.namespace = namespace;
    }
    
    @Override
    public INamespace getNamespace() {
      return this.namespace;
    }

    @Override
    public IProvenance getProvenance() {
      return null;
    }

    @Override
    public IEventBus getEventBus() {
      return null;
    }

    @Override
    public Collection<IRelationship> getOutgoingRelationships(ISubject observation) {
      return new ArrayList<>();
    }

    @Override
    public Collection<IRelationship> getIncomingRelationships(ISubject observation) {
      return new ArrayList<>();
    }

    @Override
    public Collection<ISubject> getAllSubjects() {
      return new ArrayList<>();
    }

    @Override
    public IArtifact getData(String localName) {
      return null;
    }

    @Override
    public IMonitor getMonitor() {
      return monitor;
    }

    @Override
    public IObjectArtifact newObservation(IObservable observable, IGeometry geometry) {
      return null;
    }

    @Override
    public IObjectArtifact newRelationship(IObservable observable, IGeometry geometry,
        IObjectArtifact source, IObjectArtifact target) {
      return null;
    }
  }
  
  public static IComputationContext emptyContext(IMonitor monitor) {
    return new Context(monitor);
  }
  
  public static IComputationContext emptyContext(IMonitor monitor, INamespace namespace) {
    return new Context(monitor, namespace);
  }    
}
