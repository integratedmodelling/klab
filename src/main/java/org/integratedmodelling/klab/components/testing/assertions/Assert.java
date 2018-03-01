package org.integratedmodelling.klab.components.testing.assertions;

import java.util.Map;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public class Assert implements IExpression {
  
  @Override
  public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
      throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IGeometry getGeometry() {
    return null;
  }

}
