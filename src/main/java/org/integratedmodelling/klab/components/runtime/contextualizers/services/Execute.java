package org.integratedmodelling.klab.components.runtime.contextualizers.services;

import java.util.Map;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Expression executor service. Used to compile arbitrary expression code into a dataflow. Performs
 * a code analysis and according to the result, produces either a state resolver (distributing the
 * expression over the context) or a regular resolver that will be run only once over the target
 * object.
 * 
 * @author Ferd
 *
 */
public class Execute implements IExpression {

  @Override
  public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
      throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IGeometry getGeometry() {
    // TODO Auto-generated method stub
    return null;
  }

}
