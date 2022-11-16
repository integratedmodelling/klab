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
import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Scope;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Call;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Classifier;
import org.integratedmodelling.kactors.kactors.ListElement;
import org.integratedmodelling.kactors.kactors.Literal;
import org.integratedmodelling.kactors.kactors.MapEntry;
import org.integratedmodelling.kactors.kactors.Match;
import org.integratedmodelling.kactors.kactors.MessageCall;
import org.integratedmodelling.kactors.kactors.MetadataPair;
import org.integratedmodelling.kactors.kactors.Observable;
import org.integratedmodelling.kactors.kactors.Quantity;
import org.integratedmodelling.kactors.kactors.Tree;
import org.integratedmodelling.kactors.kactors.Value;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.services.IActorsService;
import org.integratedmodelling.klab.api.services.IConceptService;
import org.integratedmodelling.klab.api.services.IExtensionService;
import org.integratedmodelling.klab.api.services.IObservableService;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * Values. Most are reported as the object they are encoded with (strings for most non-POD objects)
 * but carry the type defined by the semantics they are declared with. Values also encode
 * expressions (for now just a ternary operation). They may be deferred, meaning the container is
 * passed to functions without evaluation until the time of usage of the actual evaluated value.
 * 
 * @author Ferd
 */
public class KActorsValue extends KActorCodeStatement implements IKActorsValue {

    private Type type;
    private Object value;
    private boolean deferred = false;
    private DataType cast = null;

    // to support costly translations from implementations
    private Object data;
    // if true when used in matching, the value matched will be any value except the
    // stated
    private boolean exclusive;
    private Constructor constructor;

    /*
     * if expresion type is TERNARY_OPERATOR, we are the condition and these are the two actual
     * values according to our outcome when evaluated in context.
     */
    private ExpressionType expressionType = ExpressionType.VALUE;
    private KActorsValue trueCase;
    private KActorsValue falseCase;
    private List<Call> callChain;

    /**
     * Constructors can be either for Java objects (with classname and possibly classpath not null)
     * or for components (with component != null). The value type is either OBJECT or COMPONENT and
     * the source idiom is the Java class constructor (for OBJECT) or a component created with 'new'
     * (for COMPONENT).
     * 
     * @author Ferd
     *
     */
    public static class Constructor {

        private String classpath;
        private String classname;
        private String component;
        private KActorsArguments arguments;

        /**
         * Package name. May be null, in which case it should reference classes specifically enabled
         * for integration. Non-null, arbitrary classes are a security risk and should not be
         * allowed as a rule, but it's here syntactically.
         * 
         * @return
         */
        public String getClasspath() {
            return classpath;
        }

        void setClasspath(String classpath) {
            this.classpath = classpath;
        }

        /**
         * Only field guaranteed to not be null.
         * 
         * @return
         */
        public String getClassname() {
            return classname;
        }

        void setClassname(String classname) {
            this.classname = classname;
        }

        /**
         * Arguments to constructor call. Never null, possibly empty.
         * 
         * @return
         */
        public KActorsArguments getArguments() {
            return arguments;
        }

        public void setArguments(KActorsArguments arguments) {
            this.arguments = arguments;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }
    }

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

    public KActorsValue(String expression, KActorCodeStatement parent) {
        super(null, parent);
        this.type = Type.EXPRESSION;
        if (expression.startsWith("[") && expression.endsWith("]")) {
            expression = expression.substring(1, expression.length() - 1);
        }
        this.value = parseExpression(expression);
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
            if (value.getString().startsWith("#") && StringUtil.isUppercase(value.getString().substring(1))) {
                this.value = value.getString().substring(1);
                this.type = Type.LOCALIZED_KEY;
            } else {
                this.value = value.getString();
                this.type = Type.STRING;
            }
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
            this.value = new Range(from.doubleValue(), to.doubleValue(), lt.equals("exclusive"), rt.equals("exclusive"));
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
            if (value.getString().startsWith("#") && StringUtil.isUppercase(value.getString().substring(1))) {
                this.value = value.getString().substring(1);
                this.type = Type.LOCALIZED_KEY;
            } else {
                this.value = value.getString();
                this.type = Type.STRING;
            }
        } else if (value.getMap() != null) {
            this.value = parseMap(value.getMap(), this);
            this.type = Type.MAP;
        }
    }

    public static Object parseObservable(Observable observable) {
        INode node = NodeModelUtils.getNode(observable);
        String ret = NodeModelUtils.getTokenText(node).trim();
        // remove leading spaces and backquotes
        ret = ret.substring(1, ret.length() - 1).trim().replace("`", "");
        IObservableService service = Services.INSTANCE.getService(IObservableService.class);
        if (service != null) {
            return service.parseDeclaration(ret);
        }
        return ret;
    }

    public KActorsValue(Value value, KActorCodeStatement parent) {
        super(value, parent);
        this.deferred = value.isDeferred();
        if (value.getId() != null) {
            this.type = value.getId().contains(".") ? Type.URN : Type.IDENTIFIER;
            this.value = value.getId();
        } else if (value.getLiteral() != null) {
            this.value = parseLiteral(value.getLiteral());
        } else if (value.getExpression() != null) {
            this.type = Type.EXPRESSION;
            this.value = parseExpression(value.getExpression().substring(1, value.getExpression().length() - 1));
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
        } else if (value.isEmpty()) {
            this.type = Type.EMPTY;
            this.value = null;
        } else if (value.getConstructor() != null) {
            this.constructor = new Constructor();
            this.type = Type.OBJECT;
            this.constructor.setArguments(value.getConstructor().getParameters() == null
                    ? new KActorsArguments()
                    : new KActorsArguments(value.getConstructor().getParameters()));
            this.constructor.setClassname(value.getConstructor().getClassid());
            this.constructor.setClasspath(value.getConstructor().getPath());
        } else if (value.isComponent()) {
            this.constructor = new Constructor();
            this.type = Type.COMPONENT;
            this.constructor.setArguments(
                    value.getParameters() == null ? new KActorsArguments() : new KActorsArguments(value.getParameters()));
            this.constructor.setComponent(value.getBehavior());
        } else if (value.getMethodCalls() != null && value.getMethodCalls().size() > 0) {
            this.type = Type.CALLCHAIN;
            callChain = new ArrayList<>();
            for (MessageCall call : value.getMethodCalls()) {
                callChain.add(new KActorsActionCall(call, this));
            }
        }

        if (value.getThen() != null) {
            this.expressionType = ExpressionType.TERNARY_OPERATOR;
            this.trueCase = new KActorsValue(value.getThen(), parent);
        }
        if (value.getElse() != null) {
            this.expressionType = ExpressionType.TERNARY_OPERATOR;
            this.falseCase = new KActorsValue(value.getElse(), parent);
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
        
        if (value.getCast() != null) {
            switch (value.getCast()) {
            case "int":
                this.cast = DataType.INTEGER;
                break;
            case "number":
                this.cast = DataType.NUMBER;
                break;
            case "concept":
                this.cast = DataType.CONCEPT;
                break;
            case "boolean":
                this.cast = DataType.BOOLEAN;
                break;
            case "text":
                this.cast = DataType.TEXT;
                break;
            }
        }

    }

    @Override
    public List<Call> getCallChain() {
        return this.callChain;
    }

    private Object parseExpression(String string) {

        IExtensionService service = Services.INSTANCE.getService(IExtensionService.class);
        if (service != null) {
            return service.parse(string);
        }

        return string;
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
            this.value = parseExpression(match.getExpr().substring(1, match.getExpr().length() - 1));
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
        } else if (match.getAnnotation() != null) {
            this.type = Type.ANNOTATION;
            this.value = match.getAnnotation().substring(1);
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
                this.setTag(val.getTag().substring(1));
            }
        }
        return ret;
    }

    Object parseLiteral(Literal literal) {
        if (literal.getBoolean() != null) {
            this.type = Type.BOOLEAN;
            return Boolean.parseBoolean(literal.getBoolean());
        } else if (literal.getNumber() != null) {
            this.type = Type.NUMBER;
            return parseNumber(literal.getNumber());
        } else if (literal.getString() != null) {
            if (literal.getString().startsWith("#") && StringUtil.isUppercase(literal.getString().substring(1))) {
                this.type = Type.LOCALIZED_KEY;
                return literal.getString().substring(1);
            }
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
            return new KActorsDate(literal.getDate());
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
        if (value != null && cls.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Object evaluate(Scope scope, IIdentity identity, boolean forceEvaluationIfDeferred) {

        if (this.deferred && !forceEvaluationIfDeferred) {
            return this;
        }

        if (identity == null && scope != null) {
            identity = scope.getIdentity();
        }

        Object ret = this.value;
        IActorsService service = Services.INSTANCE.getService(IActorsService.class);
        if (service != null) {
            ret = service.evaluate(this, identity, scope);
        }

        return ret;
    }

    @Override
    public Object getStatedValue() {
        return this.value;
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

    @Override
    public KActorsValue getTrueCase() {
        return trueCase;
    }

    @Override
    public KActorsValue getFalseCase() {
        return falseCase;
    }
    
    @Override
    public DataType getCast() {
        return this.cast;
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
     * Return a map for the value containing at least an ID (unique for the same value), a label for
     * display, and the string value of the type. According to the type, this may be more or less
     * intelligent. So far unimplemented for collection types.
     * 
     * @return
     */
    public Map<String, String> asMap(Scope scope) {

        IConceptService cservice = Services.INSTANCE.getService(IConceptService.class);

        Map<String, String> ret = new HashMap<>();

        String id = value == null ? "null" : value.toString();
        if (type == Type.LOCALIZED_KEY) {
            id = scope.localize(id.startsWith("#") ? id : ("#" + id));
        }

        ret.put("id", id);
        ret.put("label", id);
        ret.put("type", type.name());

        switch(type) {
        case OBSERVABLE:
            Object o = getStatedValue();
            if (cservice != null && o instanceof ISemantic) {
                ret.put("label", cservice.getDisplayLabel(((ISemantic) o).getType()));
                break;
            }
        case ANYTHING:
        case ANYTRUE:
        case ANYVALUE:
        case BOOLEAN:
        case CLASS:
        case COMPONENT:
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
        case OBJECT:
            // TODO return a JSON map from the object
        case CONSTANT:
        case ANNOTATION:
        case EMPTY:
            ret.put("label", ret.get("id"));
            break;
        }

        /*
         * metadata may override anything
         */
        for (String key : metadata.keySet()) {
            ret.put(key,
                    metadata.get(key) instanceof KActorsValue
                            ? ((KActorsValue) metadata.get(key)).getStatedValue().toString()
                            : metadata.get(key).toString());
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

    public Constructor getConstructor() {
        return this.constructor;
    }

    @Override
    public String toString() {
        return "<" + type + " " + getStatedValue() + ">";
    }

    /**
     * Check for truth value. For now we consider true anything that is not null or not empty,
     * unless it's a boolean or number where we check the actual value for true value or != 0.
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

    @Override
    public ExpressionType getExpressionType() {
        return expressionType;
    }

    @Override
    public boolean isDeferred() {
        return deferred;
    }

    public void visit(Visitor visitor, IKActorsStatement kActorsActionCall, IKActorsAction action) {
        if (constructor != null) {
            constructor.getArguments().visit(action, kActorsActionCall, visitor);
        } else if (type == Type.TREE && value instanceof Graph) {
            for (KActorsValue o : ((Graph<KActorsValue, ?>) value).vertexSet()) {
                o.visit(visitor, kActorsActionCall, action);
            }
        } else if (type == Type.LIST && value instanceof List) {
            for (Object o : ((List<?>) value)) {
                if (o instanceof KActorsValue) {
                    ((KActorsValue) o).visit(visitor, kActorsActionCall, action);
                }
            }
        } else if (type == Type.MAP && value instanceof Map) {
            for (Object o : ((Map<?, ?>) value).values()) {
                if (o instanceof KActorsValue) {
                    ((KActorsValue) o).visit(visitor, kActorsActionCall, action);
                }
            }
        }
        visitor.visitValue(this, kActorsActionCall, action);
        visitMetadata(metadata, visitor);
    }

}
