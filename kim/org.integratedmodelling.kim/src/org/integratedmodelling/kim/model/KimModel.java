package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.kim.ModelStatement;

/**
 * Built by the generator and passed to the engine.
 * 
 * @author Ferd
 *
 */
public class KimModel extends KimStatement implements IKimModel {

	private static final long serialVersionUID = -2936567328185233385L;

	public String name = "inconsistent-statement";

	public KimModel(ModelStatement statement, IKimStatement parent) {
		super(statement, parent);
	}

	private boolean instantiator = false;
	private boolean inactive = false;
	private boolean isAbstract = false;
	private boolean interpreter = false;
	private boolean learningModel = false;
	private Type type = Type.SEMANTIC;
	private Scope scope = Scope.PUBLIC;
	private List<IKimObservable> observables = new ArrayList<>();
	private List<IKimObservable> dependencies = new ArrayList<>();
	private IKimConcept reinterpretingRole = null;
	private IKimBehavior behavior = new KimBehavior();
	private String docstring;

	// next four represent the datasource/inline value/URN given before 'as
	// <observable>'. They are
	// translated into the one IResource in IModel.
	private List<String> resourceUrns = new ArrayList<>();
	private IServiceCall resourceFunction;
	private Object inlineValue;
//	private boolean resourceMerger;
	
	// contextualizer/processing given after 'using'
	private List<IContextualizable> contextualization = new ArrayList<>();

	// private Map<String, Object> parameters = new HashMap<>();

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isInstantiator() {
		return instantiator;
	}

	public void setInstantiator(boolean instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	@Override
	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	@Override
	public boolean isInterpreter() {
		return interpreter;
	}

	public void setInterpreter(boolean interpreter) {
		this.interpreter = interpreter;
	}

	@Override
	public boolean isLearningModel() {
		return learningModel;
	}

	public void setLearningModel(boolean learningModel) {
		this.learningModel = learningModel;
	}

	@Override
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public Scope getScope() {
		return this.scope;
	}

	@Override
	public List<IKimObservable> getObservables() {
		return observables;
	}

	public void setObservables(List<IKimObservable> observables) {
		this.observables = observables;
	}

	@Override
	public List<IKimObservable> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<IKimObservable> dependencies) {
		this.dependencies = dependencies;
	}

	public void setReinterpretingRole(IKimConcept reinterpretingRole) {
		this.reinterpretingRole = reinterpretingRole;
	}

	@Override
	protected String getStringRepresentation(int offset) {
		String ret = offset(offset) + "[model " + name + "]";
		for (IKimScope child : children) {
			ret += "\n" + ((KimScope) child).getStringRepresentation(offset + 3);
		}
		return ret;
	}

	@Override
	public Optional<IKimConcept> getReinterpretingRole() {
		return this.reinterpretingRole == null ? Optional.empty() : Optional.of(this.reinterpretingRole);
	}

	@Override
	public List<String> getResourceUrns() {
		return this.resourceUrns;
	}

	public void setResourceUrn(List<String> resourceUrn) {
		this.resourceUrns = resourceUrn;
	}

	@Override
	public Optional<IServiceCall> getResourceFunction() {
		return resourceFunction == null ? Optional.empty() : Optional.of(resourceFunction);
	}

	public void setResourceFunction(IServiceCall resourceFunction) {
		this.resourceFunction = resourceFunction;
	}

	@Override
	public Optional<Object> getInlineValue() {
		return inlineValue == null ? Optional.empty() : Optional.of(inlineValue);
	}

	public void setInlineValue(Object inlineValue) {
		this.inlineValue = inlineValue;
	}

	@Override
	public IKimBehavior getBehavior() {
		return this.behavior;
	}

	public void setBehavior(IKimBehavior behavior) {
		this.behavior = behavior;
	}

	@Override
	public List<IContextualizable> getContextualization() {
		return contextualization;
	}

	public String getDocstring() {
		return docstring;
	}

	public void setDocstring(String docstring) {
		this.docstring = docstring;
	}

	@Override
	public void visit(Visitor visitor) {
		visitor.visitModel(this);
		for (IKimObservable observable : getObservables()) {
			observable.visit(visitor);
		}
		for (IKimObservable dependency : dependencies) {
			dependency.visit(visitor);
		}
		if (reinterpretingRole != null) {
			reinterpretingRole.visit(visitor);
		}
		for (IContextualizable resource : getContextualization()) {
			resource.visit(visitor);
		}
	}

	@Override
	public boolean isSemantic() {
		return observables.size() > 0 && observables.get(0).getNonSemanticType() == null;
	}

	public void setScope(IKimStatement.Scope scope) {
		this.scope = scope;
	}

//	@Override
//	public boolean isResourceMerger() {
//		return resourceMerger;
//	}
//
//	public void setResourceMerger(boolean resourceMerger) {
//		this.resourceMerger = resourceMerger;
//	}

}
