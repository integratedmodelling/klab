package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimStatement;

/**
 * Captures the code aspects of a statement - define, concept, model and observation.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class KimStatement extends KimScope implements IKimStatement {

  private static final long      serialVersionUID = -1426788695739147795L;

  protected int                  firstLine;
  protected int                  lastLine;
  protected int                  firstCharOffset;
  protected int                  lastCharOffset;
  protected List<IKimAnnotation> annotations      = new ArrayList<>();
  protected KimMetadata          metadata;
  protected KimMetadata          documentationMetadata;
  protected String               resource;
  protected boolean              deprecated       = false;
  protected String               deprecation      = null;
  protected String               sourceCode       = null;

  public KimStatement() {}

  public KimStatement(EObject statement) {
    if (statement != null) {
      setCode(statement);
    }
  }

  protected void setCode(EObject statement) {
    ICompositeNode node = NodeModelUtils.findActualNodeFor(statement);
    this.firstLine = node.getStartLine();
    this.lastLine = node.getEndLine();
    this.firstCharOffset = node.getOffset();
    this.lastCharOffset = node.getEndOffset();
    this.uri = EcoreUtil.getURI(statement);
    this.resource = statement.eResource() == null ? "" : statement.eResource().getURI().path();
    sourceCode = node.getText().trim();
  }


  /**
   * Copy constructor. Shallow copy only as it's expected to build substitutes with full k.LAB
   * semantics for contextualization.
   * 
   * KEEP UPDATED WHEN FIELDS CHANGE.
   * 
   * @param model
   */
  protected KimStatement(KimStatement statement) {
    this.firstLine = statement.firstLine;
    this.lastLine = statement.lastLine;
    this.firstCharOffset = statement.firstCharOffset;
    this.lastCharOffset = statement.lastCharOffset;
    this.annotations = statement.annotations;
    this.metadata = statement.metadata;
    this.documentationMetadata = statement.documentationMetadata;
    this.deprecated = statement.deprecated;
    this.deprecation = statement.deprecation;
    this.resource = statement.resource;
  }

  @Override
  public String toString() {
    return getLocationDescriptor();
  }

  @Override
  public String getLocationDescriptor() {
    return resource + " [" + firstLine + "-" + lastLine + "]: " + sourceCode;
  }

  @Override
  public int getFirstLine() {
    return firstLine;
  }

  @Override
  public int getLastLine() {
    return lastLine;
  }

  @Override
  public int getFirstCharOffset() {
    return firstCharOffset;
  }

  @Override
  public int getLastCharOffset() {
    return lastCharOffset;
  }

  @Override
  public String getSourceCode() {
    return sourceCode;
  }

  // reimplement in models to check for runtime conditions
  public boolean isActive() {
    return true;
  }

  @Override
  public List<IKimAnnotation> getAnnotations() {
    return annotations;
  }

  public void setAnnotations(List<IKimAnnotation> annotations) {
    this.annotations = annotations;
  }

  @Override
  public KimMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(KimMetadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public KimMetadata getDocumentationMetadata() {
    return documentationMetadata;
  }

  public void setDocumentation(KimMetadata documentation) {
    this.documentationMetadata = documentation;
  }

  @Override
  public boolean isDeprecated() {
    return deprecated;
  }

  public void setDeprecated(boolean deprecated) {
    this.deprecated = deprecated;
  }

  @Override
  public String getDeprecation() {
    return deprecation;
  }

  public void setDeprecation(String deprecation) {
    this.deprecation = deprecation;
  }

  public void setFirstLine(int firstLine) {
    this.firstLine = firstLine;
  }

  public void setLastLine(int lastLine) {
    this.lastLine = lastLine;
  }

  public void setFirstCharOffset(int firstCharOffset) {
    this.firstCharOffset = firstCharOffset;
  }

  public void setLastCharOffset(int lastCharOffset) {
    this.lastCharOffset = lastCharOffset;
  }

  @Override
  protected String getStringRepresentation(int offset) {
    return sourceCode;
  }

}
