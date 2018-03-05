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
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;
import org.integratedmodelling.klab.api.resolution.IComputable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.documentation.Documentation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.CompatibleObservable;

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

  // only for the delegate RankedModel
  protected Model() {
    super(null);
  }

  public Model(IKimModel model, INamespace namespace, IMonitor monitor) {

    super(model);

    this.id = model.getName();
    this.namespace = namespace;
    this.isPrivate = model.isPrivate();

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
//      try {
//        this.resource = Optional.of(Resources.INSTANCE.getResource(model.getResourceUrn().get()));
//      } catch (KlabUnknownUrnException | KlabUnauthorizedUrnException e) {
//        monitor.error(e, model);
//      }
    } else if (model.getResourceFunction().isPresent()) {
//      this.resource =
//          Optional.of(Resources.INSTANCE.getComputedResource(model.getResourceFunction().get()));
    } else if (model.getInlineValue().isPresent()) {
//      this.resource =
//          Optional.of(Resources.INSTANCE.getLiteralResource(model.getInlineValue().get()));
    }

    this.resources.addAll(model.getContextualization());
//    IResource ctxResource = createContextualizerResource(model.getContextualization());
//    if (ctxResource != null) {
//
//      /*
//       * if we have a 'using' but no resource before the observable, this becomes the resource
//       * itself unless it's a post-processor, in which case it will be installed as a contextualizer
//       * resource.
//       */
//      if (this.resource == null && !model.getContextualization().isPostProcessor()) {
//        this.resource = Optional.of(ctxResource);
//      } else {
//        this.contextualizerResource = Optional.of(ctxResource);
//      }
//
//    }

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

//  private IResource createContextualizerResource(IKimContextualization contextualization) {
//    if (contextualization.getFunction() != null) {
//      return Resources.INSTANCE.getComputedResource(contextualization.getFunction());
//    } else if (contextualization.getRemoteUrn() != null) {
//      return Resources.INSTANCE.getUrnResource(contextualization.getRemoteUrn());
//    }
//    // TODO the rest - classifications (normal or according to), lookup table etc
//    // TODO figure out and validate the postprocessor thing
//    // TODO these may become multiple
//    return null;
//  }

  // @Override
  // public Optional<IResource> getContextualizerResource() {
  // return contextualizerResource;
  // }

  @Override
  public List<IObservable> getObservables() {
    return observables;
  }

  // @Override
  // public Optional<IResource> getResource() {
  // return resource;
  // }

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
    // TODO all resources have no parameters or all parameters are resolved throughy resources with no parameters. 
    for (IComputableResource resource : resources) {
      //
    }
    //    if (resource != null) {
//      return true;
//    }
//    if (contextualizerResource != null) {
//      return !contextualizerResource.get().getGeometry().isEmpty();
//    }
    return false;
  }

  @Override
  public boolean isInstantiator() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isReinterpreter() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isAvailable() {
    // TODO Auto-generated method stub
    return false;
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

//  public void setResource(Optional<IResource> resource) {
//    this.resource = resource;
//  }

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
  public List<IComputableResource> getComputation(ITransition transition) {
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
   * @return
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
   * @return
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
