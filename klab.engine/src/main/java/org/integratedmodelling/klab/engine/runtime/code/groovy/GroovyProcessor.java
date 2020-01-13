package org.integratedmodelling.klab.engine.runtime.code.groovy;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

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

		GroovyDescriptor(String expression, IExpression.Context context, boolean contextual, boolean forcedScalar) {

			/*
			 * Context should most definitely be nullable
			 */
			INamespace namespace = context == null ? null : context.getNamespace();
			Set<String> knownIdentifiers = context == null ? new HashSet<>()
					: new HashSet<>(context.getStateIdentifiers());
			knownIdentifiers.add("self");

			IScale scale = context == null ? null : context.getScale();

			GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor(namespace, knownIdentifiers,
					scale, context, contextual);

			this.processedCode = processor.process(expression);
			this.identifiers = processor.getIdentifiers();
			this.scalarIds = processor.getScalarIdentifiers();
			this.objectIds = processor.getObjectIdentifiers();
			this.contextualizers = processor.getContextualizers();
			this.errors = processor.getErrors();
			this.forcedScalar = forcedScalar;
			// this.tokens = processor.tokens;
			// this.context = context;
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
	}

	@Override
	public IExpression compile(String expression, IExpression.Context context, boolean forcedScalar) throws KlabValidationException {
		return new GroovyDescriptor(expression, context, true, forcedScalar).compile();
	}

	@Override
	public Descriptor describe(String expression, IExpression.Context context, boolean forcedScalar) throws KlabValidationException {
		return new GroovyDescriptor(expression, context, true, forcedScalar);
	}

	@Override
	public Descriptor describe(String expression, boolean forcedScalar) throws KlabValidationException {
		return new GroovyDescriptor(expression, null, false, forcedScalar);
	}

	@Override
	public String negate(String expression) {
		return "!(" + expression + ")";
	}
	
}
