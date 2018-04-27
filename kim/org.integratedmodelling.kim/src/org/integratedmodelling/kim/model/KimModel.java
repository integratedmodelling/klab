package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimScope;
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

  public String name = "UNASSIGNED";

  public KimModel(ModelStatement statement) {
    super(statement);
  }

  private boolean instantiator = false;
  private boolean inactive = false;
  private boolean isAbstract = false;
  private boolean interpreter = false;
  private boolean learningModel = false;
  private boolean assessmentModel = false;
  private Type type = Type.SEMANTIC;
  private boolean isPrivate = false;
  private List<IKimObservable> observables = new ArrayList<>();
  private List<IKimObservable> dependencies = new ArrayList<>();
  private IKimConcept reinterpretingRole = null;
  private IKimBehavior behavior = new KimBehavior();

  // next four represent the datasource/inline value/URN given before 'as <observable>'. They are
  // translated into the one IResource in IModel.
  private String resourceUrn;
  private IServiceCall resourceFunction;
  private Object inlineValue;

  // contextualizer/processing given after 'using'
  private List<IComputableResource> contextualization = new ArrayList<>();

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
  public boolean isAssessmentModel() {
    return assessmentModel;
  }

  public void setAssessmentModel(boolean assessmentModel) {
    this.assessmentModel = assessmentModel;
  }

  @Override
  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean isPrivate) {
    this.isPrivate = isPrivate;
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
    return this.reinterpretingRole == null ? Optional.empty()
        : Optional.of(this.reinterpretingRole);
  }

  @Override
  public Optional<String> getResourceUrn() {
    return this.resourceUrn == null ? Optional.empty() : Optional.of(this.resourceUrn);
  }

  public void setResourceUrn(String resourceUrn) {
    this.resourceUrn = resourceUrn;
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
  public List<IComputableResource> getContextualization() {
    return contextualization;
  }


}
