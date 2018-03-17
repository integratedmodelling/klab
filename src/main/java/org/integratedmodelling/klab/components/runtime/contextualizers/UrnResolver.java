package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Map;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public class UrnResolver implements IExpression, IResolver<IObservationData> {

  public final static String FUNCTION_ID = "klab.resolve.urn";
  
  // don't remove - only used as expression
  public UrnResolver() {}
  
  public UrnResolver(String urn) {
    // TODO Auto-generated constructor stub
  }

  public static IServiceCall getServiceCall(String urn) {
    return new KimServiceCall(FUNCTION_ID, "urn", urn);
  }

  @Override
  public IObservationData resolve(IObservationData observation, IComputationContext context, ILocator locator) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
      throws KlabException {
    // TODO resolve URN, generate the appropriate contextualizer for type and geometry
    return new UrnResolver(parameters.get("urn").toString());
  }

  @Override
  public IGeometry getGeometry() {
    // TODO Auto-generated method stub
    return null;
  }

}
