package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Map;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public class ExpressionResolver implements IResolver<IStorage<?>>, IExpression {

  static final public String FUNCTION_ID = "klab.runtime.exec";

  // don't remove - only used as expression
  public ExpressionResolver() {
  }

  public ExpressionResolver(Map<String, Object> parameters) {
    // TODO Auto-generated constructor stub
  }

  public static IServiceCall getServiceCall(IComputableResource resource) {
    
    KimServiceCall ret = new KimServiceCall(FUNCTION_ID);
    ret.getParameters().put("code", resource.getExpression());
    // TODO conditions are upstream!
    return ret;
  }


  @Override
  public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
      throws KlabException {
    // TODO analyze code and create an appropriate contextualizer. May also see context for hand-coded instantiators.
    return new ExpressionResolver(parameters);
  }


  @Override
  public IGeometry getGeometry() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IStorage<?> resolve(IStorage<?> ret, IComputationContext context, IScale locator) {
    // TODO Auto-generated method stub
    return null;
  }


  
}
