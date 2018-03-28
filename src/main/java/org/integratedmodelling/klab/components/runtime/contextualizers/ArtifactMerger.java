package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.List;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

public class ArtifactMerger implements IExpression {

  static final public String FUNCTION_ID = "klab.runtime.merge";
  
  public static IServiceCall getServiceCall(IObservable observable, List<String> artifacts) {

    IServiceCall ret = KimServiceCall.create(FUNCTION_ID);
    ret.getParameters().put("artifacts", artifacts);
    return ret;
  }
  
  public ArtifactMerger() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public Object eval(IParameters parameters, IComputationContext context) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

}
