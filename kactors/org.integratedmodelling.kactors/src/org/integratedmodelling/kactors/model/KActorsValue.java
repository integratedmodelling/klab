package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Literal;
import org.integratedmodelling.kactors.kactors.Match;
import org.integratedmodelling.kactors.kactors.Quantity;
import org.integratedmodelling.kactors.kactors.Value;
import org.integratedmodelling.klab.utils.Range;

/**
 * Values. Most are reported as the object they are encoded with (strings for
 * most non-POD objects) but carry the type defined by the semantics they are
 * declared with.
 * 
 * @author Ferd
 *
 */
public class KActorsValue extends KActorCodeStatement implements IKActorsValue {

	private Type type;
	private Object value;
	
	// to support costly translations from implementations
	private Object data;

	/**
	 * Create a value that will be matched by anything except errors or null.
	 * 
	 * @return
	 */
	public static KActorsValue anyvalue() {
		return new KActorsValue(Type.ANYVALUE, null);
	}
	
	public static KActorsValue anytrue() {
		return new KActorsValue(Type.ANYTRUE, null);
	}

	/**
	 * Create an error value. Pass an exception, string, or nothing.
	 * 
	 * @return
	 */
	public static KActorsValue error(Object o) {
		return new KActorsValue(Type.ERROR, o);
	}

	public KActorsValue(Value value, KActorCodeStatement parent) {
		super(value, parent);
		if (value.getId() != null) {
			this.type = value.getId().contains(".") ? Type.URN : Type.IDENTIFIER;
			this.value = value.getId();
		} else if (value.getLiteral() != null) {
			this.value = parseLiteral(value.getLiteral());
		} else if (value.getExpression() != null) {
			this.type = Type.EXPRESSION;
			this.value = value.getExpression().substring(1, value.getExpression().length() - 1);
		} else if (value.getQuantity() != null) {
			this.type = Type.QUANTITY;
			this.value = parseQuantity(value.getQuantity());
		} else if (value.getObservable() != null) {
			this.type = Type.OBSERVABLE;
			this.value = value.getObservable().substring(1, value.getObservable().length() - 1);
		} else if (value.getList() != null) {
			this.type = Type.LIST;
			this.value = parseList(value.getList());
		} else if (value.getArgvalue() != null) {
			this.type = Type.NUMBERED_PATTERN;
			this.value = value.getArgvalue();
		} else if (value.getMap() != null) {
			this.type = Type.MAP;
			// TODO
		} else if (value.getTable() != null) {
			this.type = Type.TABLE;
			// TODO
		} else if (value.getUrn() != null) {
			this.type = Type.URN;
			this.value = value.getUrn();
		}
	}

	public KActorsValue(Match match, KActorCodeStatement parent) {
		super(match, parent);
		if (match.getId() != null) {
			this.type = Type.IDENTIFIER;
			this.value = match.getId();
		} else if (match.getLiteral() != null) {
			this.value = parseLiteral(match.getLiteral());
		} else if (match.getExpr() != null) {
			this.type = Type.EXPRESSION;
			this.value = match.getExpr().substring(0, match.getExpr().length() - 1);
		} else if (match.getQuantity() != null) {
			this.type = Type.QUANTITY;
			this.value = parseQuantity(match.getQuantity());
		} else if (match.getObservable() != null) {
			this.type = Type.OBSERVABLE;
			this.value = match.getObservable().substring(0, match.getObservable().length() - 1);
		} else if (match.getSet() != null) {
			this.type = Type.LIST;
			this.value = parseList(match.getSet());
		} else if (match.getBoolean() != null) {
			this.type = Type.BOOLEAN;
			this.value = Boolean.parseBoolean(match.getBoolean());
		} else if (match.getType() != null) {
			this.type = Type.TYPE;
			this.value = match.getType();
		}
	}

	private KActorsValue(Type type, Object value) {
		this.type = type;
		this.value = value;
	}

	private KActorsQuantity parseQuantity(Quantity quantity) {
		KActorsQuantity ret = new KActorsQuantity();
		ret.setValue(parseNumber(quantity.getValue()));
		if (quantity.getUnit() != null) {
			ICompositeNode node = NodeModelUtils.getNode(quantity.getUnit());
			ret.setUnit(node.getText().trim());
		} else if (quantity.getCurrency() != null) {
			ICompositeNode node = NodeModelUtils.getNode(quantity.getCurrency());
			ret.setCurrency(node.getText().trim());
		}
		return ret;
	}

	public List<?> parseList(org.integratedmodelling.kactors.kactors.List list) {
		List<Object> ret = new ArrayList<>();
		for (Value val : list.getContents()) {
			ret.add(new KActorsValue(val, this));
		}
		return ret;
	}

	private Object parseLiteral(Literal literal) {
		if (literal.getBoolean() != null) {
			this.type = Type.BOOLEAN;
			return Boolean.parseBoolean(literal.getBoolean());
		} else if (literal.getNumber() != null) {
			this.type = Type.NUMBER;
			return parseNumber(literal.getNumber());
		} else if (literal.getString() != null) {
			this.type = Type.STRING;
			return literal.getString();
		} else if (literal.getFrom() != null) {
			this.type = Type.RANGE;
			Number high = null;
			Number low = parseNumber(literal.getFrom()).doubleValue();
			if (literal.getTo() != null) {
				high = parseNumber(literal.getTo()).doubleValue();
			}
			return Range.create(low.doubleValue(), high == null ? Double.POSITIVE_INFINITY : high.doubleValue());
		} else if (literal.getDate() != null) {
			this.type = Type.DATE;
			this.value = new KActorsDate(literal.getDate());
		}
		return null;
	}

	public Number parseNumber(org.integratedmodelling.kactors.kactors.Number number) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(number);
		if (number.isExponential() || number.isDecimal()) {
			return Double.parseDouble(node.getText().trim());
		}
		if (number.isLong()) {
			String s = node.getText().trim();
			int nl = s.indexOf('l');
			return Long.parseLong(s.substring(0, nl));
		}
		return Integer.parseInt(node.getText().trim());
	}

	@Override
	public <T> T as(Class<? extends T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Object getValue() {
		if (KActors.INSTANCE.getValueTranslator() != null) {
			return KActors.INSTANCE.getValueTranslator().translate(this, this.value);
		}
		return value;
	}

	/**
	 * Use in translators to support complex and costly data processing.
	 * 
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Use in translators to support complex and costly data processing.
	 * 
	 * @return
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
