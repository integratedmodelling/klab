package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimLookupTable.Argument;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.kim.Classification;
import org.integratedmodelling.kim.kim.ComputableValue;
import org.integratedmodelling.kim.kim.LookupTableArgument;
import org.integratedmodelling.kim.kim.Table;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.kim.ValueAssignment;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.kim.model.Kim.Validator;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerScope;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.services.IExtensionService;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;

public class ComputableResource extends KimStatement implements IContextualizable {

    private static final long serialVersionUID = -5104679843126238555L;

    private String _resourceId = Long.toString(System.nanoTime());

    private String language;
    private Object literal;
    private KimServiceCall serviceCall;
    private KimLookupTable lookupTable;
    private IKimExpression expression;
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
    private IKimAction.Trigger trigger = IKimAction.Trigger.RESOLUTION;
    // if true, this computes a single value to be added to the expression context
    // later.
    private boolean variable;
    private Long timestamp = System.currentTimeMillis();

    /**
     * Slot to save a validated resource so that it won't need to be validated twice. Shouldn't be
     * serialized.
     */
    private transient Object validatedResource;

    private transient AtomicInteger status = new AtomicInteger(0);
    private transient AtomicLong startComputation = new AtomicLong(0);
    private transient AtomicLong endComputation = new AtomicLong(0);

    // all that follows can only be set on a copy as they are runtime-dependent.
    private String targetId;
    private IObservable target;
    // this is the observable of the original actuator, which is only used when the
    // resource is a filter (because that will differ from the observable of the
    // actuator the resource is used in).
    private IObservable originalObservable;
    private boolean copy = false;
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
        ret.resolutionMode = this.resolutionMode;
        ret.originalObservable = this.originalObservable;
        ret.variable = this.variable;
        ret.trigger = this.trigger;
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

    public void setExpression(IKimExpression expression) {
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
     * Use this to build a resource from an inline model value, which can only be a POD (not text),
     * a concept, a function or an expression.
     * 
     * @param inlineComputable
     * @return
     */
    public static ComputableResource create(Object inlineComputable) {

        if (inlineComputable instanceof Number || inlineComputable instanceof Boolean || inlineComputable instanceof IConcept) {
            return new ComputableResource(Optional.of(inlineComputable));
        } else if (inlineComputable instanceof IKimExpression) {
            ComputableResource ret = new ComputableResource();
            ret.resolutionMode = Mode.RESOLUTION;
            ret.type = Type.EXPRESSION;
            ret.expression = (IKimExpression) inlineComputable;
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

    public ComputableResource(Table lookupTable, List<LookupTableArgument> lookupTableArgs, boolean twoWay,
            IKimStatement parent) {

        super(lookupTable, parent);
        setCode(lookupTable);
        this.resolutionMode = Mode.RESOLUTION;
        this.type = Type.LOOKUP_TABLE;
        this.lookupTable = new KimLookupTable(new KimTable(lookupTable, parent), lookupTableArgs, twoWay, parent);
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
            this.expression = new KimExpression(value.getExpr(), null);
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

    public ComputableResource(ValueAssignment statement, ComputableResource condition, Mode resolutionMode, IKimStatement parent,
            Trigger trigger) {
        super(statement, parent);
        setFrom(statement, resolutionMode);
        this.type = Type.CONDITION;
        this.condition = condition;
        this.trigger = trigger;
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
            this.expression = new KimExpression(value.getExpr(), null);
            this.type = Type.EXPRESSION;
            // this.type = IArtifact.Type.VALUE;
        } else if (value.getLiteral() != null) {
            this.literal = Kim.INSTANCE.parseLiteral(value.getLiteral(), Kim.INSTANCE.getNamespace(value));
            this.type = Type.LITERAL;
            // this.type = Utils.getArtifactType(this.literal.getClass());
        } /*
           * else if (value.getModel() != null) { // only accepted when merging this.urn =
           * value.getModel(); this.type = Type.RESOURCE; }
           */

        this.language = value.getLanguage();
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
    public IKimExpression getExpression() {
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
                    ILanguageProcessor processor = ext
                            .getLanguageProcessor(language == null ? IExtensionService.DEFAULT_EXPRESSION_LANGUAGE : language);
                    Descriptor descriptor = getExpression().isForcedScalar()
                            ? processor.describe(expression.getCode(), ext.getScope(CompilerScope.Scalar))
                            : processor.describe(expression.getCode(), ext.getScope());
                    for (String var : descriptor.getIdentifiers()) {
                        requiredResourceNames.add(new Pair<>(var, IArtifact.Type.VALUE));
                    }
                }
            } else if (getLookupTable() != null) {
                for (Argument arg : lookupTable.getArguments()) {
                    if (arg.id != null) {
                        requiredResourceNames.add(new Pair<>(arg.id, IArtifact.Type.VALUE));
                    }
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
    public IContextualizable getCondition() {
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

    // /**
    // * Chain a new resource to the current one. Only happens with URNs so far.
    // *
    // * @param validate
    // */
    // public void chainResource(ComputableResource resource) {
    // siblings.add(resource);
    // }
    //
    // public List<ComputableResource> getSiblings() {
    // return siblings;
    // }

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
        // for (ComputableResource sibling : siblings) {
        // sibling.visit(visitor);
        // }
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
     * Add parameters, possibly interactive, set through annotation in the model containing the
     * resource.
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
            } /*
               * else if (this.mergedUrns != null) { type = Type.MERGED_RESOURCES; }
               */
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
        return "" + getType() + (target == null ? "" : (" -> " + target)) + " " + targetName();
    }

    private String targetName() {
        if (urn != null) {
            return urn;
        } else if (serviceCall != null) {
            return serviceCall.getName();
        } else if (literal != null) {
            return "" + literal;
        }
        return "";
    }

    public IObservable getOriginalObservable() {
        return originalObservable;
    }

    public void setOriginalObservable(IObservable originalObservable) {
        this.originalObservable = originalObservable;
    }

    @Override
    public IGeometry getGeometry() {

        // if (this.mergedGeometry != null) {
        // return this.mergedGeometry;
        // }

        switch(getType()) {
        case RESOURCE:
            IResourceService rs = Services.INSTANCE.getService(IResourceService.class);
            IResource resource = rs == null ? null : rs.resolveResource(this.urn);
            if (resource != null) {
                return resource.getGeometry();
            }
            break;
        case SERVICE:
            IPrototype prototype = this.serviceCall.getPrototype();
            if (prototype != null) {
                return prototype.getGeometry();
            }
            break;
        default:
            break;
        }
        return Geometry.scalar();
    }

    @Override
    public Trigger getTrigger() {
        return this.trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public String getTargetId() {
        return this.targetId;
    }

    @Override
    public boolean isVariable() {
        return variable;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }

    @Override
    public String getSourceCode() {

        // TODO if we ever support more stuff as aux vars
        switch(getType()) {
        case CLASSIFICATION:
            break;
        case CONDITION:
            break;
        case CONVERSION:
            break;
        case EXPRESSION:
            return this.expression.getSourceCode();
        case LITERAL:
            return literal instanceof String ? ("\"" + literal + "\"") : (literal + "");
        case LOOKUP_TABLE:
            break;
        case RESOURCE:
            break;
        case SERVICE:
            break;
        default:
            break;

        }
        return super.getSourceCode();
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public IProvenance getProvenance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEmpty() {
        switch(getType()) {
        case RESOURCE:
            IResource resource = getResource();
            return resource == null || resource.isEmpty();
        default:
            break;
        }
        return false;
    }

    @Override
    public boolean isFinal() {

        switch(getType()) {
        case LITERAL:
            return true;
        case RESOURCE:
            if (validatedResource == null) {
                IResourceService resourceService = Services.INSTANCE.getService(IResourceService.class);
                if (resourceService != null) {
                    validatedResource = resourceService.resolveResource(this.resource);
                }
            }
            return validatedResource instanceof IResource ? ((IResource) validatedResource).getInputs().isEmpty() : false;
        case SERVICE:
            if (getServiceCall() != null) {
                IExtensionService extensionService = Services.INSTANCE.getService(IExtensionService.class);
                IPrototype prototype = extensionService.getPrototype(getServiceCall().getName());
                if (prototype != null && prototype.isFinal()) {
                    return true;
                }
            }
            return false;
        case EXPRESSION:
            // TODO must check if there are state identifiers in the code. Usually there are, so no rush here.
            return false;
        default:
            break;

        }
        return false;
    }

    @Override
    public IContextualizable contextualize(IArtifact target, IContextualizationScope scope) {

//        DebugFile.println(getTimeLabel(scope.getScale().getTime()) + ": " + this);

        if (getType() == Type.RESOURCE) {
        	
            Urn urn = new Urn(getUrn());
            IResourceService resourceService = Services.INSTANCE.getService(IResourceService.class);
            IResource resource = resourceService.contextualizeResource(getResource(), urn.getParameters(), scope.getScale(),
                    target, scope);
            ComputableResource ret = copy();
            ret.validatedResource = resource;
//            DebugFile.println("   " + (resource.isEmpty() ? "(empty)" : resource.getUrn()));
            return ret;
        }
        return this;
    }

    private String getTimeLabel(ITime time) {
        if (time == null) {
            return "[no time]";
        }
        if (time.getTimeType() == ITime.Type.INITIALIZATION) {
            return "[INITIALIZATION]";
        }
        if (time.getResolution().getType() == ITime.Resolution.Type.YEAR) {
            return "[" + time.getStart().getYear() + "-" + time.getEnd().getYear() + "]";
        }
        return "[" + time.toString() + "]";
    }

    @Override
    public IResource getResource() {
        if (validatedResource instanceof IResource) {
            return (IResource) validatedResource;
        }
        if (getUrn() != null && validatedResource == null) {
            IResourceService service = Services.INSTANCE.getService(IResourceService.class);
            if (service != null) {
                validatedResource = service.resolveResource(getUrn());
                if (validatedResource instanceof IResource) {
                    return (IResource) validatedResource;
                }
            }
        }
        return null;
    }

}
