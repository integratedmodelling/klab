package org.integratedmodelling.klab.dataflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.utils.Pair;

/**
 * A container for an observable concept and a resolution mode with appropriate equality assessment
 * that matches interoperability rules (only consider the concept definition and any value
 * operators, but no units, range, currency or name). Used to create the dependency tree in the
 * contextualization scope. Can also carry arbitrary data through a generic map.
 * 
 * TODO this must evolve into the implementation of IProvenance.Activity, returned by
 * Actuator.compute() and Scheduler.run().
 * 
 * @author Ferd
 *
 */
public class ObservedConcept {

    private IObservable observable = null;
    private IResolutionScope.Mode mode;
    private IObservable observationContext = null;
    private ResolutionScope scope;

    private Map<String, Object> data = new HashMap<>();
    String conceptDeclaration = null;
    String contextConceptDeclaration = null;
    private List<Pair<ValueOperator, Object>> valueOperators;

    public ObservedConcept(IConcept observable) {
        this(Observable.promote(observable),
                observable.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION, (IObservable) null);
    }

    public ObservedConcept(IObservable observable) {
        this(observable, observable.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION,
                (IObservable) null);
    }

    public ObservedConcept(IObservable observable, IResolutionScope.Mode mode,
            IObservable observationContext) {
        this.observable = observable;
        this.observationContext = observationContext;
        if (!observable.getValueOperators().isEmpty()) {
            this.valueOperators = observable.getValueOperators();
        }
        if (observable.is(Type.ROLE)) {
            ((Observable) observable).setGeneric(true);
        }
        this.mode = mode;
        this.conceptDeclaration = observable.getType().getDefinition();
        if (this.observationContext != null) {
            this.contextConceptDeclaration = this.observationContext.getType().getDefinition();
        }
    }

    public ObservedConcept(IObservable observable, IResolutionScope.Mode mode) {
        this(observable, mode, (IObservable) null);
    }
            
    /**
     * NOTE: this does NOT set the context observable from the scope!
     * 
     * @param observable
     * @param mode
     * @param scope
     */
    public ObservedConcept(IObservable observable, IResolutionScope.Mode mode, ResolutionScope scope) {
        this(observable, mode, (IObservable)null/*scope.getContext() == null ? null : scope.getContext().getObservable()*/);
        this.scope = scope;
    }

    public IObservable getObservable() {
        return observable;
    }

    public IConcept getConcept() {
        return observable.getType();
    }

    public IResolutionScope.Mode getMode() {
        return mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(conceptDeclaration, contextConceptDeclaration, mode, valueOperators);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ObservedConcept other = (ObservedConcept) obj;
        return Objects.equals(conceptDeclaration, other.conceptDeclaration)
                && Objects.equals(contextConceptDeclaration, other.contextConceptDeclaration)
                && mode == other.mode && Objects.equals(valueOperators, other.valueOperators);
    }

    /**
     * Arbitrary data, such as an actuator to repeat the observation when dependencies change.
     * 
     * @return
     */
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "<" + observable + " " + mode.name().toLowerCase() + ">";
    }

    /**
     * The scope must be set explicitly or is null: it is used only to remember the original scope
     * during resolution if there are further resolution to be made after the first, so that we can
     * use the same namespace and conditions of the original resolution.
     * 
     * @return
     */
    public ResolutionScope getScope() {
        return scope;
    }

    public void setScope(ResolutionScope scope) {
        this.scope = scope;
    }

}
