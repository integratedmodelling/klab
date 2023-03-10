package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.kim.Annotation;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.api.knowledge.IObservable.ResolutionException;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

public class KimObservable extends KimStatement implements IKimObservable {

    private static final long serialVersionUID = 9015149238349286112L;

    public KimObservable(String name, String type) {
        this.nonSemanticType = IArtifact.Type.valueOf(type.toUpperCase());
        this.modelReference = type;
    }

    public KimObservable(ObservableSemantics statement, IKimStatement parent) {
        super(statement, parent);
    }

    private IKimConcept main;
    private Range range;
    private String unit;
    private String currency;
    private String formalName;
    private Object value;
    private boolean hasAttribute;
    private boolean optional;
    private List<Pair<ValueOperator, Object>> valueOperators = new ArrayList<>();
    private String modelReference;
    private IArtifact.Type nonSemanticType = null;
    private boolean generic = false;
    private boolean global = false;
    private boolean exclusive = false;
    private Object defaultValue = null;
    private Set<ResolutionException> resolutionExceptions = EnumSet.noneOf(ResolutionException.class);

    @Override
    public IArtifact.Type getNonSemanticType() {
        return nonSemanticType;
    }

    public void setMain(IKimConcept main) {
        this.main = main;
    }

    public String getName() {
        if (formalName != null) {
            return formalName;
        }
        if (main != null) {
            return CamelCase.toLowerCase(main.getName(), '_');
        }
        // TODO the rest
        return null;
    }

    @SuppressWarnings("unchecked")
    public static KimObservable normalize(ObservableSemantics declaration, IKimStatement parent) {

        KimConcept concept = Kim.INSTANCE.declareConcept(declaration.getDeclaration());
        if (concept == null) {
            return null;
        }

        KimObservable ret = new KimObservable(declaration, parent);
        for (Annotation annotation : declaration.getAnnotations()) {
            ret.getAnnotations()
                    .add(new KimAnnotation(annotation, Kim.INSTANCE.getNamespace(KimValidator.getNamespace(declaration)), ret));
        }

        ret.main = concept;
        ret.global = declaration.isGlobal();
        ret.generic = declaration.isGeneric();
        ret.formalName = declaration.getName();
        ret.optional = declaration.isOptional();
        ret.exclusive = declaration.isExclusive();

        // if (declaration.isDefault()) {
        //
        // if (declaration.getDefaultLiteral() != null) {
        // ret.defaultValue = Kim.INSTANCE.parseLiteral(declaration.getDefaultLiteral(),
        // Kim.INSTANCE.getNamespace(KimValidator.getNamespace(declaration)));
        // } else if (declaration.getDefaultConcept() != null) {
        // ret.defaultValue = Kim.INSTANCE.declareConcept(declaration.getDefaultConcept());
        // }
        //
        // if (declaration.getCauses() != null) {
        // for (String resEx : declaration.getCauses()) {
        // switch(resEx) {
        // case "error":
        // ret.resolutionExceptions.add(ResolutionException.Error);
        // break;
        // case "missing":
        // ret.resolutionExceptions.add(ResolutionException.Missing);
        // break;
        // case "nodata":
        // ret.resolutionExceptions.add(ResolutionException.Nodata);
        // break;
        // }
        // }
        // }
        //
        // if (ret.resolutionExceptions.contains(ResolutionException.Missing)) {
        // ret.optional = true;
        // }
        // }

        if (declaration.getValue() != null) {
            String id = declaration.getValue().getId();
            ret.value = Kim.INSTANCE.parseValue(declaration.getValue(),
                    Kim.INSTANCE.getNamespace(KimValidator.getNamespace(declaration)));
            if (id != null && ret.value instanceof String && id.equals(ret.value)) {
                ret.hasAttribute = true;
            }
        }

        for (org.integratedmodelling.kim.kim.ValueOperator modifier : declaration.getValueOperators()) {

            String op = null;
            if (modifier.getTotal() != null) {
                op = "total";
            } else if (modifier.getSummed() != null) {
                op = "summed";
            } else if (modifier.getAveraged() != null) {
                op = "averaged";
            } else if (modifier.getModifier() == null) {
                op = "down_to";
            } else {
                op = modifier.getModifier();
            }

            ValueOperator operator = ValueOperator.getOperator(op);
            Object operand = new ArrayList<>();
            if (modifier.getComparisonConcept() != null && modifier.getComparisonConcept().size() > 0) {
                if (modifier.getComparisonConcept().size() == 1) {
                    operand = Kim.INSTANCE.declareConcept(modifier.getComparisonConcept().get(0));
                } else {
                    operand = new ArrayList<Object>();
                    for (ConceptDeclaration cc : modifier.getComparisonConcept()) {
                        ((List<Object>) operand).add(Kim.INSTANCE.declareConcept(cc));
                    }
                }
            } else if (modifier.getComparisonObservable() != null) {
                operand = (Kim.INSTANCE.declareObservable(modifier.getComparisonObservable()));
            } else if (modifier.getComparisonValue() != null) {
                operand = (Kim.INSTANCE.parseNumber(modifier.getComparisonValue()));
            }

            ret.valueOperators.add(new Pair<>(operator, operand));
        }

        if (declaration.getUnit() != null) {
            ICompositeNode node = NodeModelUtils.getNode(declaration.getUnit());
            ret.unit = node.getText().trim();
        }
        if (declaration.getCurrency() != null) {
            ICompositeNode node = NodeModelUtils.getNode(declaration.getCurrency());
            ret.currency = node.getText().trim();
        }

        if (declaration.getFrom() != null) {
            double from = Kim.INSTANCE.parseNumber(declaration.getFrom()).doubleValue();
            double to = Double.POSITIVE_INFINITY;
            if (declaration.getTo() != null) {
                to = Kim.INSTANCE.parseNumber(declaration.getTo()).doubleValue();
            }
            ret.range = new Range(from, to, false, false);
        }

        return ret;
    }

    @Override
    public String toString() {
        return getDefinition();
    }

    public ConceptDescriptor getDescriptor() {
        return main == null ? null : ((KimConcept) main).getDescriptor();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof KimObservable && toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String getDefinition() {

        if (nonSemanticType != null) {
            return nonSemanticType + " " + modelReference;
        }

        String ret = "";
        
        if (this.generic) {
            ret += "any ";
        } else if (this.exclusive) {
            ret += "only ";
        } else if (this.global) {
            ret += "all ";
        } 
        
        ret += main.getDefinition();

        if (this.unit != null) {
            ret += " in " + this.unit;
        } else if (this.currency != null) {
            ret += " in " + this.currency;
        } else if (this.range != null) {
            ret += this.range.getKimCode();
        }

        for (Pair<ValueOperator, Object> operator : valueOperators) {

            ret += " " + operator.getFirst().declaration;

            if (operator.getSecond() instanceof IKimConcept) {
                ret += " " + ((IKimConcept) operator.getSecond()).getDefinition();
            } else if (operator.getSecond() instanceof IKimObservable) {
                ret += " (" + ((IKimObservable) operator.getSecond()).getDefinition() + ")";
            } else {
                ret += " " + (operator.getSecond() instanceof String ? "'" : "")
                        + (operator.getSecond() != null ? operator.getSecond().toString() : "")
                        + (operator.getSecond() instanceof String ? "'" : "");
            }
        }
        
        if (this.optional) {
            ret += " optional";
        }

        if (formalName != null && !formalName.isEmpty()) {
            ret += " named " + formalName;
        }

        return ret;
    }

    @Override
    public IKimConcept getMain() {
        return main;
    }

    @Override
    public Range getRange() {
        return range;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public String getFormalName() {
        return formalName;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setMain(KimConcept main) {
        this.main = main;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean hasAttributeIdentifier() {
        return hasAttribute;
    }

    public boolean isHasAttribute() {
        return hasAttribute;
    }

    public void setHasAttribute(boolean hasAttribute) {
        this.hasAttribute = hasAttribute;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public String getModelReference() {
        return modelReference;
    }

    public void setModelReference(String modelReference) {
        this.modelReference = modelReference;
    }

    @Override
    public void visit(Visitor visitor) {

        if (main != null) {
            main.visit(visitor);
        }

        for (Pair<ValueOperator, Object> operator : valueOperators) {
            if (operator.getSecond() instanceof IKimConcept) {
                ((IKimConcept) operator.getSecond()).visit(visitor);
            } else if (operator.getSecond() instanceof IKimObservable) {
                ((IKimObservable) operator.getSecond()).visit(visitor);
            }
        }
    }

    public String validateValue() {

        // any errors on main should be reported elsewhere
        if (main == null || value == null) {
            return null;
        }

        if (main.is(Type.COUNTABLE)) {
            return "A countable observable cannot have pre-defined values: only qualities and traits can";
        }
        if (value instanceof Number && !main.is(Type.QUANTIFIABLE) /* || classifier != null */) {
            return value + " is not an acceptable value for this observable";
        }
        if (value instanceof String) {
            return "A string is not an acceptable value for any observable";
        }
        if (value instanceof IKimConcept
                && !(main.is(Type.CLASS) || main.is(Type.TRAIT) /*
                                                                 * || classifier != null
                                                                 */)) {
            return "A concept is not an acceptable value for this observable";
        }
        if (value instanceof IKimConcept && !main.is(((IKimConcept) value).getFundamentalType())) {
            return value + " is not an acceptable concept for this observable";
        }
        return null;
    }

    public String validateOperators() {
        // TODO!
        return null;
    }

    @Override
    public String getCodeName() {

        if (main == null) {
            return "undefined";
        }

        String ret = main.getCodeName();

        for (Pair<ValueOperator, Object> operator : valueOperators) {

            ret += "_" + operator.getFirst().declaration.replace(' ', '_');

            if (operator.getSecond() instanceof IKimConcept) {
                ret += "_" + ((IKimConcept) operator.getSecond()).getCodeName();
            } else if (operator.getSecond() instanceof IKimObservable) {
                ret += "_" + ((IKimObservable) operator.getSecond()).getCodeName();
            } else {
                ret += "_" + operator.getSecond().toString().replace(' ', '_');
            }
        }

        return ret;
    }

    // @Override
    public boolean needsUnits() {

        boolean checkMetadata = false;
        if (main == null) {
            return false;
        }
        if (main.is(Type.MONEY) || main.is(Type.MONETARY_VALUE) || main.is(Type.EXTENSIVE_PROPERTY)
                || main.is(Type.INTENSIVE_PROPERTY) || main.is(Type.NUMEROSITY)) {
            boolean assignUnits = true;
            // Boolean rescaled = main.getType().getMetadata().get(IMetadata.IM_IS_RESCALED,
            // Boolean.class);
            // if (rescaled == null) {
            // // move on with further checks later
            // checkMetadata = true;
            // for (IConcept trait : Traits.INSTANCE.getTraits(observable.getType())) {
            // if (trait.is(Type.RESCALING)) {
            // assignUnits = false;
            // observable.getType().getMetadata().put(IMetadata.IM_IS_RESCALED, Boolean.TRUE);
            // break;
            // }
            // }
            // if (/* still */ assignUnits) {
            // observable.getType().getMetadata().put(IMetadata.IM_IS_RESCALED, Boolean.FALSE);
            // }
            // } else {
            // assignUnits = !rescaled;
            // }

            /**
             * This part is for the benefit of checking if this describes an extensive value OF some
             * countable, done by needsUnitScaling, which calls this first, so we keep all the logic
             * in one place. If this is a property inherent to something else, this is intensive,
             * not extensive.
             * 
             * FIXME the numerosity check is because at the moment we use the inherent type for the
             * numerosity 'of', but this makes it impossible to have "numerosity of X of Y" - which
             * is a limitation of the language but also a stumbling block for fully general
             * statements.
             */
            if (checkMetadata && !main.is(Type.NUMEROSITY) && !main.is(Type.INTENSIVE_PROPERTY)) {
                // Boolean rescalesInherent =
                // main.getType().getMetadata().get(IMetadata.IM_RESCALES_INHERENT,
                // Boolean.class);
                // if (rescalesInherent == null) {
                // if (main.getInherent() != null) {
                // rescalesInherent = true;
                // } else {
                // rescalesInherent = false;
                // }
                // observable.getType().getMetadata().put(IMetadata.IM_RESCALES_INHERENT,
                // rescalesInherent);
                // }
            }

            return assignUnits;
        }
        return false;
    }

    @Override
    public List<Pair<ValueOperator, Object>> getValueOperators() {
        return valueOperators;
    }

    public boolean isGeneric() {
        return generic;
    }

    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Collection<ResolutionException> getResolutionExceptions() {
        return resolutionExceptions;
    }
}
