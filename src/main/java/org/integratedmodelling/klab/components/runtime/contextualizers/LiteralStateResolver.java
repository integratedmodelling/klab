package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

public class LiteralStateResolver implements IStateResolver, IExpression {
  
  static final public String FUNCTION_ID = "klab.runtime.literal";

  Object                     value;

  // don't remove - only used as expression
  public LiteralStateResolver() {}

  public LiteralStateResolver(Object value) {
    this.value = value;
  }

  public static IServiceCall getServiceCall(Object literal) {
    return new LiteralFunction(literal);
  }


  /**
   * A literal function produces a literal and takes no arguments. It is used to simplify the
   * encoding in dataflows, and its KDL code equivalent is the literal itself.
   * 
   * @author ferdinando.villa
   *
   */
  public static class LiteralFunction extends KimServiceCall {

    static final public String FUNCTION_ID = "klab.runtime.literal";

    Object                     value;

    public LiteralFunction(Object value) {
      super(null);
      this.value = value;
      setName(FUNCTION_ID);
      getParameters().put("val", value);
    }

    private static final long serialVersionUID = -5190145577904822153L;

    @Override
    public String getSourceCode() {
      return value == null ? "unknown" : value.toString().trim();
    }

  }

  @Override
  public Object eval(IParameters parameters, IComputationContext context)
      throws KlabException {
    return new LiteralStateResolver(parameters.get("val"));
  }

  @Override
  public IGeometry getGeometry() {
    return Geometry.scalar();
  }

  @Override
  public Object resolve(IDataArtifact observation, IComputationContext context, IScale locator) {
    return value;
  }
}
