package org.integratedmodelling.klab.owl.syntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.SemanticModifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;

/**
 * Defines the acceptable semantic tokens in any given state of the specification of a semantic
 * expression and the lexical context in which it's being specified.
 * 
 * @author Ferd
 *
 */
public class SemanticScope {

    /**
     * A logical constraint. If not negated, the arguments are required, otherwise they are
     * prohibited. The argument list may contain IKimConcept.Type, IConcept, or other constraints.
     * Multiple constraints in the logicalScope field are in OR; individual arguments in the
     * constraint are in AND.
     * 
     * @author Ferd
     *
     */
    public static class Constraint {
        
        public boolean negated = false;
        public Collection<Object> arguments = new HashSet<>();
        public IUnit unit;
        
        private Constraint() {
        }

        public static Constraint not(Object arg) {
            Constraint ret = new Constraint();
            ret.arguments.add(arg);
            ret.negated = true;
            return ret;
        }

        public static Constraint of(Object... objects) {
            Constraint ret = new Constraint();
            for (Object o : objects) {
                ret.arguments.add(o);
            }
            return ret;
        }

        public boolean matches(IConcept concept) {
            for (Object o : arguments) {
                if (o instanceof IKimConcept.Type && (!concept.is((Type) o) || (negated && concept.is((Type) o)))) {
                    return false;
                } else if (o instanceof Constraint && (!((Constraint) o).matches(concept))
                        || (negated && ((Constraint) o).matches(concept))) {
                    return false;
                } // TODO continue
            }
            return negated ? false : true;
        }

        public String toString() {
            return (negated ? "<NOT " : "<") + arguments + ">";
        }

        public Constraint compatibleWith(IConcept concept) {
            // TODO Auto-generated method stub
            return this;
        }

        public Constraint withDifferentBaseTraitOf(IConcept concept) {
            // TODO Auto-generated method stub
            return this;
        }
        
        public static Constraint compatibleUnit(IUnit unit) {
            Constraint ret = new Constraint();
            ret.unit = unit;
            return ret;
        }

    }

    /**
     * Admitted concept types that can be added in this scope. Empty means everything's allowed,
     * filled means any of those but nothing else.
     */
    Set<Constraint> logicalRealm = new HashSet<>();

    /**
     * Admitted lexical realm for this scope. Calls that fulfill a role which isn't here are
     * illegal. Empty means everything's allowed, filled means any of those but nothing else.
     */
    Set<ObservableRole> lexicalRealm = EnumSet.noneOf(ObservableRole.class);

    String error = null;

    public String toString() {
        if (logicalRealm.isEmpty() && lexicalRealm.isEmpty()) {
            return "[]";
        }

        return lexicalRealm + ", " + logicalRealm;
    }

    public static SemanticScope root() {

        SemanticScope ret = new SemanticScope();
        ret.lexicalRealm.add(ObservableRole.UNARY_OPERATOR);
        ret.lexicalRealm.add(ObservableRole.GROUP_OPEN);
        ret.logicalRealm.add(Constraint.of(IKimConcept.Type.OBSERVABLE));
        ret.logicalRealm.add(Constraint.of(IKimConcept.Type.PREDICATE));
        return ret;
    }

    public static SemanticScope scope(IConcept concept, SemanticExpression context) {

        SemanticScope ret = new SemanticScope();

        if (concept.is(Type.OBSERVABLE)) {

            ret.lexicalRealm.addAll(compatibleModifiers(concept));
            
            if (concept.is(Type.QUALITY)) {
                
                /*
                 * add value operators and constrain for those that are already present in the
                 * current lexical scope
                 * 
                 * TODO check if we need to differentiate operators
                 */
                ret.lexicalRealm.add(ObservableRole.VALUE_OPERATOR);

                /*
                 * check for units and constrain for compatibility
                 */
                if (concept.is(Type.MONETARY_VALUE)) {
                    ret.lexicalRealm.add(ObservableRole.CURRENCY);
                } else if (concept.is(Type.EXTENSIVE_PROPERTY) || concept.is(Type.INTENSIVE_PROPERTY)) {
                    IUnit baseUnit = Units.INSTANCE.getDefaultUnitFor(concept);
                    if (baseUnit != null) {
                        ret.lexicalRealm.add(ObservableRole.UNIT);
                        ret.logicalRealm.add(Constraint.compatibleUnit(baseUnit));
                    }
                } else if (concept.is(Type.NUMEROSITY)) {
                    ret.lexicalRealm.add(ObservableRole.UNIT);
                    ret.logicalRealm.add(Constraint.compatibleUnit(Units.INSTANCE.getUnit("1")));
                }
            }
            
        } else {
            ret.lexicalRealm.add(ObservableRole.UNARY_OPERATOR);
            ret.logicalRealm.add(Constraint.of(Type.PREDICATE).withDifferentBaseTraitOf(concept));
            ret.logicalRealm.add(Constraint.of(Type.OBSERVABLE).compatibleWith(concept));
        }

        return ret;
    }

    private static Collection<? extends ObservableRole> compatibleModifiers(IConcept concept) {
        List<ObservableRole> ret = new ArrayList<>();
        for (SemanticModifier modifier : SemanticModifier.values()) {
            if (modifier.role != null) {
                for (Type type : modifier.applicable) {
                    if (concept.is(type)) {
                        ret.add(modifier.role);
                        break;
                    }
                }
            }
        }
        return ret;
        
    }

    public static SemanticScope scope(String syntacticElement, SemanticExpression context) {

        SemanticScope ret = new SemanticScope();

        switch(syntacticElement) {
        case "(":
            // no change to the scope of the inner observable
            ret = context.getCurrent().getScope();
            break;
        case "in":
            // collect observable, set to currency or unit
            break;
        case "per":
            // collect observable, set to numerosity
            break;
        default:
            // unit or currency: check context, validate, then return empty scope
        }

        return ret;
    }

    public static SemanticScope scope(BinarySemanticOperator op, SemanticExpression context) {
        SemanticScope ret = new SemanticScope();

        switch(op) {
        case FOLLOWS:
            ret.logicalRealm.add(Constraint.of(Type.EVENT));
            ret.lexicalRealm.add(ObservableRole.GROUP_OPEN);
            break;
        case INTERSECTION:
            // must intersect same type
            ret.logicalRealm.addAll(context.getCurrent().getScope().getAdmittedLogicalInput());
            ret.lexicalRealm.add(ObservableRole.GROUP_OPEN);
            break;
        case UNION:
            // must intersect same type
            ret.logicalRealm.addAll(context.getCurrent().getScope().getAdmittedLogicalInput());
            ret.lexicalRealm.add(ObservableRole.GROUP_OPEN);
            break;
        }

        return ret;
    }

    public static SemanticScope scope(ValueOperator op, SemanticExpression context) {

        SemanticScope ret = new SemanticScope();

        switch(op) {
        case AVERAGED:
        case TOTAL:
        case SUMMED:
            // no further input 
            break;
        case BY:
            ret.logicalRealm.add(Constraint.of(Type.ABSTRACT, Type.CLASS));
            ret.logicalRealm.add(Constraint.of(Type.COUNTABLE));
            break;
        case DOWN_TO:
            ret.logicalRealm.add(Constraint.of(Constraint.not(Type.ABSTRACT), Type.CLASS));
            ret.lexicalRealm.add(ObservableRole.INLINE_VALUE);
            break;
        case GREATEREQUAL:
        case GREATER:
        case LESS:
        case LESSEQUAL:
        case MINUS:
        case OVER:
        case PLUS:
        case SAMEAS:
        case IS:
        case TIMES:
        case WITHOUT:
            ret.lexicalRealm.add(ObservableRole.INLINE_VALUE);
            break;
        case WHERE:
            ret.lexicalRealm.add(ObservableRole.GROUP_OPEN);
            break;
        }

        return ret;
    }

    public static SemanticScope scope(SemanticModifier role, SemanticExpression context) {

        SemanticScope ret = new SemanticScope();
        // always possible to scope for a complex observable
        ret.lexicalRealm.add(ObservableRole.GROUP_OPEN);
        for (Type type : role.argument) {
            ret.logicalRealm.add(Constraint.of(type));
        }
        return ret;
    }

    public static SemanticScope scope(UnarySemanticOperator role, SemanticExpression context) {

        SemanticScope ret = new SemanticScope();

        // always possible to scope for a complex observable
        ret.lexicalRealm.add(ObservableRole.GROUP_OPEN);

        switch(role) {
        case ASSESSMENT:
            break;
        case CHANGE:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case CHANGED:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case COUNT:
            ret.logicalRealm.add(Constraint.of(Type.COUNTABLE));
            break;
        case DISTANCE:
            ret.logicalRealm.add(Constraint.of(Type.COUNTABLE));
            break;
        case LEVEL:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case MAGNITUDE:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case MONETARY_VALUE:
            ret.logicalRealm.add(Constraint.of(Type.OBSERVABLE));
            break;
        case NOT:
            ret.logicalRealm.add(Constraint.of(Type.DENIABLE));
            break;
//        case OBSERVABILITY:
//            ret.logicalRealm.add(Constraint.of(Type.OBSERVABLE));
//            break;
        case OCCURRENCE:
            ret.logicalRealm.add(Constraint.of(Type.COUNTABLE));
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case PERCENTAGE:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case PRESENCE:
            ret.logicalRealm.add(Constraint.of(Type.COUNTABLE));
            break;
        case PROBABILITY:
            ret.logicalRealm.add(Constraint.of(Type.EVENT));
            break;
        case PROPORTION:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case RATE:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case RATIO:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case TYPE:
            ret.logicalRealm.add(Constraint.of(Type.CLASS));
            break;
        case UNCERTAINTY:
            ret.logicalRealm.add(Constraint.of(Type.QUALITY));
            break;
        case VALUE:
            ret.logicalRealm.add(Constraint.of(Type.OBSERVABLE));
            break;
        default:
            break;

        }

        return ret;
    }

    /**
     * Check for admissibility of the passed token; if not admitted, set the error and return false.
     * 
     * @param token
     * @return
     */
    public boolean isAdmitted(Object token, SemanticExpression context) {

        boolean ret = false;

        if (token instanceof IConcept) {

            for (Constraint c : this.logicalRealm) {
                if (c.matches((IConcept) token)) {
                    ret = true;
                }
            }

        } else if (token instanceof ValueOperator) {
            ret = this.lexicalRealm.contains(ObservableRole.VALUE_OPERATOR)
                    && !context.collect(ObservableRole.VALUE_OPERATOR).contains(token);
        } else if (token instanceof SemanticModifier) {
            ret = this.lexicalRealm.contains(((SemanticModifier)token).role)
                    && !context.collect(ObservableRole.SEMANTIC_MODIFIER).contains(token);
        } else if (token instanceof UnarySemanticOperator) {
            ret = this.lexicalRealm.contains(ObservableRole.UNARY_OPERATOR)
                    && context.collect(ObservableRole.UNARY_OPERATOR).isEmpty();
        } else if (token instanceof BinarySemanticOperator) {
            ret = this.lexicalRealm.contains(ObservableRole.BINARY_OPERATOR);
        } else if (token instanceof String) {
            switch((String) token) {
            case "(":
                ret = this.lexicalRealm.contains(ObservableRole.GROUP_OPEN);
                break;
            case ")":
                ret = context.getCurrent().getGroupParent() != null;
                break;
            case "in":
                Set<Object> obs = context.collect(ObservableRole.OBSERVABLE);
                if (!obs.isEmpty()) {
                    if (((IConcept) obs.iterator().next()).is(Type.MONETARY_VALUE)) {
                        ret = this.lexicalRealm.contains(ObservableRole.CURRENCY)
                                && context.collect(ObservableRole.CURRENCY).isEmpty();
                    } else {
                        ret = this.lexicalRealm.contains(ObservableRole.UNIT) && context.collect(ObservableRole.UNIT).isEmpty();
                    }
                }
                break;
            case "per":
                ret = this.lexicalRealm.contains(ObservableRole.DISTRIBUTED_UNIT);
                break;
            default:

                /*
                 * TODO must be under unit, currency or operator value; validate as required based
                 * on context
                 */
                if (context.getCurrent().isAs(ObservableRole.UNIT)) {
                    // validate against property (not numerosity). TODO may want a constraint for
                    // base unit
                } else if (context.getCurrent().isAs(ObservableRole.CURRENCY)) {
                    // validate against monetary value
                } else if (context.getCurrent().isAs(ObservableRole.DISTRIBUTED_UNIT)) {
                    // validate against numerosity, must be unitless. TODO may want a constraint for
                    // base unit
                } else if (context.getCurrent().isAs(ObservableRole.INLINE_VALUE)) {
                    // validate against operator and observable. TODO may want a type constraint
                }
                break;
            }
        }

        if (!ret && error == null) {
            // catch-all
            this.error = "token " + token + " is illegal in this position of a semantic expression";
        }

        return ret;
    }

    public String getErrorAndReset() {
        String ret = error;
        this.error = null;
        return ret;
    }

    public Collection<ObservableRole> getAdmittedLexicalInput() {
        return lexicalRealm;
    }

    public Collection<Constraint> getAdmittedLogicalInput() {
        return logicalRealm;
    }

}