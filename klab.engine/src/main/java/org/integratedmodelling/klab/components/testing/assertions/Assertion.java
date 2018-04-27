package org.integratedmodelling.klab.components.testing.assertions;

import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * An assertion is a simple function that takes the context subject and checks the
 * parameters, returning true (OK) or false. Exceptions are equivalent to returning
 * false, with an additional message.
 * 
 * @author ferdinando.villa
 *
 */
public interface Assertion {

  public boolean evaluate(ISubject context, Parameters parameters) throws Exception;
  
}
