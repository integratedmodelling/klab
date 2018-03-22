package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

public class ExpressionResolver implements IResolver<IDataArtifact>, IExpression {

  static final public String FUNCTION_ID = "klab.runtime.exec";

  // don't remove - only used as expression
  public ExpressionResolver() {
  }

  public ExpressionResolver(IParameters parameters) {
    // TODO Auto-generated constructor stub
  }

  public static IServiceCall getServiceCall(IComputableResource resource) {
    
    KimServiceCall ret = new KimServiceCall(FUNCTION_ID);
    ret.getParameters().put("code", resource.getExpression());
    // TODO conditions are upstream!
    return ret;
  }


  @Override
  public Object eval(IParameters parameters, IComputationContext context)
      throws KlabException {
    // TODO analyze code and create an appropriate contextualizer. May also see context for hand-coded instantiators.
    ILanguageProcessor processor = Extensions.INSTANCE.getLanguageProcessor(parameters.get("language", Extensions.DEFAULT_EXPRESSION_LANGUAGE));
    Descriptor descriptor = processor.describe(parameters.get("code", String.class), context);
    return new ExpressionResolver(parameters);
  }


  @Override
  public IGeometry getGeometry() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IDataArtifact resolve(IDataArtifact ret, IComputationContext context, IScale locator) {
    // TODO Auto-generated method stub
    return null;
  }


  
}
