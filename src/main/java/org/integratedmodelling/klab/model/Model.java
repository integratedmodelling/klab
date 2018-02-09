package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.integratedmodelling.kim.api.IKimContextualization;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IBehavior;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.documentation.Documentation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;
import org.integratedmodelling.klab.observation.Scale;

public class Model extends KimObject implements IModel {

  private static final long        serialVersionUID       = 6405594042208542702L;

  private String                   id;
  private Optional<IResource>      resource               = Optional.empty();
  private Optional<IResource>      contextualizerResource = Optional.empty();
  private Optional<IDocumentation> documentation          = Optional.empty();
  private List<IObservable>        observables            = new ArrayList<>();
  private List<IObservable>        dependencies           = new ArrayList<>();
  private Map<String, IObservable> attributeObservables   = new HashMap<>();
  private INamespace               namespace;
  private Behavior                 behavior;
  private boolean                  isPrivate;

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
      try {
        this.resource = Optional.of(Resources.INSTANCE.getResource(model.getResourceUrn().get()));
      } catch (KlabUnknownUrnException | KlabUnauthorizedUrnException e) {
        monitor.error(e, model);
      }
    } else if (model.getResourceFunction().isPresent()) {
      this.resource =
          Optional.of(Resources.INSTANCE.getComputedResource(model.getResourceFunction().get()));
    } else if (model.getInlineValue().isPresent()) {
      this.resource =
          Optional.of(Resources.INSTANCE.getLiteralResource(model.getInlineValue().get()));
    }

    IResource ctxResource = createContextualizerResource(model.getContextualization());
    if (ctxResource != null) {

      /*
       * if we have a 'using' but no resource before the observable, this becomes the resource
       * itself unless it's a post-processor, in which case it will be installed as a contextualizer
       * resource.
       */
      if (this.resource == null && !model.getContextualization().isPostProcessor()) {
        this.resource = Optional.of(ctxResource);
      } else {
        this.contextualizerResource = Optional.of(ctxResource);
      }

    }

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

  private IResource createContextualizerResource(IKimContextualization contextualization) {
    return null;
  }

  @Override
  public Optional<IResource> getContextualizerResource() {
    return contextualizerResource;
  }

  @Override
  public List<IObservable> getObservables() {
    return observables;
  }

  @Override
  public Optional<IResource> getResource() {
    return resource;
  }

  @Override
  public Map<String, IObservable> getAttributeObservables() {
    return attributeObservables;
  }

  @Override
  public String getLocalNameFor(IObservable observable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isResolved() {
    // TODO Auto-generated method stub
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

  public void setResource(Optional<IResource> resource) {
    this.resource = resource;
  }

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

}
