package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Platform;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.model.KActorsBehavior;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.KlabPermissions;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.data.Metadata;

public class Behavior implements IBehavior {

    static Set<String> viewAnnotations;
    static {
        viewAnnotations = new HashSet<>();
        viewAnnotations.add("panel");
        viewAnnotations.add("header");
        viewAnnotations.add("footer");
        viewAnnotations.add("modal");
        viewAnnotations.add("window");
    }

    /**
     * Pre-processed match value optimized for matching.
     * 
     * @author Ferd
     *
     */
    public static class Match implements ActionMatch {

        KActorsValue value;
        String matchName;

        public Match(IKActorsValue ikActorsValue, String matchName) {
            this.value = (KActorsValue) ikActorsValue;
            this.matchName = matchName;
        }

        public IKActorsValue getValue() {
            return value;
        }

        public String getMatchName() {
            return matchName;
        }

        /**
         * If true, no match value was given and the values will be set into system variables $ for
         * the entire match, plus $1..$n if multiple values. This is crucial as some matchers will
         * just imply but not contain the matched value. TODO CHECK this -
         * 
         * @return
         */
        public boolean isImplicit() {
            return value == null || value.getStatedValue() == null || value.getType() == IKActorsValue.Type.OBSERVABLE
                    || value.getType() == IKActorsValue.Type.ANNOTATION || value.getType() == IKActorsValue.Type.TYPE;
        }

        // Call only if isIdentifier() returns true
        public String getIdentifier() {
            return this.value.getStatedValue().toString();
        }

        /**
         * If true, this matches true and contains an identifier to set into the scope to the
         * matched value.
         * 
         * @param scope
         * @return
         */
        public boolean isIdentifier(IKActorsBehavior.Scope scope) {
            return this.value.getType() == IKActorsValue.Type.IDENTIFIER
                    && !scope.getSymbolTable().containsKey(this.value.getStatedValue());
        }

        public boolean matches(Object value, IKActorsBehavior.Scope scope) {
            return Actors.INSTANCE.matches(this.value, value, scope);
        }
    }

    IKActorsBehavior statement;
    Map<String, BehaviorAction> actions = new LinkedHashMap<>();
    IMetadata metadata = new Metadata();
    List<IAnnotation> annotations = new ArrayList<>();
    String projectId;
    Map<String, String> localizedSymbols;
    String locale;

    public Behavior(IKActorsBehavior statement, String locale, Map<String, String> localization) {
        this(statement);
        this.locale = locale;
        this.localizedSymbols = localization;
    }

    public Behavior(IKActorsBehavior statement) {

        this.statement = statement;
        this.projectId = statement.getProjectId();

        if (statement.getDescription() != null) {
            this.metadata.put(IMetadata.DC_COMMENT, statement.getDescription());
        }
        if (statement.getLabel() != null) {
            this.metadata.put(IMetadata.DC_LABEL, statement.getLabel());
        }
        for(IKActorsAction a : statement.getActions()) {
            BehaviorAction action = new BehaviorAction(a, this);
            actions.put(action.getId(), action);
        }
        Properties projectProperties = Resources.INSTANCE.getProject(this.projectId).getStatement().getProperties();
        if (projectProperties.containsKey(IKimProject.KLAB_CONFIGURATION_PERMISSIONS)) {
            this.metadata.put(IMetadata.IM_PERMISSIONS,
                    KlabPermissions.create(projectProperties.get(IKimProject.KLAB_CONFIGURATION_PERMISSIONS).toString()));
        }

    }

    private Behavior() {
        // empty behavior
        this.statement = new KActorsBehavior();
    }

    @Override
    public String getId() {
        return this.statement.getName();
    }

    @Override
    public String getName() {
        return statement.getName() + (this.locale == null ? "" : ("." + this.locale));
    }

    @Override
    public IKActorsBehavior getStatement() {
        return this.statement;
    }

    @Override
    public List<IKimObject> getChildren() {
        List<IKimObject> ret = new ArrayList<>();
        for(String id : actions.keySet()) {
            ret.add(actions.get(id));
        }
        return ret;
    }

    @Override
    public List<IAnnotation> getAnnotations() {
        return annotations;
    }

    @Override
    public boolean isDeprecated() {
        return statement.isDeprecated();
    }

    @Override
    public boolean isErrors() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IMetadata getMetadata() {
        return metadata;
    }

    @Override
    public Type getDestination() {
        return statement.getType();
    }

    @Override
    public List<Action> getActions(String... match) {
        List<Action> ret = new ArrayList<>();
        for(Action action : actions.values()) {
            if (match == null || match.length == 0) {
                ret.add(action);
                continue;
            }
            boolean ok = false;
            for(String m : match) {
                if (!ok && m.startsWith("@")) {
                    ok = Annotations.INSTANCE.hasAnnotation(action, m.substring(1));
                } else if (!ok) {
                    ok = m.equals(action.getId()) || m.equals(action.getName());
                }
                if (ok) {
                    break;
                }
            }
            if (ok) {
                ret.add(action);
            }
        }
        return ret;
    }

    @Override
    public Action getAction(String actionId) {
        return actions.get(actionId);
    }

    public static IBehavior empty() {
        return new Behavior();
    }

    @Override
    public Platform getPlatform() {
        return getStatement().getPlatform() == null ? Platform.ANY : getStatement().getPlatform();
    }

    @Override
    public String getProject() {
        return projectId;
    }

    @Override
    public Map<String, String> getLocalization() {
        return localizedSymbols;
    }

    @Override
    public String getLocale() {
        return this.locale;
    }

}
