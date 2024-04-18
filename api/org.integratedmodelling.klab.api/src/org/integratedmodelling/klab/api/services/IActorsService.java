package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Scope;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity.Reference;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.rest.Localization;

/**
 * The actors service keeps the k.Actor language parser and administers the actor runtime subsystem.
 * 
 * @author Ferd
 *
 */
public interface IActorsService {

    /**
     * Return the behavior identified by the passed string. Returns a pre-instantiated prototype to
     * be used only if the behavior is run in a single instance.
     * 
     * @param id
     * @return a behavior or null
     */
    IBehavior getBehavior(String id);

    /**
     * Return a new copy of the behavior, suitable for concurrent use. Behavior must have been
     * registered with a valid prototype in advance of the call.
     * 
     * @param behaviorId
     * @return
     */
    IBehavior newBehavior(String behaviorId);

    /**
     * Read a k.Actor specification from a URL.
     * 
     * @param url
     * @return the actor specification
     */
    IKActorsBehavior declare(URL url);

    /**
     * Read a k.Actor specification from a file.
     * 
     * @param file
     * @return the actor specification
     */
    IKActorsBehavior declare(File file);

    /**
     * Read a k.Actor specification from a stream.
     * 
     * @param file
     * @return the actor specification
     */
    IKActorsBehavior declare(InputStream file);

    /**
     * 
     * @return
     */
    Collection<String> getBehaviorIds();

    /**
     * 
     * @return
     */
    Collection<String> getPublicApps();
    
    /**
     * @param user
     * @return
     */
    Collection<String> getPublicApps(IUserIdentity user);

    /**
     * 
     * @param type
     * @return
     */
    Collection<String> getBehaviorIds(Type type);

    Reference createUserActor(IEngineUserIdentity user);

    /**
     * Evaluate a k.Actors value in the passed scope.
     * 
     * @param kActorsValue
     * @param identity
     * @param scope
     * @return
     */
    Object evaluate(IKActorsValue kActorsValue, IIdentity identity, Scope scope);

    /**
     * Get the localization descriptors using the companion file, if any. Try to also provide app
     * localized descriptions and labels (docstring). At least one localization should always be
     * provided, defaulting to english with the default unlocalized docstrings.
     * 
     * @param behavior
     * @return
     */
    List<Localization> getLocalizations(String behavior);

}