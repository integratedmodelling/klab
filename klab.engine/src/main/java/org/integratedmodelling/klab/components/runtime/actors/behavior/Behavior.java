package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Platform;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.model.KActorsBehavior;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Range;

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
    public static class Match {

        KActorsValue value;

        public Match(IKActorsValue ikActorsValue) {
            this.value = (KActorsValue) ikActorsValue;
        }

        public IKActorsValue getValue() {
            return value;
        }

        private boolean notMatch(Object value) {
            return value == null || value instanceof Throwable || (value instanceof Boolean && !((Boolean) value));
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
        public boolean isIdentifier(Scope scope) {
            return this.value.getType() == IKActorsValue.Type.IDENTIFIER
                    && !scope.getSymbolTable().containsKey(this.value.getStatedValue());
        }

        public boolean matches(Object value, Scope scope) {
            switch(this.value.getType()) {
            case ANNOTATION:
                for (IAnnotation annotation : Annotations.INSTANCE.collectAnnotations(value)) {
                    if (annotation.getName().equals(this.value.getStatedValue())) {
                        scope.symbolTable.put(annotation.getName(), annotation);
                        return true;
                    }
                }
                break;
            case ANYTHING:
                return true;
            case ANYVALUE:
                return value != null && !(value instanceof Throwable);
            case ANYTRUE:
                boolean ret = value != null && !(value instanceof Throwable) && !(value instanceof Boolean && !((Boolean) value));
                // if (ret) {
                // scope.symbolTable.put("$", value);
                // if (value instanceof Collection) {
                // int n = 1;
                // for (Object v : ((Collection<?>)value)) {
                // scope.symbolTable.put("$" + (n++), v);
                // }
                // }
                // }
                return ret;
            case BOOLEAN:
                return value instanceof Boolean && value.equals(this.value.getStatedValue());
            case CLASS:
                break;
            case DATE:
                break;
            case EXPRESSION:
                System.out.println("ACH AN EXPRESSION");
                break;
            case IDENTIFIER:
                if (scope.symbolTable.containsKey(this.value.getStatedValue())) {
                    return this.value.getStatedValue().equals(scope.symbolTable.get(value));
                }
                if (!notMatch(value)) {
                    // NO - if defined in scope, match to its value, else just return true.
                    // scope.symbolTable.put(this.value.getValue().toString(), value);
                    return true;
                }
                break;
            case SET:
                // TODO OR match for values in list
                break;
            case LIST:
                // TODO multi-identifier match
                break;
            case MAP:
                break;
            case NODATA:
                return value == null || value instanceof Number && Double.isNaN(((Number) value).doubleValue());
            case NUMBER:
                return value instanceof Number && value.equals(this.value.getStatedValue());
            case NUMBERED_PATTERN:
                break;
            case OBSERVABLE:
                Object obj = this.value.evaluate(scope, scope.getIdentity(), true);
                if (obj instanceof IObservable) {
                    if (value instanceof IObservation) {
                        return ((IObservation) value).getObservable().resolves((IObservable) obj, null);
                    }
                }
                break;
            case QUANTITY:
                break;
            case RANGE:
                return value instanceof Number
                        && ((Range) (this.value.getStatedValue())).contains(((Number) value).doubleValue());
            case REGEXP:
                break;
            case STRING:
                return value instanceof String && value.equals(this.value.getStatedValue());
            case TABLE:
                break;
            case TYPE:
                return value != null && (this.value.getStatedValue().equals(value.getClass().getCanonicalName())
                        || this.value.getStatedValue().equals(Path.getLast(value.getClass().getCanonicalName(), '.')));
            case URN:
                break;
            case ERROR:
                // match any error? any literal for that?
                return value instanceof Throwable;
            case OBSERVATION:
                // might
                break;
            case TREE:
                break;
            case CONSTANT:
                return (value instanceof Enum && ((Enum<?>) value).name().toUpperCase().equals(this.value.getStatedValue()))
                        || (value instanceof String && ((String) value).equals(this.value.getStatedValue()));
            case EMPTY:
                return value == null || (value instanceof Collection && ((Collection<?>) value).isEmpty())
                        || (value instanceof String && ((String) value).isEmpty())
                        || (value instanceof IConcept && ((IConcept) value).is(IKimConcept.Type.NOTHING))
                        || (value instanceof IObservable && ((IObservable) value).is(IKimConcept.Type.NOTHING))
                        || (value instanceof IArtifact && !(value instanceof IObservationGroup) && ((IArtifact) value).isEmpty())
                        || (value instanceof IObservation && ((Observation) value).getObservable().is(IKimConcept.Type.NOTHING));
            case OBJECT:
                break;
            default:
                break;
            }
            return false;
        }
    }

    IKActorsBehavior statement;
    Map<String, BehaviorAction> actions = new LinkedHashMap<>();
    IMetadata metadata = new Metadata();
    List<IAnnotation> annotations = new ArrayList<>();
    String projectId;

    public Behavior(IKActorsBehavior statement) {
        this.statement = statement;
        this.projectId = statement.getProjectId();
        for (IKActorsAction a : statement.getActions()) {
            BehaviorAction action = new BehaviorAction(a, this);
            actions.put(action.getId(), action);
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
        return statement.getName();
    }

    @Override
    public IKActorsBehavior getStatement() {
        return this.statement;
    }

    @Override
    public List<IKimObject> getChildren() {
        List<IKimObject> ret = new ArrayList<>();
        for (String id : actions.keySet()) {
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
        for (Action action : actions.values()) {
            if (match == null || match.length == 0) {
                ret.add(action);
                continue;
            }
            boolean ok = false;
            for (String m : match) {
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

}
