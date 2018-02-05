package org.integratedmodelling.klab.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.KimNamespace;
import org.integratedmodelling.klab.Ontologies;
import org.integratedmodelling.klab.Projects;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class Namespace extends KimObject implements INamespace {

  private static final long serialVersionUID = -6469868584021658804L;

  private String            name;
  private IOntology         ontology;
  private IProject          project;
  private boolean           internal         = false;
  private boolean           canonical        = false;
  private boolean           scenario         = false;
  private boolean           inactive         = false;
  private boolean           isPrivate         = false;
  private long              timestamp        = 0l;

  List<IKimObject>          objects          = new ArrayList<>();
  Map<String, IKimObject>   objectsByName    = new HashMap<>();
  Map<String, Object>       symbolTable      = new HashMap<>();

  /*
   * for incremental building of the knowledge
   */
  List<IAxiom>              axioms           = new ArrayList<>();

  public Namespace(IKimNamespace namespace) {
    super((KimNamespace) namespace);
    this.name = namespace.getName();
    this.isPrivate = namespace.isPrivate();
    this.inactive = namespace.isInactive();
    this.scenario = namespace.isScenario();
    this.ontology = Ontologies.INSTANCE.require(name);
    this.project = Projects.INSTANCE.retrieveOrCreate(namespace.getProject());
    setDeprecated(namespace.isDeprecated());
  }

  public Namespace(String id, @Nullable File file, IOntology ontology) {
    super(null);
    setStatement(new KimNamespace(id, file));
    this.name = id;
    this.ontology = ontology;
    this.timestamp = file == null ? System.currentTimeMillis() : file.lastModified();
  }

  public void addAxiom(IAxiom axiom) {
    this.axioms.add(axiom);
  }

  public void define() {
    this.ontology.define(this.axioms);
    this.axioms.clear();
  }

  @Override
  public IKimNamespace getStatement() {
    return (IKimNamespace) super.getStatement();
  }

  @Override
  public long getTimeStamp() {
    return timestamp;
  }

  @Override
  public IConcept getDomain() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IProject getProject() {
    return project;
  }

  @Override
  public Collection<INamespace> getImportedNamespaces() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getTrainingNamespaces() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getLookupNamespaces() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IScale getCoverage(IMonitor monitor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean hasErrors() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean hasWarnings() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Collection<ICompileNotification> getCodeAnnotations() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IOntology getOntology() {
    return ontology;
  }

  @Override
  public Map<String, Object> getSymbolTable() {
    return symbolTable;
  }

  @Override
  public boolean isScenario() {
    return scenario;
  }

  @Override
  public IMetadata getResolutionCriteria() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<String> getDisjointNamespaces() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public File getLocalFile() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isPrivate() {
    return isPrivate;
  }

  @Override
  public boolean isInactive() {
    return inactive;
  }

  @Override
  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IDocumentation getDocumentation() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setInternal(boolean b) {
    this.internal = b;
  }

  @Override
  public boolean isInternal() {
    return this.internal;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<IKimObject> getObjects() {
    return objects;
  }

  public void addObject(IKimObject object) {
    this.objectsByName.put(object.getId(), object);
    this.objects.add(object);
  }

  @Override
  public List<IKimObject> getAllObjects() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IKimObject getObject(String id) {
    return objectsByName.get(id);
  }

  @Override
  public boolean isCanonical() {
    return canonical;
  }

  public String toString() {
    return "[NS " + getName() + " (" + getObjects().size() + " objects)]";
  }

  @Override
  public String getId() {
    return name;
  }

}
