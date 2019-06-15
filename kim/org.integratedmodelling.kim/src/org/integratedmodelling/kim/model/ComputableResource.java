package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.kim.Classification;
import org.integratedmodelling.kim.kim.ComputableValue;
import org.integratedmodelling.kim.kim.Table;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.kim.ValueAssignment;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.kim.model.Kim.Validator;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.services.IExtensionService;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;

public class ComputableResource extends KimStatement implements IComputableResource {

	private static final long serialVersionUID = -5104679843126238555L;

	private String _resourceId = UUID.randomUUID().toString();

	private String language;
	private Object literal;
	private KimServiceCall serviceCall;
	private KimLookupTable lookupTable;
	private String expression;
	private KimClassification classification;
	private String urn;
	private String accordingTo;
	private String dataflowId = NameGenerator.shortUUID();
	private boolean postProcessor;
	private boolean negated;
	private boolean mediation;
	public IResolutionScope.Mode resolutionMode;
	private ComputableResource condition;
	private Pair<IValueMediator, IValueMediator> conversion;
	private Collection<Pair<String, IArtifact.Type>> requiredResourceNames = null;
	private Map<String, Object> interactiveParameters;
	private Type type;
	
	/**
	 * Slot to save a validated resource so that it won't need to be validated
	 * twice. Shouldn't be serialized.
	 */
	private transient Object validatedResource;
	
    private transient AtomicInteger status = new AtomicInteger(0);
    private transient AtomicLong startComputation = new AtomicLong(0);
    private transient AtomicLong endComputation = new AtomicLong(0);


	// all that follows can only be set on a copy as they are runtime-dependent.
	private String targetId;
	private IObservable target;
	private boolean copy = false;

	/**
	 * If not empty, this is the first of a chain (which cannot be hierarchical).
	 * For now this only happens with URNs.
	 */
	private List<ComputableResource> siblings = new ArrayList<>();

	private List<IAnnotation> externalParameters;

	public ComputableResource copy() {
		ComputableResource ret = new ComputableResource(getEObject(), getParent());
		ret.language = this.language;
		ret.literal = this.literal;
		ret.serviceCall = this.serviceCall;
		ret.lookupTable = this.lookupTable;
		ret.expression = this.expression;
		ret.classification = this.classification;
		ret.urn = this.urn;
		ret.accordingTo = this.accordingTo;
		ret.postProcessor = this.postProcessor;
		ret.negated = this.negated;
		ret.mediation = this.mediation;
		ret.condition = this.condition;
		ret.conversion = this.conversion;
		ret.requiredResourceNames = this.requiredResourceNames;
		ret.validatedResource = this.validatedResource;
		ret.target = this.target;
		ret.targetId = this.targetId;
		ret.copy = true;
		ret.interactiveParameters = this.interactiveParameters;
		ret.externalParameters = this.externalParameters;
		// ret.type = this.type;
		ret.resolutionMode = this.resolutionMode;
		return ret;
	}

	public void setTarget(IObservable target) {
		if (!copy) {
			throw new KlabInternalErrorException("cannot set the target on an original computation from k.IM code!");
		}
		this.target = target;
	}

	public void setTargetId(String targetId) {
		// if (!copy) {
		// throw new KlabInternalErrorException("cannot set the target ID on an original
		// computation from k.IM code!");
		// }
		this.targetId = targetId;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLiteral(Object literal) {
		this.literal = literal;
	}

	public void setServiceCall(KimServiceCall serviceCall) {
		this.serviceCall = serviceCall;
		// this.type = serviceCall.getType();
	}

	public void setLookupTable(KimLookupTable lookupTable) {
		this.lookupTable = lookupTable;
		// this.type = lookupTable.getLookupType();
	}

	public void setExpression(String expression) {
		this.expression = expression;
		// this.type = IArtifact.Type.VALUE;
	}

	public void setClassification(KimClassification classification) {
		this.classification = classification;
		// this.type = IArtifact.Type.CONCEPT;
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

	public void setRequiredResourceNames(Collection<Pair<String, IArtifact.Type>> requiredResourceNames) {
		this.requiredResourceNames = requiredResourceNames;
	}

	protected ComputableResource() {
	}

	/**
	 * Use this to build a resource from an inline model value, which can only be a
	 * POD (not text), a concept, a function or an expression.
	 * 
	 * @param inlineComputable
	 * @return
	 */
	public static ComputableResource create(Object inlineComputable) {

		if (inlineComputable instanceof Number || inlineComputable instanceof Boolean
				|| inlineComputable instanceof IConcept) {
			return new ComputableResource(Optional.of(inlineComputable));
		} else if (inlineComputable instanceof IKimExpression) {
			ComputableResource ret = new ComputableResource();
			ret.resolutionMode = Mode.RESOLUTION;
			ret.type = Type.EXPRESSION;
			ret.expression = ((IKimExpression) inlineComputable).getCode();
			ret.language = ((IKimExpression) inlineComputable).getLanguage();
			return ret;
		} else if (inlineComputable instanceof KimServiceCall) {
			ComputableResource ret = new ComputableResource();
			ret.type = Type.SERVICE;
			ret.serviceCall = (KimServiceCall) inlineComputable;
			ret.resolutionMode = Mode.RESOLUTION;
			return ret;
		}
		return null;
	}

	public ComputableResource(ValueAssignment statement, Mode resolutionMode, IKimStatement parent) {
		super(statement, parent);
		setFrom(statement, resolutionMode);
	}

	public ComputableResource(Classification statement, boolean isDiscretization, IKimStatement parent) {

		super(statement, parent);
		setCode(statement);
		this.classification = new KimClassification(statement, isDiscretization, parent);
		this.type = Type.CLASSIFICATION;
		this.resolutionMode = Mode.RESOLUTION;
		this.setPostProcessor(true);
	}

	public ComputableResource(IKimStatement parent, String classificationProperty) {

		super(null, parent);
		this.accordingTo = classificationProperty;
		this.type = Type.CLASSIFICATION;
		this.resolutionMode = Mode.RESOLUTION;
		this.setPostProcessor(true);
	}

	public ComputableResource(Table lookupTable, List<String> lookupTableArgs, IKimStatement parent) {

		super(lookupTable, parent);
		setCode(lookupTable);
		this.resolutionMode = Mode.RESOLUTION;
		this.type = Type.LOOKUP_TABLE;
		this.lookupTable = new KimLookupTable(new KimTable(lookupTable, parent), lookupTableArgs, parent);
		this.setPostProcessor(true);
	}

	public ComputableResource(KimLookupTable table, IKimStatement parent) {
		super(((KimStatement) table).getEObject(), parent);
		this.lookupTable = table;
		this.type = Type.LOOKUP_TABLE;
		this.resolutionMode = Mode.RESOLUTION;
		this.setPostProcessor(true);
	}

	public ComputableResource(Value value, IKimStatement parent) {
		super(value, parent);
		if (value.getFunction() != null) {
			this.serviceCall = new KimServiceCall(value.getFunction(), parent);
			this.type = Type.SERVICE;
		} else if (value.getExpr() != null) {
			this.expression = removeDelimiters(value.getExpr());
			this.type = Type.EXPRESSION;
		} else if (value.getLiteral() != null) {
			this.type = Type.LITERAL;
			this.literal = Kim.INSTANCE.parseLiteral(value.getLiteral(), Kim.INSTANCE.getNamespace(value));
		}
		this.resolutionMode = Mode.RESOLUTION;
	}

	public ComputableResource(IValueMediator from, IValueMediator to) {
		this.conversion = new Pair<>(from, to);
		this.type = Type.CONVERSION;
		this.resolutionMode = Mode.RESOLUTION;
	}

	public ComputableResource(ValueAssignment statement, ComputableResource condition, Mode resolutionMode,
			IKimStatement parent) {
		super(statement, parent);
		setFrom(statement, resolutionMode);
		this.type = Type.CONDITION;
		this.condition = condition;
	}

	public ComputableResource(String urn, Mode resolutionMode) {
		this.urn = urn;
		this.type = Type.RESOURCE;
		this.resolutionMode = resolutionMode;
	}

	public ComputableResource(IServiceCall serviceCall, Mode resolutionMode) {
		this.serviceCall = (KimServiceCall) serviceCall;
		this.type = Type.SERVICE;
		this.resolutionMode = resolutionMode;
	}

	// using the optional to avoid ambiguities - only used in one point, no need to
	// fuss.
	public ComputableResource(Optional<Object> value) {
		this.literal = value.get();
		this.type = Type.LITERAL;
		this.resolutionMode = Mode.RESOLUTION;
	}

	private ComputableResource(EObject eObject, IKimStatement parent) {
		super(eObject, parent);
	}

	private void setFrom(ValueAssignment statement, Mode resolutionMode) {

		if (statement.getAssignedValue() != null) {
			setFromValue(statement.getAssignedValue());
		} else if (statement.getExecValue() != null) {
			setFromValue(statement.getExecValue());
		}
		this.targetId = statement.getTarget();
		this.resolutionMode = resolutionMode;
	}

	private void setFromValue(ComputableValue value) {

		if (value.getCondition() != null) {
			this.condition = new ComputableResource(value.getCondition(), this);
			this.negated = value.isConditionNegated();
			this.type = Type.CONDITION;
		}

		if (value.getUrn() != null) {
			this.urn = value.getUrn();
			this.type = Type.RESOURCE;
			// TODO find a way to establish type
		} else if (value.getFunction() != null) {
			this.serviceCall = new KimServiceCall(value.getFunction(), getParent());
			this.type = Type.SERVICE;
			// this.type = this.serviceCall.getType();
		} else if (value.getExpr() != null) {
			this.expression = removeDelimiters(value.getExpr());
			this.type = Type.EXPRESSION;
		// this.type = IArtifact.Type.VALUE;
		} else if (value.getLiteral() != null) {
			this.literal = Kim.INSTANCE.parseLiteral(value.getLiteral(), Kim.INSTANCE.getNamespace(value));
			this.type = Type.LITERAL;
			// this.type = Utils.getArtifactType(this.literal.getClass());
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
	public IObservable getTarget() {
		return this.target;
	}

	// used in mediations only
	public String getMediationTargetId() {
		return this.targetId;
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
	public Collection<Pair<String, IArtifact.Type>> getInputs() {

		if (requiredResourceNames == null) {

			requiredResourceNames = new ArrayList<>();

			if (getExpression() != null) {
				IExtensionService ext = Services.INSTANCE.getService(IExtensionService.class);
				if (ext != null) {
					ILanguageProcessor processor = ext.getLanguageProcessor(
							language == null ? IExtensionService.DEFAULT_EXPRESSION_LANGUAGE : language);
					Descriptor descriptor = processor.describe(expression);
					for (String var : descriptor.getIdentifiers()) {
						requiredResourceNames.add(new Pair<>(var, IArtifact.Type.VALUE));
					}
				}
			} else if (getLookupTable() != null) {
				for (String arg : lookupTable.getArguments()) {
					requiredResourceNames.add(new Pair<>(arg, IArtifact.Type.VALUE));
				}
			} else if (getUrn() != null) {
				Validator validator = Kim.INSTANCE.getValidator();
				if (validator != null) {
					UrnDescriptor urnd = validator.classifyUrn(getUrn());
					if (urnd != null) {
						requiredResourceNames.addAll(urnd.getDependencies());
					}
				}
			} else if (getServiceCall() != null) {
			    
			}
		}
		return requiredResourceNames;
	}

	public boolean isPostProcessor() {
		return postProcessor;
	}

	public void setPostProcessor(boolean postProcessor) {
		this.postProcessor = postProcessor;
	}

	@Override
	public IComputableResource getCondition() {
		return condition;
	}

	public void setCondition(ComputableResource condition) {
		this.condition = condition;
	}

	@Override
	public Map<String, Object> getParameters() {
		// TODO URN parameters
		return serviceCall != null ? serviceCall.getParameters() : new HashMap<>();
	}

	@Override
	public Pair<IValueMediator, IValueMediator> getConversion() {
		return conversion;
	}

	public void setConversion(Pair<IValueMediator, IValueMediator> conversion) {
		this.conversion = conversion;
	}

	public void setMediation(boolean b) {
		this.mediation = b;
	}

	@Override
	public boolean isMediation() {
		return this.mediation;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValidatedResource(Class<T> cls) {
		return (T) validatedResource;
	}

	public void setValidatedResource(Object validatedResource) {
		this.validatedResource = validatedResource;
		if (validatedResource instanceof IClassification) {
			this.type = Type.CLASSIFICATION;
		} else if (validatedResource instanceof ILookupTable) {
			this.type = Type.LOOKUP_TABLE;
		}
	}

	/**
	 * Chain a new resource to the current one. Only happens with URNs so far.
	 * 
	 * @param validate
	 */
	public void chainResource(ComputableResource resource) {
		siblings.add(resource);
	}

	public List<ComputableResource> getSiblings() {
		return siblings;
	}

	@Override
	public void visit(Visitor visitor) {
		if (classification != null) {
			classification.visit(visitor);
		} else if (lookupTable != null) {
			lookupTable.visit(visitor);
		} else if (serviceCall != null) {
			serviceCall.visit(visitor);
		}
		if (condition != null) {
			condition.visit(visitor);
		}
		for (ComputableResource sibling : siblings) {
			sibling.visit(visitor);
		}
	}

	@Override
	public Mode getComputationMode() {
		return resolutionMode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accordingTo == null) ? 0 : accordingTo.hashCode());
		result = prime * result + ((classification == null) ? 0 : classification.hashCode());
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((conversion == null) ? 0 : conversion.hashCode());
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((literal == null) ? 0 : literal.hashCode());
		result = prime * result + ((lookupTable == null) ? 0 : lookupTable.hashCode());
		result = prime * result + (mediation ? 1231 : 1237);
		result = prime * result + (negated ? 1231 : 1237);
		result = prime * result + ((resolutionMode == null) ? 0 : resolutionMode.hashCode());
		result = prime * result + ((serviceCall == null) ? 0 : serviceCall.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((targetId == null) ? 0 : targetId.hashCode());
		result = prime * result + ((urn == null) ? 0 : urn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComputableResource other = (ComputableResource) obj;
		if (accordingTo == null) {
			if (other.accordingTo != null)
				return false;
		} else if (!accordingTo.equals(other.accordingTo))
			return false;
		if (classification == null) {
			if (other.classification != null)
				return false;
		} else if (!classification.equals(other.classification))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (conversion == null) {
			if (other.conversion != null)
				return false;
		} else if (!conversion.equals(other.conversion))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (literal == null) {
			if (other.literal != null)
				return false;
		} else if (!literal.equals(other.literal))
			return false;
		if (lookupTable == null) {
			if (other.lookupTable != null)
				return false;
		} else if (!lookupTable.equals(other.lookupTable))
			return false;
		if (mediation != other.mediation)
			return false;
		if (negated != other.negated)
			return false;
		if (resolutionMode != other.resolutionMode)
			return false;
		if (serviceCall == null) {
			if (other.serviceCall != null)
				return false;
		} else if (!serviceCall.equals(other.serviceCall))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (targetId == null) {
			if (other.targetId != null)
				return false;
		} else if (!targetId.equals(other.targetId))
			return false;
		if (urn == null) {
			if (other.urn != null)
				return false;
		} else if (!urn.equals(other.urn))
			return false;
		return true;
	}

	public String getDataflowId() {
		return dataflowId;
	}

	public void setDataflowId(String dataflowId) {
		this.dataflowId = dataflowId;
	}

	@Override
	public Collection<String> getInteractiveParameters() {

		List<String> ret = new ArrayList<>();
		if (serviceCall != null) {
			ret.addAll(serviceCall.getInteractiveParameters());
		}
		// TODO other interactives

		/*
		 * Anything passed through model annotations
		 */
		if (externalParameters != null) {
			for (IAnnotation a : externalParameters) {
				if (a.get("interact", Boolean.TRUE)) {
					ret.add(a.get("name", String.class));
				}
			}
		}

		return ret;
	}

	public Map<String, Object> getModifiedParameters() {
		return interactiveParameters;
	}

	// @Override
	public void setInteractiveParameter(String parameterId, Object value) {
		if (this.interactiveParameters == null) {
			this.interactiveParameters = new HashMap<>();
		}
		this.interactiveParameters.put(parameterId, value);
	}

	public String getId() {
		return _resourceId;
	}

	/*
	 * Add parameters, possibly interactive, set through annotation in the model
	 * containing the resource.
	 */
	public void addParameters(List<IAnnotation> parameters) {
		this.externalParameters = parameters;
	}

	public Collection<IAnnotation> getExternalParameters() {
		return this.externalParameters;
	}

	@Override
	public boolean isAvailable() {
		if (this.urn != null) {
			return Services.INSTANCE.getService(IResourceService.class).isResourceOnline(this.urn);
		} // TODO anything else?
		return true;
	}

	@Override
	public Type getType() {
		if (type == null) {
			// FIXME this shouldn't be necessary but I can't find a way to fix it
			if (this.accordingTo != null || this.classification != null || this.validatedResource instanceof IClassification) {
				type = Type.CLASSIFICATION;
			} else if (this.lookupTable != null) {
				type = Type.LOOKUP_TABLE;
			} else if (this.expression != null) {
				type = Type.EXPRESSION;
			} else if (this.conversion != null) {
				type = Type.CONVERSION;
			} else if (this.literal != null) {
				type = Type.LITERAL;
			} else if (this.serviceCall != null) {
				type = Type.SERVICE;
			} else if (this.condition != null) {
				type = Type.CONDITION;
			} else if (this.urn != null) {
				type = Type.RESOURCE;
			}
		}
		
		if (type /* still */ == null) {
			throw new KlabInternalErrorException("internal: resource type is null!");
		}
		return type;
	}

	protected void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "<" + getType() + " -> " + (target == null ? "default" : target) + " [" + dataflowId + "]>";
	}
}
