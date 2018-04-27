package org.integratedmodelling.kim.model;

import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.kim.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.utils.Range;

public class KimObservable extends KimStatement implements IKimObservable {

	private static final long serialVersionUID = 9015149238349286112L;

	public KimObservable(ObservableSemantics statement) {
		super(statement);
		// TODO Auto-generated constructor stub
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
	private IKimConcept by = null;
	private IKimConcept downTo = null;

	@Override
	public boolean isAbstractObservable() {
		return abstractObservable;
	}

	public void setAbstractObservable(boolean abstractObservable) {
		this.abstractObservable = abstractObservable;
	}

	@Override
	public IKimConcept getBy() {
		return by;
	}

	public void setBy(IKimConcept by) {
		this.by = by;
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

	public static KimObservable normalize(ObservableSemantics declaration) {

		KimConcept concept = Kim.INSTANCE.declareConcept(declaration.getDeclaration());
		if (concept == null) {
			return null;
		}

		KimObservable ret = new KimObservable(declaration);

		ret.main = concept;
		ret.formalName = declaration.getName();
		ret.optional = declaration.isOptional();
		ret.abstractObservable = concept.checkAbstractStatus().contains(Type.ABSTRACT) || declaration.isGeneric();
		if (declaration.getValue() != null) {
			String id = declaration.getValue().getId();
			ret.value = Kim.INSTANCE.parseValue(declaration.getValue(),
					Kim.INSTANCE.getNamespace(KimValidator.getNamespace(declaration), false));
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
			ret.by = KimConcept.normalize(declaration.getBy());
		}
		if (declaration.getDownTo() != null) {
			// ret.downTo = KimConcept.normalize(declaration.getDownTo());
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

	public String getDefinition() {

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

}
