package org.integratedmodelling.klab.engine.runtime.code.groovy;

import java.util.Collection;
import java.util.List;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public enum GroovyProcessor implements ILanguageProcessor {
  
  INSTANCE;

  class GroovyDescriptor implements Descriptor {

    GroovyExpressionPreprocessor processor;
    
    GroovyDescriptor(String expression, IRuntimeContext context) {
      // TODO these must all come from the runtime context.
      processor = new GroovyExpressionPreprocessor(context.getNamespace(), null/* knownIdentifiers */, null /* knownDomains*/);
      processor.process(expression);
    }
    
    @Override
    public Collection<String> getIdentifiers() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public boolean isScalar(Collection<String> stateIdentifiers) {
      // TODO Auto-generated method stub
      return false;
    }
    
    public List<KimNotification> getNotifications() {
      return null;
    }
    
    public boolean hasErrors() {
      return false;
    }
    
  }
  
  @Override
  public IExpression compile(String expression, IComputationContext context) throws KlabValidationException {
    return new GroovyExpression(expression);
  }

  @Override
  public IExpression compile(Descriptor expressionDescriptor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Descriptor describe(String expression, IComputationContext context) throws KlabValidationException {
    // TODO Auto-generated method stub
    return null;
  }

}
