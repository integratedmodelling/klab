package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimActiveStatement;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.kim.Action;
import org.integratedmodelling.kim.kim.ActionSpecification;
import org.integratedmodelling.kim.kim.FunctionOrID;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;

import com.google.common.collect.Sets;

public class KimBehavior extends KimStatement implements IKimBehavior {

    private static final long serialVersionUID = -6524623811866282121L;
    List<IKimAction> actions = new ArrayList<>();
    List<IServiceCall> extents = new ArrayList<>();

    // static Pair<List<IFunctionCall>, List<IAction>> defineContextActions(KIMScope context,
    // KIMModelObject observer, List<Contextualization> statements) {
    //
    // List<IFunctionCall> functions = new ArrayList<>();
    // List<IAction> actions = new ArrayList<>();
    //
    // for (Contextualization c : statements) {
    //
    // Set<IConcept> domains = new HashSet<>();
    //
    // IAction.Trigger trigger = null;
    // if (c.isInitialization()) {
    // trigger = Trigger.DEFINITION;
    // } else if (c.isResolution()) {
    // trigger = Trigger.RESOLUTION;
    // } else if (c.isTermination()) {
    // trigger = Trigger.TERMINATION;
    // } else if (c.isInstantiation()) {
    // trigger = Trigger.INSTANTIATION;
    // } else if (c.isStateInitialization()) {
    // trigger = Trigger.STATE_INITIALIZATION;
    // }
    // ;
    // IConcept onEvent = null;
    // if (c.getEvent() != null) {
    // KIMKnowledge k = new KIMKnowledge(context.get(KIMScope.Type.EVENT_CONTEXTUALIZATION), c
    // .getEvent());
    // if (k == null || !k.isConcept() || k.isNothing() || !NS.isEvent(k.getConcept())) {
    // context.error("only known event concepts can be used to trigger actions", lineNumber(c));
    // }
    // domains.add(k.getConcept());
    // trigger = Trigger.EVENT;
    // }
    //
    // boolean ok = true;
    //
    // if (c.getDomain() != null) {
    //
    // for (FunctionOrID fid : c.getDomain()) {
    //
    // IFunctionCall function = null;
    // if (fid.getFunctionId() != null) {
    // function = new KIMFunctionCall(context.get(KIMScope.Type.COVERAGE_FUNCTION_CALL), fid
    // .getFunctionId());
    // } else if (fid.getFunction() != null) {
    // function = new KIMFunctionCall(context.get(KIMScope.Type.COVERAGE_FUNCTION_CALL), fid
    // .getFunction());
    // }
    //
    // IConcept domain = validateFunctionCall(context, function, KLAB.c(NS.EXTENT));
    // if (domain != null) {
    // domains.add(domain);
    // functions.add(function);
    // if (domain.is(KLAB.c(NS.TIME_DOMAIN))) {
    // trigger = Trigger.TRANSITION;
    // }
    // } else {
    // ok = false;
    // }
    //
    // /**
    // * Function defines the scale even if no actions are specified.
    // */
    // // if (function != null && observer instanceof
    // // KIMObservingObject) {
    // // ((KIMObservingObject)
    // // observer).scaleGenerators.add(function);
    // // }
    // }
    // }
    //
    // if (trigger == null) {
    // if (c.getDomain() == null) {
    // context.warning("cannot determine trigger for actions", lineNumber(statements.iterator()
    // .next()));
    // }
    // } else {
    //
    // for (Action action : c.getActions()) {
    // if (ok) {
    // KIMAction a = new KIMAction(context
    // .get(KIMScope.Type.CONTEXTUALIZATION_ACTION), observer, domains, action, trigger, c);
    // actions.add(a);
    // } else {
    // context.warning("cannot determine domain: ignoring action", lineNumber(action));
    // }
    // }
    // }
    // }
    //
    // return new Pair<>(functions, actions);
    //
    // }

    @Override
    public Iterator<IKimAction> iterator() {
        return actions.iterator();
    }

    @Override
    public boolean isEmpty() {
        return actions.isEmpty();
    }

    @Override
    public boolean isDynamic() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Add an action from its specs. If any errors or warnings result, return them.
     * 
     * @param action
     * @param context
     * @return the list of notifications resulting from validation
     */
    public List<KimNotification> addAction(ActionSpecification action, IKimActiveStatement context) {

        List<KimNotification> ret = new ArrayList<>();
        List<KimAction> allActions = new ArrayList<>();
        KimAction act = new KimAction(action, this);
        boolean ok = true;
        boolean hasTemporalActions = false;

        // default trigger if none is defined but we don't have the 'over' part
        if (!action.isOver()) {
        	// will be overwritten later if necessary
        	act.setTrigger(Trigger.RESOLUTION);
        }
        
        if (action.isTrigger() || !action.isOver()) {

            if (action.isInitialization()) {
                act.setTrigger(Trigger.DEFINITION);
			} /*
				 * else if (action.isResolution()) { act.setTrigger(Trigger.RESOLUTION); }
				 */else if (action.isTermination()) {
                act.setTrigger(Trigger.TERMINATION);
            } else if (action.isInstantiation()) {
                act.setTrigger(Trigger.INSTANTIATION);
			} /*
				 * else if (action.isStateInitialization()) {
				 * act.setTrigger(Trigger.STATE_INITIALIZATION); }
				 */

            IKimConcept onEvent = null;
            if (action.getEvent() != null) {
                onEvent = Kim.INSTANCE.declareConcept(action.getEvent());
                if (onEvent == null || !onEvent.is(Type.EVENT)) {
                    ret.add(new KimNotification("only events can trigger actions", Level.SEVERE));
                    ok = false;
                }
                act.getTriggeringEvents().add(onEvent);
                act.setTrigger(Trigger.EVENT);
            }

        } else {

            for (FunctionOrID domain : action.getDomain()) {
                if (domain.getFunction() != null) {
                    extents.add(new KimServiceCall(domain.getFunction(), getParent()));
                } else {
                    extents.add(new KimServiceCall(domain, domain.getFunctionId(), new HashMap<>(), getParent()));
                }
                for (IServiceCall extent : extents) {
                    for (KimNotification note : Kim.INSTANCE.validateFunctionCall(extent,
                            Sets.immutableEnumSet(IArtifact.Type.SPATIALEXTENT, IArtifact.Type.TEMPORALEXTENT))) {
                        if (note.getLevel().equals(Level.SEVERE.getName())) {
                            ok = false;
                        }
                        ret.add(note);
                        IPrototype prot = Kim.INSTANCE.getValidator().getFunctionPrototype(extent.getName());
                        if (prot != null && prot.getType() == IArtifact.Type.TEMPORALEXTENT) {
                            hasTemporalActions = true;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < action.getActions().size(); i++) {

            Action ac = action.getActions().get(i);

            KimAction targetAction = i == 0 ? act : new KimAction(action, this);

            // Actions are never instantiations, so RESOLUTION here should be safe
            targetAction.set(ac, IResolutionScope.Mode.RESOLUTION);

            // check for temporal and single 'over time', if so set trigger to transition
            if (hasTemporalActions) {
                targetAction.setTrigger(Trigger.TRANSITION);
                if (extents.size() > 1) {
                    ret.add(new KimNotification("temporal actions cannot reference more than one extent",
                            Level.SEVERE));
                }
            }

            for (KimNotification not : targetAction.validate(context)) {
                if (not.getLevel().equals(Level.SEVERE.getName())) {
                    ok = false;
                }
                ret.add(not);
            }

            allActions.add(targetAction);
        }

        if (ok) {

            if (allActions.isEmpty()) {
                allActions.add(act);
            }

            for (KimAction a : allActions) {
                this.actions.add(a);
            }
        }

        return ret;
    }

    @Override
    public List<IKimAction> getActions() {
        return this.actions;
    }
    
    @Override
    public Collection<IServiceCall> getExtentFunctions() {
        return extents;
    }

}
