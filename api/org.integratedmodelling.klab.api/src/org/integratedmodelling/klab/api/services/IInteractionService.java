package org.integratedmodelling.klab.api.services;

import java.util.Collection;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IContextualizable.InteractiveParameter;
import org.integratedmodelling.klab.api.model.IModel;

/**
 * All functionalities related to interacting with a human user, waiting for
 * answers etc., besides the sending of log messages through the monitor.
 * 
 * @author ferdinando.villa
 *
 */
public interface IInteractionService {

	Collection<InteractiveParameter> getInteractiveParameters(IContextualizable computable, IModel model);

}
