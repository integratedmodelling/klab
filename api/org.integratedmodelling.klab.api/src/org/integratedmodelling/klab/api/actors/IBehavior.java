package org.integratedmodelling.klab.api.actors;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Scope;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IKimObject;

/**
 * The result of parsing an actor specification within the engine. Provides operational
 * configuration for all k.LAB actors.
 * <p>
 * Localized instances of localizable behaviors appear to the engine as independent behaviors, with
 * the language ISO code appended to the name path (.en) returned by {@link #getName()}. The
 * {@link #getId()} method continues to return the unlocalized name.
 * 
 * @author Ferd
 *
 */
public interface IBehavior extends IKimObject {

    public interface Action extends IKimObject {

        IBehavior getBehavior();

        @Override
        IKActorsAction getStatement();

        List<String> getFormalArguments();

        /**
         * Declared as <code>function</code> instead of <code>action</code>, will behave
         * functionally and return any values instead of firing them.
         * 
         * @return
         */
        boolean isFunction();

    }
    
    /**
     * Match data for action listeners.
     * 
     * @author Ferd
     *
     */
    public interface ActionMatch {

        boolean isIdentifier(Scope ret);

        String getIdentifier();

        boolean isImplicit();

        String getMatchName();
        
    }

    @Override
    IKActorsBehavior getStatement();

    /**
     * Metadata, following the (forthcoming) actor-specific schema in IMetadata.Schema. For now
     * limited to {@link IMetadata#DC_COMMENT} for the docstring.
     * 
     * @return
     */
    IMetadata getMetadata();

    /**
     * Who this is for.
     * 
     * @return
     */
    IKActorsBehavior.Type getDestination();

    /**
     * What this is for.
     * 
     * @return
     */
    IKActorsBehavior.Platform getPlatform();

    /**
     * Get all the actions in order of declaration. If a string is passed, match it to the action
     * name; if it starts with "@", match it to the ID of annotations associated to it. All
     * parameters are in OR.
     *
     * 
     * @return
     */
    List<Action> getActions(String... match);

    /**
     * Get an action by name.
     * 
     * @param actionId local name of the action (no path)
     * @return the action or null
     */
    Action getAction(String actionId);

    /**
     * The name of the project this behavior is declared in. May be null only in user behaviors.
     * 
     * @return
     */
    String getProject();

    /**
     * If the behavior has a specified locale, return the mapping of localized symbols to their
     * string values.
     * 
     * @return
     */
    Map<String, String> getLocalization();

    /**
     * If this is not null, {@link #getLocalization()} must also be not null, and the locale is the
     * last part of the actor path, whose prototype is the leading path of the name.
     * 
     * @return
     */
    String getLocale();

}
