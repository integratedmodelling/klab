package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Classifier;
import org.integratedmodelling.kactors.kactors.ListElement;
import org.integratedmodelling.kactors.kactors.Literal;
import org.integratedmodelling.kactors.kactors.MapEntry;
import org.integratedmodelling.kactors.kactors.Match;
import org.integratedmodelling.kactors.kactors.MetadataPair;
import org.integratedmodelling.kactors.kactors.Observable;
import org.integratedmodelling.kactors.kactors.Quantity;
import org.integratedmodelling.kactors.kactors.Tree;
import org.integratedmodelling.kactors.kactors.Value;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.services.IConceptService;
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
	private Map<String, KActorsValue> metadata = new HashMap<>();

	// to support costly translations from implementations
	private Object data;
	// if true when used in matching, the value matched will be any value except the
	// stated
	private boolean exclusive;

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

	public KActorsValue(boolean value, KActorCodeStatement parent) {
		super(null, parent);
		this.type = Type.BOOLEAN;
		this.value = value;
	}

	public KActorsValue(Literal value, KActorCodeStatement parent) {
		super(value, parent);
		if (value.getBoolean() != null) {
			this.value = "true".equals(value.getBoolean());
			this.type = Type.NUMBER;
		} else if (value.getFrom() != null) {
			Number from = parseNumber(value.getFrom());
			Number to = parseNumber(value.getTo());
			this.value = new Range(from.doubleValue(), to.doubleValue(), false, false);
			type = Type.RANGE;
		} else if (value.getNumber() != null) {
			this.value = parseNumber(value.getNumber());
			this.type = Type.BOOLEAN;
		} else if (value.getString() != null) {
			this.value = value.getString();
			this.type = Type.STRING;
		} else if (value.getDate() != null) {
			this.value = new KActorsDate(value.getDate());
			this.type = Type.DATE;
		}
	}

	public KActorsValue(Classifier value, KActorCodeStatement parent) {
		super(value, parent);
		if (value.getOp() != null) {
			Number op = parseNumber(value.getExpression());
			if (value.getOp().isGe()) {
				this.value = new Range(op.doubleValue(), null, false, true);
				this.type = Type.RANGE;
			} else if (value.getOp().isGt()) {
				this.value = new Range(op.doubleValue(), null, true, true);
				this.type = Type.RANGE;
			} else if (value.getOp().isLe()) {
				this.value = new Range(null, op.doubleValue(), true, false);
				this.type = Type.RANGE;
			} else if (value.getOp().isLt()) {
				this.value = new Range(null, op.doubleValue(), true, true);
				this.type = Type.RANGE;
			} else if (value.getOp().isEq()) {
				this.value = op.doubleValue();
				this.type = Type.NUMBER;
			} else if (value.getOp().isNe()) {
				this.value = op.doubleValue();
				this.type = Type.NUMBER;
				this.setExclusive(true);
			}
		} else if (value.getBoolean() != null) {
			this.value = "true".equals(value.getBoolean());
		} else if (value.getId() != null) {
			this.value = value.getId();
			this.type = Type.IDENTIFIER;
		} else if (value.getInt0() != null) {
			Number from = parseNumber(value.getInt0());
			Number to = parseNumber(value.getInt1());
			String lt = value.getLeftLimit();
			String rt = value.getRightLimit();
			if (lt == null)
				lt = "inclusive";
			if (rt == null)
				rt = "exclusive";
			this.value = new Range(from.doubleValue(), to.doubleValue(), lt.equals("exclusive"),
					rt.equals("exclusive"));
			type = Type.RANGE;
		} else if (value.getNodata() != null) {
			this.type = Type.NODATA;
		} else if (value.getNum() != null) {
			this.value = parseNumber(value.getNum());
		} else if (value.getObservable() != null) {
			this.type = Type.OBSERVABLE;
			this.value = parseObservable(value.getObservable());
		} else if (value.getSet() != null) {
			this.type = Type.LIST;
			this.value = parseList(value.getSet(), this);
		} else if (value.getString() != null) {
			this.value = value.getString();
			this.type = Type.STRING;
		} else if (value.getMap() != null) {
			this.value = parseMap(value.getMap(), this);
			this.type = Type.MAP;
		}
	}

	public static String parseObservable(Observable observable) {
		INode node = NodeModelUtils.getNode(observable);
		String ret = NodeModelUtils.getTokenText(node).trim();
		// remove leading spaces and backquotes
		return ret.substring(1, ret.length() - 1).trim().replace("`", "");
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
			this.value = parseObservable(value.getObservable());
		} else if (value.getList() != null) {
			this.type = Type.LIST;
			this.value = parseList(value.getList(), this);
		} else if (value.getArgvalue() != null) {
			this.type = Type.NUMBERED_PATTERN;
			this.value = value.getArgvalue();
		} else if (value.getMap() != null) {
			this.type = Type.MAP;
			this.value = parseMap(value.getMap(), this);
		} else if (value.getTable() != null) {
			this.type = Type.TABLE;
			// TODO
		} else if (value.getUrn() != null) {
			this.type = Type.URN;
			this.value = value.getUrn();
		} else if (value.getConstant() != null) {
			this.type = Type.CONSTANT;
			this.value = value.getConstant();
		} else if (value.getTree() != null) {
			this.type = Type.TREE;
			this.value = parseTree(value.getTree(), this);
		}

		if (value.getMetadata() != null) {
			for (MetadataPair pair : value.getMetadata().getPairs()) {
				String key = pair.getKey().substring(1);
				boolean negative = pair.getKey().startsWith("!");
				KActorsValue v = null;
				if (pair.getValue() != null) {
					v = new KActorsValue(pair.getValue(), this);
				} else {
					v = new KActorsValue(!negative, this);
				}
				metadata.put(key, v);
			}
		}

	}

	public static Map<KActorsValue, KActorsValue> parseMap(org.integratedmodelling.kactors.kactors.Map map,
			KActorCodeStatement parent) {
		Map<KActorsValue, KActorsValue> ret = new LinkedHashMap<>();
		for (MapEntry entry : map.getEntries()) {
			ret.put(new KActorsValue(entry.getClassifier(), parent), new KActorsValue(entry.getValue(), parent));
		}
		return ret;
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
			this.value = parseObservable(match.getObservable());
		} else if (match.getSet() != null) {
			this.type = Type.SET;
			this.value = parseList(match.getSet(), this);
		} else if (match.getBoolean() != null) {
			this.type = Type.BOOLEAN;
			this.value = Boolean.parseBoolean(match.getBoolean());
		} else if (match.getType() != null) {
			this.type = Type.TYPE;
			this.value = match.getType();
		} else if (match.getList() != null) {
			this.type = Type.LIST;
			this.value = parseList(match.getList(), this);
		} else if (match.getConstant() != null) {
			this.type = Type.CONSTANT;
			this.value = match.getConstant();
		} else if (match.isEmpty()) {
			this.type = Type.EMPTY;
		} else if (match.isAnything()) {
			this.type = Type.ANYTHING;
		} else if (match.isException()) {
			this.type = Type.ERROR;
		} else if (match.isStar()) {
			this.type = Type.ANYVALUE;
		}
	}

	KActorsValue(Type type, Object value) {
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

	public List<?> parseList(org.integratedmodelling.kactors.kactors.List list, KActorCodeStatement parent) {
		List<Object> ret = new ArrayList<>();
		for (ListElement val : list.getContents()) {
			if (val.getValue() != null) {
				ret.add(new KActorsValue(val.getValue(), parent));
			} else if (val.getTag() != null) {
				this.setTag(val.getTag());
			}
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

	public static Number parseNumber(org.integratedmodelling.kactors.kactors.Number number) {
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

	public boolean isVariable() {
		if (this.value instanceof List) {
			for (Object val : ((List<?>) this.value)) {
				if (val instanceof KActorsValue && ((KActorsValue) val).getType() == Type.IDENTIFIER) {
					return true;
				}
			}
		}
		return type == Type.IDENTIFIER;
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

	public Graph<KActorsValue, DefaultEdge> parseTree(Tree tree, KActorCodeStatement parent) {
		Graph<KActorsValue, DefaultEdge> ret = new DefaultDirectedGraph<KActorsValue, DefaultEdge>(DefaultEdge.class);
		addNode(tree, parent, ret);
		return ret;
	}

	private Tree getSubTree(Value value) {
		if (value.getList() != null && value.getList().getContents().size() == 1
				// jesus, give me a null-safe operator
				&& value.getList().getContents().get(0).getValue() != null
				&& value.getList().getContents().get(0).getValue().getTree() != null) {
			return value.getList().getContents().get(0).getValue().getTree();
		}
		return null;
	}

	private KActorsValue addNode(Tree treeNode, KActorCodeStatement parent, Graph<KActorsValue, DefaultEdge> ret) {
		KActorsValue value = new KActorsValue(treeNode.getRoot(), parent);
		ret.addVertex(value);
		for (EObject child : treeNode.getValue()) {
			Value vchild = (Value) child;
			KActorsValue vvc = null;
			Tree chtree = getSubTree(vchild);
			if (chtree != null) {
				vvc = addNode(chtree, parent, ret);
			} else {
				vvc = new KActorsValue(vchild, parent);
				ret.addVertex(vvc);
			}
			ret.addEdge(vvc, value);
		}
		return value;
	}

	/**
	 * Return a map for the value containing at least an ID (unique for the same
	 * value), a label for display, and the string value of the type. According to
	 * the type, this may be more or less intelligent.
	 * 
	 * So far unimplemented for collection types.
	 * 
	 * @return
	 */
	public Map<String, String> asMap() {

		IConceptService cservice = Services.INSTANCE.getService(IConceptService.class);

		Map<String, String> ret = new HashMap<>();
		ret.put("id", value == null ? "null" : value.toString());
		ret.put("type", type.name());

		switch (type) {
		case OBSERVABLE:
			Object o = getValue();
			if (cservice != null && o instanceof ISemantic) {
				ret.put("label", cservice.getDisplayLabel(((ISemantic) o).getType()));
				break;
			}
		case ANYTHING:
		case ANYTRUE:
		case ANYVALUE:
		case BOOLEAN:
		case CLASS:
		case DATE:
		case ERROR:
		case EXPRESSION:
		case IDENTIFIER:
		case LIST:
		case MAP:
		case NODATA:
		case NUMBER:
		case NUMBERED_PATTERN:
		case OBSERVATION:
		case QUANTITY:
		case RANGE:
		case REGEXP:
		case SET:
		case STRING:
		case TABLE:
		case TREE:
		case TYPE:
		case URN:
			ret.put("label", ret.get("id"));
			break;
		}

		/*
		 * metadata may override anything
		 */
		for (String key : metadata.keySet()) {
			ret.put(key, metadata.get(key).getValue().toString());
		}

		return ret;
	}

	@Override
	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	/**
	 * Check for truth value. For now we consider true anything that is not null or
	 * not empty, unless it's a boolean or number where we check the actual value
	 * for true value or != 0.
	 * 
	 * @param check
	 * @return
	 */
	public static boolean isTrue(Object check) {
		if (check instanceof Boolean) {
			return (Boolean) check;
		} else if (check instanceof Integer) {
			return ((Integer) check) != 0;
		} else if (check instanceof Number) {
			return ((Number) check).doubleValue() != 0;
		} else if (check instanceof IArtifact) {
			return !((IArtifact) check).isEmpty();
		} else if (check instanceof Collection) {
			return !((Collection<?>) check).isEmpty();
		}
		return check != null;
	}

}
