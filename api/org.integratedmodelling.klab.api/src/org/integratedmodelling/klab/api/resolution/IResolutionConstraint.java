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

	/**
	 * Return true if the model is part of a whitelist, is not blacklisted, and uses
	 * accepted resources.
	 * 
	 * @param model
	 * @return
	 */
	boolean accepts(IModel model);

	/**
	 * Check for blacklisted or whitelisted resources. Also used by
	 * {@link #accepts(IModel)} when the model passed to it contains resources.
	 * 
	 * @param model
	 * @return
	 */
	boolean accepts(IResource model);

}
