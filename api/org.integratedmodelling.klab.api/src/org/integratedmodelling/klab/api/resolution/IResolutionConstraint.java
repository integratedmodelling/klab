package org.integratedmodelling.klab.api.resolution;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.model.IModel;

/**
 * One or more resolution constraints may be added to a session through k.Actors
 * to implement tests or other scripts that require customizing the
 * accessibility of specific models or resources.
 * 
 * @author Ferd
 *
 */
public interface IResolutionConstraint {

	boolean accepts(IModel model);

	boolean accepts(IResource model);

}
