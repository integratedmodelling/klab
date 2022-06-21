package org.integratedmodelling.klab.engine.runtime.code.groovy;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
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
		private boolean forcedScalar;
		private Map<String, Set<String>> mapIdentifiers;
		private Set<CompilerOption> options;
		private IExpression.Scope context;
		
		GroovyDescriptor(String expression, IExpression.Scope context, boolean contextual, CompilerOption... options) {

			this.options = Sets.newHashSet(options == null ? new CompilerOption[] {} : options);
			
			// we may force the context to be ignored through options
			contextual = contextual && !this.options.contains(CompilerOption.IgnoreContext);
			
			/*
			 * Context should most definitely be nullable
			 */
			INamespace namespace = context == null ? null : context.getNamespace();
			Set<String> knownIdentifiers = context == null ? new HashSet<>()
					: new HashSet<>(context.getStateIdentifiers());
			this.context = context;
			knownIdentifiers.add("self");

			IScale scale = context == null ? null : context.getScale();

			GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor(namespace, knownIdentifiers,
					scale, context, contextual, this.options);

			this.processedCode = processor.process(expression);
			this.identifiers = processor.getIdentifiers();
			this.scalarIds = processor.getScalarIdentifiers();
			this.objectIds = processor.getObjectIdentifiers();
			this.contextualizers = processor.getContextualizers();
			this.mapIdentifiers = processor.getMapIdentifiers();
			this.errors = processor.getErrors();
			this.forcedScalar = this.options.contains(CompilerOption.ForcedScalar);
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
		public boolean isForcedScalar() {
			return forcedScalar;
		}

		@Override
		public Map<String, Set<String>> getMapIdentifiers() {
			return mapIdentifiers;
		}

        @Override
        public Map<String, Object> getVariables() {
            // TODO Auto-generated method stub
            return null;
        }
	}

	@Override
	public IExpression compile(String expression, IExpression.Scope context, CompilerOption... options)
			throws KlabValidationException {
		return new GroovyDescriptor(expression, context, true, options).compile();
	}

	@Override
	public Descriptor describe(String expression, IExpression.Scope context, CompilerOption... options)
			throws KlabValidationException {
		return new GroovyDescriptor(expression, context, true, options);
	}

	@Override
	public Descriptor describe(String expression, CompilerOption... options) throws KlabValidationException {
		return new GroovyDescriptor(expression, null, false, options);
	}

	@Override
	public String negate(String expression) {
		return "!(" + expression + ")";
	}

}
