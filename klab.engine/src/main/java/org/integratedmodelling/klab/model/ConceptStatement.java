package org.integratedmodelling.klab.model;

import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.INamespace;

public class ConceptStatement extends KimObject implements IConceptDefinition {

  private static final long serialVersionUID = -1590834207034743591L;

  IConcept                  concept;

  public ConceptStatement(IKimStatement statement) {
    super(statement);
    setDeprecated(statement.isDeprecated());
  }

  public ConceptStatement(IKimStatement statement, IConcept concept) {
    super(statement);
    this.concept = concept;
    setDeprecated(statement.isDeprecated());
  }

  @Override
  public IConcept getConcept() {
    return this.concept;
  }

  @Override
  public String getId() {
    return concept.getName();
  }

  @Override
  public String getName() {
    return concept.getNamespace() + ":" + getId();
  }

  public void set(IConcept concept) {
    this.concept = concept;
  }

  @Override
  public INamespace getNamespace() {
    return Namespaces.INSTANCE.getNamespace(concept.getNamespace());
  }

}
