package org.integratedmodelling.klab.components.geospace.processing;

import java.util.List;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

public class FeatureExtractor implements IExpression, IInstantiator {

  @Override
  public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context)
      throws KlabException {

    // find source state(s) 
    // FIXME we don't need these - just the expression with scalar semantics, find state in inputs
    // that are qualities.
    IState sourceState =
        context.getArtifact(context.get("source-state", String.class), IState.class);
    if (sourceState == null) {
      throw new KlabResourceNotFoundException("feature extractor: source state "
          + context.get("source-state", String.class) + " not found or not a state");
    }

    // apply mask to build image
    
    // TODO build blobs
    return null;
  }

  @Override
  public Object eval(IParameters parameters, IComputationContext context) throws KlabException {
    return new FeatureExtractor();
  }

  @Override
  public IGeometry getGeometry() {
    return Geometry.create("#s2");
  }

}
