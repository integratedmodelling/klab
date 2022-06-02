package org.integratedmodelling.klab.api.resolution;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;

/**
 * One or more resolution constraints may be added to a session through k.Actors to implement tests
 * or other scripts that require customizing the accessibility of specific models or resources.
 * <p>
 * The default behavior when black/whitelisting a model is to only set the constraint to resolve its
 * own observable. All other constraints are global unless otherwise specified.
 * 
 * @author Ferd
 *
 */
public interface IResolutionConstraint {

    /**
     * Return true if the model is for a different observable or it's for the passed observable but
     * part of a whitelist, is not blacklisted, comes from an accepted namespace, and uses accepted
     * resources.
     * 
     * @param model
     * @return
     */
    boolean accepts(IModel model, IObservable observable);

    /**
     * Check for blacklisted or whitelisted resources. Also used by
     * {@link #accepts(IModel, IObservable)} when the model passed to it contains resources.
     * 
     * @param model
     * @return
     */
    boolean accepts(IResource model);

    /**
     * 
     * @param namespace
     * @return
     */
    boolean accepts(INamespace namespace);

}
