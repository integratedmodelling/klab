package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.DataflowBuilder;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.resolution.ResolutionScope.Link;

public enum DataflowCompiler {

  INSTANCE;
  
  @SuppressWarnings("unchecked")
  public <T extends IArtifact> Dataflow<T> compile(ResolutionScope scope, Class<T> cls)
      throws KlabException {
    
    String name = scope.findObservable().getLocalName();
    
    Dataflow.Builder builder = new DataflowBuilder<T>(name, cls)
        .withScale(scope.getScale())
        .withCoverage(scope.getCoverage())
        .within(scope.getContext());
   
    if (scope.getMode() == Mode.RESOLUTION) {
      builder = builder.instantiating(scope.findObservable(), scope.getResolutionNamespace());
    }
    
    for (Link link : scope.links) {
      builder.addDependency(link.getTarget().getResolvable(), link.getSource().getResolvable(), link.getTarget());
    }
    
    return (Dataflow<T>) builder.build();
  }
}
