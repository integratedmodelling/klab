package org.integratedmodelling.klab.api.runtime;

import java.util.concurrent.Future;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IRuntimeProvider {

  /**
   * The main executor for a k.LAB dataflow.
   * 
   * @param actuator
   * @param context
   * @return
   * @throws KlabException 
   */
  Future<IArtifact> compute(IActuator actuator, IRuntimeContext context, IMonitor monitor) throws KlabException;
  
  /**
   * Create a suitable runtime context for the passed root subject.
   * 
   * @param rootSubject
   * @return
   */
  IRuntimeContext createRuntimeContext(ISubject rootSubject);

  /**
   * Mandatory ID of the execution function for k.IM expression code.
   */
  static final public String EXECUTE_FUNCTION_ID                           = "klab.runtime.exec";
  static final public String EXECUTE_FUNCTION_PARAMETER_CODE               = "code";
  static final public String EXECUTE_FUNCTION_PARAMETER_LANGUAGE           = "language";
  static final public String EXECUTE_FUNCTION_PARAMETER_CONDITION          = "ifcondition";
  static final public String EXECUTE_FUNCTION_PARAMETER_NEGATIVE_CONDITION = "unlesscondition";

  /**
   * A service call whose only purpose is to carry a literal. Doesn't even get compiled into KDL
   * (its source code is the literal itself), so no need for an implementation.
   */
  static final public String LITERAL_FUNCTION_ID                           = "klab.literal";

}
