package org.integratedmodelling.klab.engine.runtime.expressions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

import com.google.common.collect.Sets;

public enum GroovyProcessor implements ILanguageProcessor {

    INSTANCE;

    public static final String ID = "groovy";

    class GroovyDescriptor implements Descriptor {

        String processedCode;
        Collection<String> identifiers;
        private Set<String> scalarIds;
        private Set<String> objectIds;
        private Set<String> contextualizers;
        private List<KimNotification> errors;
        private Map<String, Set<String>> mapIdentifiers;
        private Set<CompilerOption> options;
        private Map<String, Object> variables;
        private boolean forceScalar;
        // private IExpression.Scope context;

        GroovyDescriptor(String expression, IExpression.Scope context, CompilerOption... options) {

            this.options = Sets.newHashSet(options == null ? new CompilerOption[]{} : options);

            /*
             * Context should most definitely be nullable
             */
            INamespace namespace = context == null ? null : context.getNamespace();
            // this.context = context;
            GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor(namespace, context, this.options);

            this.processedCode = processor.process(expression);
            this.identifiers = processor.getIdentifiers();
            this.scalarIds = processor.getScalarIdentifiers();
            this.objectIds = processor.getObjectIdentifiers();
            this.contextualizers = processor.getContextualizers();
            this.mapIdentifiers = processor.getMapIdentifiers();
            this.variables = processor.getVariables();
            this.errors = processor.getErrors();
            this.forceScalar = context == null ? false : context.isForcedScalar();
            
            if (context.getRuntimeScope() != null && !this.options.contains(CompilerOption.IgnoreContext)) {
                Map<String, IObservation> catalog = ((IRuntimeScope)context.getRuntimeScope()).getLocalCatalog(IObservation.class);
                for (String key : catalog.keySet()) {
                    IObservation obs = catalog.get(key);
                    if (!this.variables.containsKey(key)) {
                        variables.put(key, obs);
                    }
                }
            }
            
        }

        @Override
        public Collection<String> getIdentifiers() {
            return identifiers;
        }

        @Override
        public Collection<CompilerOption> getOptions() {
            return options;
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
        public Collection<String> getIdentifiersInScalarScope() {
            return this.scalarIds;
        }

        @Override
        public Collection<String> getIdentifiersInNonscalarScope() {
            return this.objectIds;
        }

        @Override
        public ILanguageExpression compile() {
            return new GroovyExpression(processedCode, true, this);
        }

        @Override
        public boolean isScalar(String identifier) {
            return scalarIds.contains(identifier);
        }

        @Override
        public boolean isNonscalar(String identifier) {
            return objectIds.contains(identifier);
        }

        @Override
        public boolean isNonscalar(Collection<String> stateIdentifiers) {
            for (String id : stateIdentifiers) {
                if (this.objectIds.contains(id)) {
                    return true;
                }
            }
            return false;

        }

        @Override
        public Collection<String> getContextualizers() {
            return contextualizers;
        }

        @Override
        public Map<String, Set<String>> getMapIdentifiers() {
            return mapIdentifiers;
        }

        @Override
        public Map<String, Object> getVariables() {
            return variables;
        }

        @Override
        public boolean isScalar() {

            if (forceScalar) {
                return true;
            }

            for (String id : scalarIds) {
                if (isScalar(id)) {
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public IExpression compile(String expression, IExpression.Scope context, CompilerOption... options)
            throws KlabValidationException {
        return new GroovyDescriptor(expression, context, options).compile();
    }

    @Override
    public Descriptor describe(String expression, IExpression.Scope context, CompilerOption... options)
            throws KlabValidationException {
        return new GroovyDescriptor(expression, context, options);
    }

    @Override
    public String negate(String expression) {
        return "!(" + expression + ")";
    }

}
