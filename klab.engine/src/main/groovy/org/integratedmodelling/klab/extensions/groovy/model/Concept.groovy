package org.integratedmodelling.klab.extensions.groovy.model;


import org.integratedmodelling.kim.api.IKimConcept
import org.integratedmodelling.klab.Concepts
import org.integratedmodelling.klab.Roles
import org.integratedmodelling.klab.Traits
import org.integratedmodelling.klab.api.knowledge.IConcept
import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.api.observations.scale.IExtent
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation
import org.integratedmodelling.klab.components.runtime.observations.Observation
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException
import org.integratedmodelling.klab.exceptions.KlabValidationException
import org.integratedmodelling.klab.extensions.groovy.ActionBase


/**
 * Wraps an observable concept, and potentially adds a context for observation when
 * the user wants a different one from the default (added with the <= operator).
 * 
 * The is() method is smarter than IConcept.is() and adapts to the type of
 * comparison based on the passed concept.
 * 
 * @author Ferd
 *
 */
public class Concept {

    private IConcept concept;
    IObservation roleContext;
    Binding binding;
    static class Def {
        Concept of;
        Concept within;
        List<Concept> traits;
        List<Concept> roles;
        Concept by;
        Concept downTo;
    };

    Def defs = null;
    Collection<IExtent> searchExtents = new ArrayList<>();
    Collection<String> scenarios = new ArrayList<>();
    boolean resolved = false;

    Concept(IConcept c, Binding binding) {
        this.concept = c;
        this.binding = binding;
    }

    Concept(IConcept c, Binding binding, IObservation roleContext) {
        this.concept = c;
        this.binding = binding;
        this.roleContext = roleContext;
    }

    Concept(Concept obs, List<Concept> traits, List<Concept> roles, Binding binding) {
        this.binding = binding;
        this.concept = obs.concept;
        this.defs = new Def(traits: traits, roles: roles);
    }

    def of(Concept of) {
        if (this.defs == null) {
            this.defs = new Def();
        }
        this.defs.of = of;
    }

    def within(Concept of) {
        if (this.defs == null) {
            this.defs = new Def();
        }
        this.defs.within = of;
    }

    def getConcept() {
        resolve();
        return concept;
    }

    def by(Concept of) {
        if (this.defs == null) {
            this.defs = new Def();
        }
        this.defs.by = of;
    }

    def getObservable() {

        if (!concept.is(IKimConcept.Type.ROLE)) {
            throw new KlabValidationException("getObservable can only be called on a role", ActionBase.getArtifact(binding));
        }

        IConcept observable = Roles.getObservableWithRole(concept, null);
        if (observable == null) {
            return null;
        }

        if (observable.isAbstract()) {
            throw new KlabValidationException(
            "role " + this +
            " applies to an abstract observable (" + observable +
            ") and can not be used to produce an observable", ActionBase.getArtifact(binding));
        }

        return new Concept(observable, binding);
    }

    def getRole(Concept baseRole) {
        if (!baseRole.concept.is(IKimConcept.Type.ROLE)) {
            throw new KlabValidationException("getRole can only be called with a role parameter", ActionBase.getArtifact(binding));
        }
        for (IConcept c : Roles.getRoles(concept)) {
            if (c.is(baseRole.concept)) {
                return new Concept(c, binding);
            }
        }
        return null;
    }

    def implied(Concept baseRole) {
        if (!baseRole.concept.is(IKimConcept.Type.ROLE) || !concept.is(IKimConcept.Type.ROLE)) {
            throw new KlabValidationException("implied() can only be called on a role and with a role parameter", ActionBase.getArtifact(binding));
        }
        for (IConcept c : Roles.getImpliedObservableRoles(concept)) {
            if (c.is(baseRole.concept)) {
                return new Concept(c, binding);
            }
        }
        return null;
    }

    def getName() {
        return concept.getName();
    }

    def getTrait(Concept baseTrait) {
        for (IConcept c : Traits.getTraits(concept)) {
            if (c.is(baseTrait.concept)) {
                return new Concept(c, binding);
            }
        }
        return null;
    }

    def downTo(Concept of) {
        if (this.defs == null) {
            this.defs = new Def();
        }
        this.defs.downTo = of;
    }

    public String toString() {
        return Concepts.getDisplayName(getConcept());
    }

    def isa(Object o) {
        resolve();
        IConcept sem = toConcept(o);
        boolean ret = concept.is(sem);
		// TODO
//        if (!ret && (sem.is(IKimConcept.Type.OBSERVABLE) || sem.is(IKimConcept.Type.TRAIT) || sem.is(IKimConcept.Type.ROLE))) {
//            if (sem.is(IKimConcept.Type.TRAIT)) {
//                return Traits.hasTrait(concept, sem.getType());
//            } else if (sem.is(IKimConcept.Type.ROLE)) {
//                if (Roles.hasRole(concept, sem.getType())) {
//                    return true;
//                }
//                if (o instanceof Concept && ((Concept)o).roleContext != null) {
//                    if (((Observation)((Concept)o).roleContext).getExplicitRoles().contains(sem.getType())) {
//                        return true;
//                    }
//                }
//            }
//        }
        return ret;
    }


    def isThing() {
        resolve();
        return concept.is(IKimConcept.Type.SUBJECT);
    }

    def isQuality() {
        resolve();
        return concept.is(IKimConcept.Type.QUALITY);
    }

    def isProcess() {
        resolve();
        return concept.is(IKimConcept.Type.PROCESS);
    }

    def isEvent() {
        resolve();
        return concept.is(IKimConcept.Type.EVENT);
    }

    def isRelationship() {
        resolve();
        return concept.is(IKimConcept.Type.RELATIONSHIP);
    }

    def isRole() {
        resolve();
        return concept.is(IKimConcept.Type.ROLE);
    }

    def isTrait() {
        resolve();
        return concept.is(IKimConcept.Type.TRAIT);
    }


    /**
     * Use <concept> *  extent/observation to lookup the concept in a different space or time than
     * the default. Use <concept> *  "scenario" to apply a scenario.
     * 
     * @param o
     * @return
     */
    def multiply(Object o) {

        if (o instanceof String) {
            scenarios.add((String)o);
            return this;
        }

        if (o instanceof DirectObservation) {
            for (IExtent ext : ((DirectObservation)o).obs.getScale()) {
                searchExtents.add(ext);
            }
            return this;
        }

        if (o instanceof IDirectObservation) {
            for (IExtent ext : ((IDirectObservation)o).getScale()) {
                searchExtents.add(ext);
            }
            return this;
        }

        if (!(o instanceof IExtent)) {
            throw new KlabValidationException("cannot use the * operator on a concept with an argument that is not an extent", DefaultAction.getArtifact(binding));
        }
        searchExtents.add((IExtent)o);
        return this;
    }

    /**
     * Concept / observation contextualizes a role to an observation, meaning "role in observation"
     * 
     * TODO this must set the returned concept to the first concrete role that is the passed in
     * the (recursive) implication closure of the roles of the observation's observable.
     * Parameter can also be knowledge.
     * 
     * @param obs
     * @return
     */
    def div(Object obs) {
        if (!concept.is(IKimConcept.Type.ROLE)) {
            throw new KlabValidationException("role context operator: target concept " + concept + " is not a role", DefaultAction.getArtifact(binding));
        }
        IConcept observable = null;
        if (obs instanceof Concept) {
            observable = ((Concept)obs).concept;
        } else if (obs instanceof Observation) {
            observable = ((Observation)obs).obs.getObservable().getSemantics().getType();
        } else {
            throw new KlabValidationException("role context operator: argument must be an observation", DefaultAction.getArtifact(binding));
        }
        
        IConcept actual = Roles.getImpliedRole(concept, observable);
        if (actual != null) {
           return new Concept(actual, binding);
        }
//        throw new KlabRuntimeException("role " + concept + " has no concrete specification within " + obs, DefaultAction.getArtifact(binding));
    }

    private void resolve() {
        if (!resolved) {
            resolved = true;
            if (defs != null) {
				throw new KlabUnimplementedException("resolution of concepts within Groovy expressions currently unimplemented");
//                concept = Observables.declareObservable(
//                        concept,
//                        defs.traits == null ? null : unpack(defs.traits),
//                        defs.within == null ? null : defs.within.concept,
//                        defs.of == null ? null : defs.of.concept,
//                        defs.roles == null ? null : unpack(defs.roles),
//                        defs.by == null ? null : defs.by.concept,
//                        defs.downTo == null ? null : defs.downTo.concept,
//                        KLAB.REASONER.getOntology(),
//                        0);
            }
        }
    }

    private Collection<IConcept> unpack(Collection<Concept> c) {
        List<IConcept> ret = new ArrayList<>();
        for (Concept cc : c) {
            ret.add(cc.concept);
        }
        return ret;
    }

    private IConcept toConcept(Object o) {

        if (o instanceof IConcept) {
            return (IConcept) o;
        }

        if (o instanceof Concept) {
            ((Concept)o).resolve();
            return ((Concept)o).concept;
        }

        return null;
    }
}
