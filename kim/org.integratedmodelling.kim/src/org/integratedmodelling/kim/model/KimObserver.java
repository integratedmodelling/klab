package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimScope.Visitor;

public class KimObserver extends KimStatement implements IKimObserver {

    private static final long serialVersionUID = -4175718293425086114L;

    private IKimObservable observable;
    // Name will be the same as the observable's
    private String name;
    // States must have a value set
    private List<IKimObservable> states = new ArrayList<>();
    private IKimBehavior behavior = new KimBehavior();
    private String docstring;

    public KimObserver(EObject statement, IKimObservable observable, IKimStatement parent) {
        super(statement, parent);
        this.observable = observable;
        this.name = observable.getFormalName();
    }

    @Override
    protected String getStringRepresentation(int offset) {
        String ret = offset(offset) + "[observation " + name + "]";
        for (IKimScope child : children) {
            ret += "\n" + ((KimScope) child).getStringRepresentation(offset + 3);
        }
        return ret;
    }

    @Override
    public IKimObservable getObservable() {
        return observable;
    }

    @Override
    public List<IKimObservable> getStates() {
        return states;
    }

    @Override
    public IKimBehavior getBehavior() {
        return behavior;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setObservable(IKimObservable observable) {
        this.observable = observable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStates(List<IKimObservable> states) {
        this.states = states;
    }

    public void setBehavior(IKimBehavior behavior) {
        this.behavior = behavior;
    }

    public String getDocstring() {
        return docstring;
    }

    public void setDocstring(String docstring) {
        this.docstring = docstring;
    }
    
	@Override
	public void visit(Visitor visitor) {
		visitor.visitObserver(this);
		observable.visit(visitor);
		for (IKimScope child : getChildren()) {
			child.visit(visitor);
		}
	}
}
