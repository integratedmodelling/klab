package org.integratedmodelling.klab.resolution;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.IComputable;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Behavior;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.StringUtils;

public class RankedModel extends Model implements IRankedModel {

  private static final long serialVersionUID = -442006167719783123L;

  String modelUrn;
  Model delegate;
  Map<String, Object> ranks;

  private org.integratedmodelling.klab.data.rest.resources.Model modelData;

  public RankedModel(String urn) {
    this.modelUrn = urn;
  }

  public RankedModel(org.integratedmodelling.klab.data.rest.resources.Model model,
      Map<String, Object> ranks) {
    this.modelUrn = model.getUrn();
    this.modelData = model;
    this.ranks = ranks;
  }

  private Model getDelegate() {

    if (delegate == null) {

      /**
       * TODO handle dereification through modelData
       */

      IKimObject m = Resources.INSTANCE.getModelObject(modelUrn);
      if (m instanceof Model) {
        delegate = (Model) m;
      }
    }
    return delegate;
  }

  public List<IKimObject> getChildren() {
    return getDelegate().getChildren();
  }

  public IKimStatement getStatement() {
    return getDelegate().getStatement();
  }

  public List<IKimAnnotation> getAnnotations() {
    return getDelegate().getAnnotations();
  }

  public void setMetadata(IMetadata metadata) {
    getDelegate().setMetadata(metadata);
  }

  public IMetadata getMetadata() {
    return getDelegate().getMetadata();
  }

  public boolean isDeprecated() {
    return getDelegate().isDeprecated();
  }

  public int hashCode() {
    return getDelegate().hashCode();
  }

  public void setDeprecated(boolean deprecated) {
    getDelegate().setDeprecated(deprecated);
  }

  public boolean equals(Object obj) {
    return getDelegate().equals(obj);
  }

  public List<IObservable> getObservables() {
    return getDelegate().getObservables();
  }

  public Map<String, IObservable> getAttributeObservables() {
    return getDelegate().getAttributeObservables();
  }

  public String getLocalNameFor(IObservable observable) {
    return getDelegate().getLocalNameFor(observable);
  }

  public boolean isResolved() {
    return getDelegate().isResolved();
  }

  public boolean isInstantiator() {
    return getDelegate().isInstantiator();
  }

  public boolean isReinterpreter() {
    return getDelegate().isReinterpreter();
  }

  public boolean isAvailable() {
    return getDelegate().isAvailable();
  }

  public Optional<IDocumentation> getDocumentation() {
    return getDelegate().getDocumentation();
  }

  public String getId() {
    return getDelegate().getId();
  }

  public String getName() {
    return getDelegate().getName();
  }

  public INamespace getNamespace() {
    return getDelegate().getNamespace();
  }

  public List<IObservable> getDependencies() {
    return getDelegate().getDependencies();
  }

  public void setDependencies(List<IObservable> dependencies) {
    getDelegate().setDependencies(dependencies);
  }

  public void setDocumentation(Optional<IDocumentation> documentation) {
    getDelegate().setDocumentation(documentation);
  }

  public void setObservables(List<IObservable> observables) {
    getDelegate().setObservables(observables);
  }

  public void setAttributeObservables(Map<String, IObservable> attributeObservables) {
    getDelegate().setAttributeObservables(attributeObservables);
  }

  public void setNamespace(INamespace namespace) {
    getDelegate().setNamespace(namespace);
  }

  public Behavior getBehavior() {
    return getDelegate().getBehavior();
  }

  public boolean isPrivate() {
    return getDelegate().isPrivate();
  }

  public Scale getCoverage(IMonitor monitor) throws KlabException {
    return getDelegate().getCoverage(monitor);
  }

  public String toString() {
    return getDelegate().toString()/* + "\n\n" + describeRanks() */;
  }

  public String describeRanks() {
    return describeRanks(0, 3);
  }

  private String describeRanks(int indent, int offset) {

    String ret = "";
    String filler = StringUtils.spaces(indent);

    ret += filler + StringUtils.rightPad(offset + ".", 4) + modelData.getName() + " ["
        + (modelData.getServerId() == null ? "local" : modelData.getServerId()) + "]\n";
    for (String s : ranks.keySet()) {
      ret += filler + "  " + StringUtils.rightPad(s, 25) + " " + ranks.get(s) + "\n";
    }

    return ret;
  }

  @Override
  public ICoverage getContextCoverage() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Object> getRanks() {
    return ranks;
  }

  @Override
  public Observable getCompatibleOutput(Observable observable) {
    return getDelegate().getCompatibleOutput(observable);
  }

  @Override
  public List<IComputableResource> getComputation(ILocator transition) {
    return getDelegate().getComputation(transition);
  }

  @Override
  public Observable getCompatibleInput(Observable observable) {
    return getDelegate().getCompatibleInput(observable);
  }

  @Override
  public List<IComputable> getResources() {
    return getDelegate().getResources();
  }

  @Override
  public IScale getNativeCoverage() {
    try {
      return getDelegate().getCoverage(Klab.INSTANCE.getRootMonitor());
    } catch (KlabException e) {
      Klab.INSTANCE.getRootMonitor().error(e);
    }
    return null;
  }


}
