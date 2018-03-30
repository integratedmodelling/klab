package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.eclipse.xtext.util.Pair;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IPrototype.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.kdecl.Value;
import org.integratedmodelling.kim.kim.ComputableValue;
import org.integratedmodelling.kim.kim.Contextualization;
import org.integratedmodelling.kim.kim.ValueAssignment;

public class ComputableResource extends KimStatement implements IComputableResource {

  private static final long serialVersionUID = -5104679843126238555L;

  private String target;
  private String language;
  private Object literal;
  private KimServiceCall serviceCall;
  private KimLookupTable lookupTable;
  private String expression;
  private KimClassification classification;
  private String urn;
  private String accordingTo;
  private boolean postProcessor;
  private boolean negated;
  private ComputableResource condition;
  private Collection<Pair<String, Type>> requiredResourceNames = new ArrayList<>();

  public void setTarget(String target) {
    this.target = target;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setLiteral(Object literal) {
    this.literal = literal;
  }

  public void setServiceCall(KimServiceCall serviceCall) {
    this.serviceCall = serviceCall;
  }

  public void setLookupTable(KimLookupTable lookupTable) {
    this.lookupTable = lookupTable;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public void setClassification(KimClassification classification) {
    this.classification = classification;
  }

  public void setUrn(String urn) {
    this.urn = urn;
  }

  public void setAccordingTo(String accordingTo) {
    this.accordingTo = accordingTo;
  }

  public void setNegated(boolean negated) {
    this.negated = negated;
  }

  public void setRequiredResourceNames(Collection<Pair<String, Type>> requiredResourceNames) {
    this.requiredResourceNames = requiredResourceNames;
  }

  public ComputableResource(Contextualization statement) {

    super(statement);

    if (statement.getValue() != null) {
      setFrom(statement.getValue());
    }
    if (statement.getLookupTable() != null) {
      setCode(statement.getLookupTable());
      this.lookupTable = new KimLookupTable(statement.getLookupTable());
      this.setPostProcessor(true);
    } else if (statement.getClassification() != null) {
      setCode(statement.getClassification());
      this.classification =
          new KimClassification(statement.getClassification(), statement.isDiscretization());
      this.setPostProcessor(true);
    }
    if ((this.accordingTo = statement.getClassificationProperty()) != null) {
      this.setPostProcessor(true);
    }
  }

  public ComputableResource(Value value) {
    super(value);
    if (value.getFunction() != null) {
      this.serviceCall = new KimServiceCall(value.getFunction());
    } else if (value.getExpr() != null) {
      this.expression = value.getExpr();
    } else if (value.getLiteral() != null) {
      this.literal =
          Kim.INSTANCE.parseLiteral(value.getLiteral(), Kim.INSTANCE.getNamespace(value, false));
    }
  }

  public ComputableResource(ValueAssignment statement, ComputableResource condition) {
    super(statement);
    setFrom(statement);
    this.condition = condition;
  }


  public ComputableResource(String urn) {
    this.urn = urn;
  }

  public ComputableResource(IServiceCall serviceCall) {
    this.serviceCall = (KimServiceCall) serviceCall;
  }

  // using the optional to avoid ambiguities - only used in one point, no need to fuss.
  public ComputableResource(Optional<Object> value) {
    this.literal = value.get();
  }

  private void setFrom(ValueAssignment statement) {

    if (statement.getAssignedValue() != null) {
      setFromValue(statement.getAssignedValue());
    } else if (statement.getExecValue() != null) {
      setFromValue(statement.getExecValue());
    }
    this.target = statement.getTarget();
  }

  private void setFromValue(ComputableValue value) {

    if (value.getUrn() != null) {
      this.urn = value.getUrn();
    } else if (value.getFunction() != null) {
      this.serviceCall = new KimServiceCall(value.getFunction());
    } else if (value.getExpr() != null) {
      this.expression = removeDelimiters(value.getExpr());
    } else if (value.getLiteral() != null) {
      this.literal =
          Kim.INSTANCE.parseLiteral(value.getLiteral(), Kim.INSTANCE.getNamespace(value, false));
    }

    this.language = value.getLanguage();
  }

  private String removeDelimiters(String string) {
    String expr = string.trim();
    if (expr.startsWith("[")) {
      expr = expr.substring(1);
    }
    if (expr.endsWith("]")) {
      expr = expr.substring(0, expr.length() - 1);
    }
    return expr;
  }

  @Override
  public String getTarget() {
    return this.target;
  }

  @Override
  public String getLanguage() {
    return this.language;
  }

  @Override
  public Object getLiteral() {
    return this.literal;
  }

  @Override
  public IServiceCall getServiceCall() {
    return this.serviceCall;
  }

  @Override
  public String getExpression() {
    return this.expression;
  }

  @Override
  public IKimClassification getClassification() {
    return this.classification;
  }

  @Override
  public IKimLookupTable getLookupTable() {
    return this.lookupTable;
  }

  @Override
  public String getAccordingTo() {
    return this.accordingTo;
  }

  @Override
  public String getUrn() {
    return this.urn;
  }

  @Override
  public boolean isNegated() {
    return negated;
  }

  @Override
  public Collection<Pair<String, Type>> getRequiredResourceNames() {
    // TODO insert a collection step
    return requiredResourceNames;
  }

  public boolean isPostProcessor() {
    return postProcessor;
  }

  public void setPostProcessor(boolean postProcessor) {
    this.postProcessor = postProcessor;
  }

  @Override
  public Optional<IComputableResource> getCondition() {
    return condition == null ? Optional.empty() : Optional.of(condition);
  }

  public void setCondition(ComputableResource condition) {
    this.condition = condition;
  }

  @Override
  public Map<String, Object> getParameters() {
    // TODO URN parameters
    return serviceCall != null ? serviceCall.getParameters() : new HashMap<>();
  }

}
