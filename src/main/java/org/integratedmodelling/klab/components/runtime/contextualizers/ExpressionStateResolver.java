package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

public class ExpressionStateResolver implements IStateResolver {

  IExpression expression;
  IExpression condition;
  
  public ExpressionStateResolver(Descriptor descriptor, Descriptor condition, IParameters parameters, IComputationContext context) {
    this.expression = descriptor.compile();
    if (condition != null) {
      this.condition = condition.compile();
    }
  }

  @Override
  public Object resolve(IDataArtifact ret, IComputationContext context, IScale locator) {
    return null;
  }


}
