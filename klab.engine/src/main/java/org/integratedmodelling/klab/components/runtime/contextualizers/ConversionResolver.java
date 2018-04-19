package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.eclipse.xtext.util.Pair;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.data.storage.MediatingState;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ConversionResolver implements IResolver<IState>, IExpression {

  static final public String FUNCTION_ID = "klab.runtime.convert";

  IValueMediator             from;
  IValueMediator             to;

  // don't remove - only used as expression
  public ConversionResolver() {}

  public ConversionResolver(IValueMediator from, IValueMediator to) {
    this.from = from;
    this.to = to;
  }

  public static IServiceCall getServiceCall(Pair<IValueMediator, IValueMediator> literal) throws KlabValidationException {
    if (!literal.getSecond().isCompatible(literal.getFirst())) {
      throw new KlabValidationException("mediator '" + literal.getFirst()
          + "' cannot be converted to '" + literal.getSecond() + "'");
    }
    return KimServiceCall.create(FUNCTION_ID, "original", literal.getFirst(), "target",
        literal.getSecond());
  }

  @Override
  public Object eval(IParameters parameters, IComputationContext context) throws KlabException {
    return new ConversionResolver((IValueMediator) parameters.get("original"),
        (IValueMediator) parameters.get("target"));
  }

  @Override
  public IGeometry getGeometry() {
    return Geometry.scalar();
  }

  @Override
  public IState resolve(IState ret, IComputationContext context) throws KlabException {
    return new MediatingState(ret, (RuntimeContext)context, from, to);
  }

  
}
