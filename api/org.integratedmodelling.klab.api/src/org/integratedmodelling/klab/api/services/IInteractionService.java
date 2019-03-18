package org.integratedmodelling.klab.api.services;

import java.util.Collection;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IComputableResource.InteractiveParameter;

/**
 * All functionalities related to interacting with a human user, waiting for
 * answers etc., besides the sending of log messages through the monitor.
 * 
 * @author ferdinando.villa
 *
 */
public interface IInteractionService {

	Collection<InteractiveParameter> getInteractiveParameters(IComputableResource computable);

}
