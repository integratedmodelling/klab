package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.kim.Classification;
import org.integratedmodelling.kim.kim.ComputableValue;
import org.integratedmodelling.kim.kim.Table;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.kim.ValueAssignment;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Pair;

public class ComputableResource extends KimStatement implements IComputableResource {

	private static final long serialVersionUID = -5104679843126238555L;

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
	private boolean mediation;
	public IResolutionScope.Mode resolutionMode;
	private ComputableResource condition;
	private Pair<IValueMediator, IValueMediator> conversion;
	private Collection<Pair<String, IArtifact.Type>> requiredResourceNames = new ArrayList<>();
	
	/**
	 * Slot to save a validated resource so that it won't need to be validated
	 * twice. Shouldn't be serialized.
	 */
	private transient Object validatedResource;
	private transient Type type;

	// all that follows can only be set on a copy as they are runtime-dependent.
	private String targetId;
	private IObservable target;
	private boolean copy = false;

	/**
	 * If not empty, this is the first of a chain (which cannot be hierarchical).
	 * For now this only happens with URNs.
	 */
	private List<ComputableResource> siblings = new ArrayList<>();

	public IComputableResource copy() {
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
		ret.type = this.type;
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
//		if (!copy) {
//			throw new KlabInternalErrorException("cannot set the target ID on an original computation from k.IM code!");
//		}
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

	protected ComputableResource() {
	}

	public ComputableResource(ValueAssignment statement, Mode resolutionMode, IKimStatement parent) {

		super(statement, parent);
		setFrom(statement, resolutionMode);
	}

	public ComputableResource(Classification statement, boolean isDiscretization, IKimStatement parent) {

		super(statement, parent);
		setCode(statement);
		this.classification = new KimClassification(statement, isDiscretization, parent);
		this.resolutionMode = Mode.RESOLUTION;
		this.setPostProcessor(true);
	}

	public ComputableResource(IKimStatement parent, String classificationProperty) {

		super(null, parent);
		this.accordingTo = classificationProperty;
        this.resolutionMode = Mode.RESOLUTION;
		this.setPostProcessor(true);
	}

	public ComputableResource(Table lookupTable, List<String> lookupTableArgs, IKimStatement parent) {

		super(lookupTable, parent);
		setCode(lookupTable);
        this.resolutionMode = Mode.RESOLUTION;
		this.lookupTable = new KimLookupTable(new KimTable(lookupTable, parent), lookupTableArgs, parent);
		this.setPostProcessor(true);
	}

	public ComputableResource(KimLookupTable table, IKimStatement parent) {
		super(((KimStatement) table).getEObject(), parent);
		this.lookupTable = table;
        this.resolutionMode = Mode.RESOLUTION;
		this.setPostProcessor(true);
	}

	public ComputableResource(Value value, IKimStatement parent) {
		super(value, parent);
		if (value.getFunction() != null) {
			this.serviceCall = new KimServiceCall(value.getFunction(), parent);
		} else if (value.getExpr() != null) {
			this.expression = value.getExpr();
		} else if (value.getLiteral() != null) {
			this.literal = Kim.INSTANCE.parseLiteral(value.getLiteral(), Kim.INSTANCE.getNamespace(value, false));
		}
        this.resolutionMode = Mode.RESOLUTION;
	}

	public ComputableResource(IValueMediator from, IValueMediator to) {
		this.conversion = new Pair<>(from, to);
        this.resolutionMode = Mode.RESOLUTION;
	}

	public ComputableResource(ValueAssignment statement, ComputableResource condition, Mode resolutionMode, IKimStatement parent) {
		super(statement, parent);
 		setFrom(statement, resolutionMode);
		this.condition = condition;
	}

	public ComputableResource(String urn, Mode resolutionMode) {
		this.urn = urn;
		this.resolutionMode = resolutionMode;
	}

	public ComputableResource(IServiceCall serviceCall, Mode resolutionMode) {
		this.serviceCall = (KimServiceCall) serviceCall;
		this.resolutionMode = resolutionMode;
	}

	// using the optional to avoid ambiguities - only used in one point, no need to
	// fuss.
	public ComputableResource(Optional<Object> value) {
		this.literal = value.get();
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

		if (value.getUrn() != null) {
			this.urn = value.getUrn();
		} else if (value.getFunction() != null) {
			this.serviceCall = new KimServiceCall(value.getFunction(), getParent());
		} else if (value.getExpr() != null) {
			this.expression = removeDelimiters(value.getExpr());
		} else if (value.getLiteral() != null) {
			this.literal = Kim.INSTANCE.parseLiteral(value.getLiteral(), Kim.INSTANCE.getNamespace(value, false));
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

}
