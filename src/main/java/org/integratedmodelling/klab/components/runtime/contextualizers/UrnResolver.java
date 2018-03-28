package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

public class UrnResolver implements IExpression, IResolver<IArtifact> {

  public final static String FUNCTION_ID = "klab.resolve.urn";
  
  // don't remove - only used as expression
  public UrnResolver() {}
  
  public UrnResolver(String urn) {
    // TODO Auto-generated constructor stub
  }

  public static IServiceCall getServiceCall(String urn) {
    return KimServiceCall.create(FUNCTION_ID, "urn", urn);
  }

  @Override
  public IArtifact resolve(IArtifact observation, IComputationContext context) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object eval(IParameters parameters, IComputationContext context)
      throws KlabException {
    // TODO resolve URN, generate the appropriate contextualizer for type and geometry
    return new UrnResolver(parameters.get("urn", String.class));
  }

  @Override
  public IGeometry getGeometry() {
    // TODO Auto-generated method stub
    return null;
  }

}
