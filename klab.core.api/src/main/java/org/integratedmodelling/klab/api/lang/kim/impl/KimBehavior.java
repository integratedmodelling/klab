package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.lang.KServiceCall;
import org.integratedmodelling.klab.api.lang.kim.KKimAction;
import org.integratedmodelling.klab.api.lang.kim.KKimBehavior;

/**
 * A IKimBehavior is the statement of the contextualization strategy for a model or an observation,
 * consisting of a list of action and a set of general methods for convenience.
 * 
 * @author fvilla
 *
 */
public class KimBehavior extends KimStatement implements KKimBehavior {

    private static final long serialVersionUID = 2701074196387350255L;
    private List<KKimAction> actions = new ArrayList<>();
    private boolean empty;
    private boolean dynamic;
    private List<KServiceCall> extentFunctions = new ArrayList<>();

    @Override
    public List<KKimAction> getActions() {
        return this.actions;
    }

    @Override
    public boolean isEmpty() {
        return this.empty;
    }

    @Override
    public boolean isDynamic() {
        return this.dynamic;
    }

    @Override
    public Collection<KServiceCall> getExtentFunctions() {
        return this.extentFunctions;
    }

    public void setActions(List<KKimAction> actions) {
        this.actions = actions;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public void setExtentFunctions(List<KServiceCall> extentFunctions) {
        this.extentFunctions = extentFunctions;
    }

}
