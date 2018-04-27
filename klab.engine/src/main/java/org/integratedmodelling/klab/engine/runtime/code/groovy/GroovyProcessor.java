package org.integratedmodelling.klab.engine.runtime.code.groovy;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.engine.runtime.code.groovy.GroovyExpressionPreprocessor.TokenDescriptor;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public enum GroovyProcessor implements ILanguageProcessor {

  INSTANCE;

  class GroovyDescriptor implements Descriptor {

    String processedCode;
    Collection<String> identifiers;
    private Set<String> scalarIds;
    private Set<String> objectIds;
    private List<KimNotification> errors;
    private List<TokenDescriptor> tokens;
    private IRuntimeContext context;

    GroovyDescriptor(String expression, IRuntimeContext context) {
      GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor(
          context.getNamespace(), context.getArtifacts(IState.class).stream()
              .map(data -> data.getFirst()).collect(Collectors.toSet()),
          context.getScale());
      this.processedCode = processor.process(expression);
      this.identifiers = processor.getIdentifiers();
      this.scalarIds = processor.getScalarIdentifiers();
      this.objectIds = processor.getObjectIdentifiers();
      this.errors = processor.getErrors();
      this.tokens = processor.tokens;
      this.context = context;
    }

    @Override
    public Collection<String> getIdentifiers() {
      return identifiers;
    }

    @Override
    public boolean isScalar(Collection<String> stateIdentifiers) {
      for (String id : stateIdentifiers) {
        if (this.scalarIds.contains(id)) {
          return true;
        }
      }
      return false;
    }

    public List<KimNotification> getNotifications() {
      return errors;
    }

    public boolean hasErrors() {
      return errors.size() > 0;
    }

    @Override
    public IExpression compile() {
      String ret = "";
      for (TokenDescriptor token : tokens) {
        ret += token.translate(context);
      }
      return new GroovyExpression(ret, true);
    }

    @Override
    public boolean isScalar(String identifier) {
      return scalarIds.contains(identifier);
    }
  }

  @Override
  public IExpression compile(String expression, IComputationContext context)
      throws KlabValidationException {
    return new GroovyDescriptor(expression, (IRuntimeContext) context).compile();
  }

  @Override
  public Descriptor describe(String expression, IComputationContext context)
      throws KlabValidationException {
    return new GroovyDescriptor(expression, (IRuntimeContext) context);
  }

  @Override
  public String negate(String expression) {
    return "!(" + expression + ")";
  }

}
