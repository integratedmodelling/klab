package org.integratedmodelling.klab.owl;

import java.util.Collection;
import java.util.Set;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.utils.CamelCase;
import org.integratedmodelling.kim.utils.Range;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.exceptions.KlabException;

public class Observable implements IObservable {

  private Concept         observable;
  private Concept         main;
  private String          name;
  private String          declaration;
  private boolean         isAbstract;
  private Range           range;
  private Unit            unit;
  private Currency        currency;
  private Concept         by;
  private Concept         downTo;
  private Object          value;
  private ObservationType observationType;
  private boolean         optional;
  private boolean         generic;

  public static Observable promote(IConceptDefinition concept) {
    return promote(concept.getConcept());
  }

  public static Observable promote(IConcept concept) {
    Observable ret = new Observable();
    ret.observable = (Concept) concept;
    ret.main = (Concept) concept;
    ret.declaration = concept.getDefinition().trim();
    ret.isAbstract = concept.isAbstract();
    ret.generic = concept.isAbstract();
    return ret;
  }

  @Override
  public IConcept getType() {
    return observable;
  }

  @Override
  public String getLocalName() {
    if (name == null) {
      name = CamelCase.toLowerCase(observable.getName(), '_');
    }
    return name;
  }

  @Override
  public IConcept getMain() {
    return main;
  }

  @Override
  public IConcept getDownTo() {
    return downTo;
  }

  @Override
  public IConcept getBy() {
    return by;
  }

  @Override
  public Range getRange() {
    return range;
  }

  @Override
  public IUnit getUnit() {
    return unit;
  }

  @Override
  public ICurrency getCurrency() {
    return currency;
  }

  @Override
  public boolean isAbstract() {
    return isAbstract;
  }

  public IConcept getObservable() {
    return observable;
  }

  public void setObservable(Concept observable) {
    this.observable = observable;
  }

  public void setMain(Concept main) {
    this.main = main;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDeclaration(String declaration) {
    this.declaration = declaration.trim();
  }

  public void setAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
  }

  public void setRange(Range range) {
    this.range = range;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public void setBy(Concept by) {
    this.by = by;
  }

  public void setDownTo(Concept downTo) {
    this.downTo = downTo;
  }

  @Override
  public boolean is(Type type) {
    return observable.is(type);
  }

  @Override
  public Collection<IConcept> getParents() {
    return observable.getParents();
  }

  @Override
  public Collection<IConcept> getAllParents() {
    return observable.getAllParents();
  }

  @Override
  public Collection<IConcept> getChildren() {
    return observable.getChildren();
  }

  @Override
  public Collection<IProperty> getProperties() {
    return observable.getProperties();
  }

  @Override
  public Collection<IProperty> getAllProperties() {
    return observable.getAllProperties();
  }

  @Override
  public Collection<IConcept> getPropertyRange(IProperty property) throws KlabException {
    return observable.getPropertyRange(property);
  }

  @Override
  public Object getValueOf(IProperty property) throws KlabException {
    return observable.getValueOf(property);
  }

  @Override
  public IConcept getParent() {
    return observable.getParent();
  }

  @Override
  public int getPropertiesCount(String property) {
    return observable.getPropertiesCount(property);
  }

  @Override
  public IConcept getLeastGeneralCommonConcept(IConcept c) {
    return observable.getLeastGeneralCommonConcept(c);
  }

  @Override
  public Set<IConcept> getSemanticClosure() {
    return observable.getSemanticClosure();
  }

  @Override
  public int[] getCardinality(IProperty property) {
    return observable.getCardinality(property);
  }

  @Override
  public Collection<IConcept> getDisjointConcreteChildren() {
    return observable.getDisjointConcreteChildren();
  }

  @Override
  public Collection<IProperty> findRestrictingProperty(IConcept target) {
    return observable.findRestrictingProperty(target);
  }

  @Override
  public String getDefinition() {
    return declaration;
  }

  @Override
  public String getUrn() {
    return observable.getUrn();
  }

  @Override
  public String getURI() {
    return observable.getURI();
  }

  @Override
  public String getNamespace() {
    return observable.getNamespace();
  }

  @Override
  public boolean is(ISemantic concept) {
    return observable.is(concept);
  }

  @Override
  public IConcept getDomain() {
    return observable.getDomain();
  }

  @Override
  public IOntology getOntology() {
    return observable.getOntology();
  }

  @Override
  public IMetadata getMetadata() {
    return observable.getMetadata();
  }

  @Override
  public Object getValue() {
    return value;
  }

  public String getDeclaration() {
    return declaration;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public String getName() {
    return observable.getName();
  }

  @Override
  public ObservationType getObservationType() {
    if (observationType == null && observable != null) {
      if (by != null || observable.is(Type.CLASS) || observable.is(Type.TRAIT)) {
        observationType = ObservationType.CLASSIFICATION;
      } else if (observable.is(Type.PRESENCE)) {
        observationType = ObservationType.VERIFICATION;
      } else if (observable.is(Type.QUALITY)) { // don't reorder these!
        observationType = ObservationType.QUANTIFICATION;
      } else if (observable.is(Type.COUNTABLE)) {
        observationType = ObservationType.INSTANTIATION;
      } else if (observable.is(Type.CONFIGURATION)) {
        observationType = ObservationType.DETECTION;
      } else if (observable.is(Type.PROCESS)) {
        observationType = ObservationType.SIMULATION;
      }
    }
    return observationType;
  }

  @Override
  public boolean isExtensive(IConcept extent) {
    return observable != null && unit != null && observable.is(Type.EXTENSIVE_PROPERTY)
        && Units.INSTANCE.isDensity(unit, extent);
  }

  public boolean isOptional() {
    return optional;
  }

  public void setOptional(boolean optional) {
    this.optional = optional;
  }

  public void setObservationType(ObservationType observationType) {
    this.observationType = observationType;
  }

  public boolean isGeneric() {
    return generic;
  }

  public void setGeneric(boolean generic) {
    this.generic = generic;
  }

  public String toString() {
    return "[" + this.declaration + "]";
  }

}
