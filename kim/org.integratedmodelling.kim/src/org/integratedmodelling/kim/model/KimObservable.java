package org.integratedmodelling.kim.model;

import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.Annotation;
import org.integratedmodelling.kim.kim.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.SemanticType;

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
	private boolean abstractObservable;
	private boolean hasAttribute;
	private boolean optional;
	private IKimConcept classifier = null;
	private IKimConcept downTo = null;
//	private IKimConcept aggregator = null;
	private String modelReference;
	private IArtifact.Type nonSemanticType = null;

	@Override
	public IArtifact.Type getNonSemanticType() {
		return nonSemanticType;
	}

	@Override
	public boolean isAbstractObservable() {
		return abstractObservable;
	}

	public void setAbstractObservable(boolean abstractObservable) {
		this.abstractObservable = abstractObservable;
	}

	@Override
	public IKimConcept getClassifier() {
		return classifier;
	}

	public void setClassifier(IKimConcept by) {
		this.classifier = by;
	}

	@Override
	public IKimConcept getDownTo() {
		return downTo;
	}

	public void setDownTo(IKimConcept downTo) {
		this.downTo = downTo;
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

	public static KimObservable normalize(ObservableSemantics declaration, IKimStatement parent) {

		KimConcept concept = Kim.INSTANCE.declareConcept(declaration.getDeclaration());
		if (concept == null) {
			return null;
		}

		KimObservable ret = new KimObservable(declaration, parent);
		for (Annotation annotation : declaration.getAnnotations()) {
			ret.getAnnotations().add(new KimAnnotation(annotation,
					Kim.INSTANCE.getNamespace(KimValidator.getNamespace(declaration)), ret));
		}

		ret.main = concept;
		ret.formalName = declaration.getName();
		ret.optional = declaration.isOptional();
		ret.abstractObservable = concept.checkAbstractStatus().contains(Type.ABSTRACT) || declaration.isGeneric();
		if (declaration.getValue() != null) {
			String id = declaration.getValue().getId();
			ret.value = Kim.INSTANCE.parseValue(declaration.getValue(),
					Kim.INSTANCE.getNamespace(KimValidator.getNamespace(declaration)));
			if (id != null && ret.value instanceof String && id.equals(ret.value)) {
				ret.hasAttribute = true;
			}
		}

		// TODO save units and ranges
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

		if (declaration.getBy() != null) {
			ret.classifier = KimConcept.normalize(declaration.getBy(), parent);
		}

		if (declaration.getDownTo() != null) {
			ret.downTo = KimConcept.normalize(declaration.getDownTo(), parent);
		}

		// if (ret.formalName == null) {
		// ret.formalName = ret.getCodeName();
		// }

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

		String ret = main.getDefinition();

		// TODO

		if (formalName != null) {
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
		if (classifier != null) {
			classifier.visit(visitor);
		}
		if (downTo != null) {
			downTo.visit(visitor);
		}
	}

//	@Override
//	public IKimConcept getAggregator() {
//		return aggregator;
//	}
//
//	public void setAggregator(IKimConcept aggregator) {
//		this.aggregator = aggregator;
//	}

	public String validateValue() {

		// any errors on main should be reported elsewhere
		if (main == null || value == null) {
			return null;
		}

		if (main.is(Type.COUNTABLE)) {
			return "A countable observable cannot have pre-defined values: only qualities and traits can";
		}
		if (value instanceof Number && !main.is(Type.QUANTIFIABLE) || classifier != null) {
			return value + " is not an acceptable value for this observable";
		}
		if (value instanceof String) {
			return "A string is not an acceptable value for any observable";
		}
		if (value instanceof IKimConcept && !(main.is(Type.CLASS) || main.is(Type.TRAIT) || classifier != null)) {
			return "A concept is not an acceptable value for this observable";
		}
		if (value instanceof IKimConcept && !main.is(((IKimConcept) value).getFundamentalType())) {
			return value + " is not an acceptable concept for this observable";
		}
		return null;
	}

	@Override
	public String getCodeName() {
		// FIXME old shit here
		if (main == null) {
			return "undefined";
		}
		String ret = main.getCodeName();
		if (classifier != null) {
			ret = ret + "-by-" + classifier.getCodeName();
		}
		if (downTo != null) {
			ret = ret + "-to-" + downTo.getCodeName();
		}
		return ret; // CamelCase.toLowerCase(new
					// SemanticType(main.getObservable().getName()).getName(), '-');
	}
}
