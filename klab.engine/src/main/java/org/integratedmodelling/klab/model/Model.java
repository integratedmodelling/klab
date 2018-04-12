package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IComputable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.documentation.Documentation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.CompatibleObservable;
import org.integratedmodelling.klab.scale.Scale;

public class Model extends KimObject implements IModel {

  private static final long serialVersionUID = 6405594042208542702L;

  private String id;
  private Optional<IDocumentation> documentation = Optional.empty();
  private List<IObservable> observables = new ArrayList<>();
  private List<IObservable> dependencies = new ArrayList<>();
  private Map<String, IObservable> attributeObservables = new HashMap<>();
  private INamespace namespace;
  private Behavior behavior;
  private List<IComputableResource> resources = new ArrayList<>();
  private boolean isPrivate;
  private boolean instantiator;
  private boolean reinterpreter;
  private boolean inactive;

  // only for the delegate RankedModel
  protected Model() {
    super(null);
  }

  /**
   * 
   * @param model
   * @param namespace
   * @param monitor
   * @return a new model
   */
  public static Model create(IKimModel model, INamespace namespace, IMonitor monitor) {
    return new Model(model, namespace, monitor);
  }

  private Model(IKimModel model, INamespace namespace, IMonitor monitor) {

    super(model);

    this.id = model.getName();
    this.namespace = namespace;
    this.isPrivate = model.isPrivate();
    this.instantiator = model.isInstantiator();
    this.inactive = model.isInactive();
    
    setDeprecated(model.isDeprecated() || namespace.isDeprecated());

    for (IKimObservable observable : model.getObservables()) {
      if (observable.hasAttributeIdentifier()) {
        attributeObservables.put(observable.getValue().toString(),
            Observables.INSTANCE.declare(observable, monitor));
      } else {
        observables.add(Observables.INSTANCE.declare(observable, monitor));
      }
    }

    for (IKimObservable dependency : model.getDependencies()) {
      dependencies.add(Observables.INSTANCE.declare(dependency, monitor));
    }

    if (model.getResourceUrn().isPresent()) {
      this.resources.add(new ComputableResource(model.getResourceUrn().get()));
    } else if (model.getResourceFunction().isPresent()) {
      this.resources.add(new ComputableResource(model.getResourceFunction().get()));
    } else if (model.getInlineValue().isPresent()) {
      this.resources.add(new ComputableResource(model.getInlineValue()));
    }

    this.resources.addAll(model.getContextualization());

    // actions
    this.behavior = new Behavior(model.getBehavior(), this);

    if (model.getMetadata() != null) {
      setMetadata(new Metadata(model.getMetadata()));
    }

    /*
     * documentation
     */
    if (model.getDocumentationMetadata() != null) {
      this.documentation = Optional.of(new Documentation(model.getDocumentationMetadata()));
    }
  }

  public String toString() {
    return "[" + getName() + "]";
  }

  @Override
  public List<IObservable> getObservables() {
    return observables;
  }

  @Override
  public Map<String, IObservable> getAttributeObservables() {
    return attributeObservables;
  }

  @Override
  public String getLocalNameFor(IObservable observable) {
    IObservable obs = getCompatibleOutput((Observable) observable);
    if (obs != null) {
      return obs.getLocalName();
    }
    obs = getCompatibleInput((Observable) observable);
    return obs == null ? null : obs.getLocalName();
  }

  @Override
  public boolean isResolved() {
    // TODO all resources have no parameters or all parameters are resolved through resources with
    // no parameters.
    // TODO also check 'change to' status on main observable. And maybe geometry (vs. context?
    // Should we check in context?)
    if (dependencies.size() > 0) {
      return false;
    }
    
    for (IComputableResource resource : resources) {
      // TODO TODO this is a temp fix to make the tests run.
      if (!resource.getRequiredResourceNames().isEmpty()) {
        return false;
      }
    }
    // if (resource != null) {
    // return true;
    // }
    // if (contextualizerResource != null) {
    // return !contextualizerResource.get().getGeometry().isEmpty();
    // }
    // TODO temp rationale: empty resource set = pass-through model
    return !resources.isEmpty();
  }

  @Override
  public boolean isInstantiator() {
    return instantiator;
  }

  @Override
  public boolean isReinterpreter() {
    // TODO Auto-generated method stub
    return reinterpreter;
  }

  @Override
  public boolean isAvailable() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public Optional<IDocumentation> getDocumentation() {
    return documentation;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.namespace.getId() + "." + id;
  }

  @Override
  public INamespace getNamespace() {
    return namespace;
  }

  @Override
  public List<IObservable> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<IObservable> dependencies) {
    this.dependencies = dependencies;
  }

  // public void setResource(Optional<IResource> resource) {
  // this.resource = resource;
  // }

  public void setDocumentation(Optional<IDocumentation> documentation) {
    this.documentation = documentation;
  }

  public void setObservables(List<IObservable> observables) {
    this.observables = observables;
  }

  public void setAttributeObservables(Map<String, IObservable> attributeObservables) {
    this.attributeObservables = attributeObservables;
  }

  public void setNamespace(INamespace namespace) {
    this.namespace = namespace;
  }

  @Override
  public Behavior getBehavior() {
    return behavior;
  }

  @Override
  public boolean isPrivate() {
    return isPrivate || namespace.isPrivate();
  }

  /**
   * Build and return the scale, if any, specified for the model (possibly along with any
   * constraints in the namespace it contains).
   * 
   * @param monitor
   * @return a new scale, possibly empty, never null.
   * @throws KlabException
   */
  public Scale getCoverage(IMonitor monitor) throws KlabException {
    return Scale.create(behavior.getExtents(monitor));
  }

  /**
   * Return all the computational steps required to compute the model, encoded as function calls.
   * 
   * @param transition the transition to be computed
   * @return the computations for the model at the transition
   */
  @Override
  public List<IComputableResource> getComputation(ILocator transition) {
    List<IComputableResource> ret = new ArrayList<>(resources);
    for (Trigger trigger : Dataflows.INSTANCE.getActionTriggersFor(transition)) {
      for (IAction action : behavior.getActions(trigger)) {
          ret.addAll(action.getComputation(transition));
      }
    }
    return ret;
  }

  /**
   * Get the output that can satisfy this observable, possibly with mediation.
   * 
   * @param observable
   * @return an existing output observable or null
   */
  public Observable getCompatibleOutput(Observable observable) {
    for (IObservable output : observables) {
      if (new CompatibleObservable((Observable) output)
          .equals(new CompatibleObservable(observable))) {
        return (Observable) output;
      }
    }
    return null;
  }

  /**
   * Get the input that can satisfy this observable, possibly with mediation.
   * 
   * @param observable
   * @return an existing output observable or null
   */
  public Observable getCompatibleInput(Observable observable) {
    for (IObservable input : dependencies) {
      if (new CompatibleObservable((Observable) input)
          .equals(new CompatibleObservable(observable))) {
        return (Observable) input;
      }
    }
    return null;
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Model && getName().equals(((Model) obj).getName());
  }

  @Override
  public List<IComputable> getResources() {
    // TODO Auto-generated method stub
    return null;
  }

}
