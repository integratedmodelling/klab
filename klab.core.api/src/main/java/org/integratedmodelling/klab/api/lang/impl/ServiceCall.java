package org.integratedmodelling.klab.api.lang.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.collections.impl.Parameters;
import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.data.mediation.KCurrency;
import org.integratedmodelling.klab.api.data.mediation.KUnit;
import org.integratedmodelling.klab.api.data.mediation.classification.KClassification;
import org.integratedmodelling.klab.api.data.mediation.classification.KClassifier;
import org.integratedmodelling.klab.api.data.mediation.classification.KLookupTable;
import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.lang.KContextualizable;
import org.integratedmodelling.klab.api.lang.KPrototype;
import org.integratedmodelling.klab.api.lang.KServiceCall;
import org.integratedmodelling.klab.api.lang.kim.KKimExpression;
import org.integratedmodelling.klab.api.lang.kim.impl.KimStatement;
import org.integratedmodelling.klab.api.services.runtime.KNotification;
import org.integratedmodelling.klab.api.utils.Utils;

public class ServiceCall extends KimStatement implements KServiceCall {

    private static final long serialVersionUID = 8447771460330621498L;

    protected String name;
    protected Parameters<String> parameters = new Parameters<>();
    protected Set<String> interactiveParameterIds = new HashSet<>();

    /**
     * Create a function call from the passed parameters. All parameters after the name must be
     * given in pairs: (string, value)*
     * 
     * @param name
     * @param parameters
     * @return a new service call
     */
    public static KServiceCall create(String name, Object... parameters) {
        return new ServiceCall(name, parameters);
    }

//    public KimServiceCall(Function statement, IKimStatement parent) {
//        super(statement, parent);
//        if (statement != null) {
//            // if we don't check, the call causes a stack overflow in functions that are argument of
//            // a 'covering' clause in a namespace definition.
//            KimNamespace namespace = parent instanceof KimNamespace
//                    ? (KimNamespace) parent
//                    : Kim.INSTANCE.getNamespace(statement);
//            this.name = statement.getName();
//            if (statement.getParameters() != null) {
//                if (statement.getParameters().getSingleValue() != null
//                        && statement.getParameters().getSingleValue().size() > 0) {
//                    List<Object> objects = new ArrayList<>();
//                    for (Value value : statement.getParameters().getSingleValue()) {
//                        objects.add(Kim.INSTANCE.parseValue(value, namespace));
//                    }
//                    this.parameters.put(DEFAULT_PARAMETER_NAME,
//                            objects.size() == 1 ? objects.get(0) : objects);
//                } else if (statement.getParameters().getPairs() != null) {
//                    for (KeyValuePair kv : statement.getParameters().getPairs()) {
//                        this.parameters.put(kv.getName(), Kim.INSTANCE.parseValue(kv.getValue(), namespace));
//                        if (kv.isInteractive()) {
//                            this.interactiveParameterIds.add(kv.getName());
//                        }
//                    }
//                }
//            }
//        }
//    }

    public List<KNotification> validateUsage(Set<KArtifact.Type> expectedType) {
        return null;// Kim.INSTANCE.validateFunctionCall(this, expectedType);
    }

//    public KimServiceCall(EObject statement, String name, Map<String, Object> parameters,
//            IKimStatement parent) {
//        super(statement, parent);
//        this.name = name;
//        this.parameters.putAll(parameters);
//    }

    @SuppressWarnings("unchecked")
    public ServiceCall(String name, Object[] parameters) {
//        super((EObject) null, null);
        this.name = name;
        if (parameters != null && parameters.length == 1 && parameters[0] instanceof KParameters) {
            this.parameters.putAll((KParameters<String>) (parameters[0]));
        } else if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                String key = parameters[i].toString();
                Object val = parameters[++i];
                this.parameters.put(key, val);
            }
        }
    }

    public ServiceCall(String name, Map<String, Object> parameters) {
//        super((EObject) null, null);
        this.name = name;
        this.parameters.putAll(parameters);
    }

    @Override
    public String getSourceCode() {

        if (super.getSourceCode() == null || super.getSourceCode().trim().isEmpty()) {
            String ret = name + "(";
            int i = 0;
            for (String key : parameters.keySet()) {

                // internal parameters
                if (key.startsWith("__")) {
                    continue;
                }
                ret += (i == 0 ? "" : ", ") + key + " = ";
                Object val = parameters.get(key);
                ret += val instanceof String
                        ? ("\"" + Utils.Escape.forDoubleQuotedString((String) val, false) + "\"")
                        : (val == null ? "unknown" : getStringValue(val));
                i++;
            }
            ret += ")";
            return ret;
        }

        return super.getSourceCode();
    }

    @Override
    public void visit(Visitor visitor) {
        // TODO must visit concept declarations in maps
    }

    @Override
    public String toString() {
        return getSourceCode();
    }

    private String getStringValue(Object val) {

        if (val instanceof List) {
            String ret = "(";
            for (Object o : ((List<?>) val)) {
                ret += (ret.length() == 1 ? "" : " ") + getStringValue(o);
            }
            return ret + ")";
        } else if (val instanceof Map) {
            String ret = "{";
            for (Object o : ((Map<?, ?>) val).keySet()) {
                ret += (ret.length() == 1 ? "" : " ") + o + " " + getStringValue(((Map<?, ?>) val).get(o));
            }
            return ret + "}";
        } else if (val instanceof Range) {
            return ((Range) val).getLowerBound() + " to " + ((Range) val).getUpperBound();
        } else if (val instanceof KClassification) {
            String ret = "";
            for (Pair<KConcept, KClassifier> o : ((KClassification) val)) {
                ret += (ret.isEmpty() ? "" : ", ") + o.getSecond().getSourceCode() + " : '" + o.getFirst()
                        + "'";
            }
            return "{" + ret + "}";
        } else if (val instanceof KLookupTable) {
            String ret = "";
            // TODO table literal
            // TODO must also pass argument list to the same function...
            return "{{" + ret + "}}";
        } else if (val instanceof KKimExpression) {
            return "[" + ((KKimExpression) val).getCode() + "]";
        } else if (val instanceof ServiceCall) {
            return ((ServiceCall) val).getSourceCode();
        } else if (val instanceof KContextualizable) {
            return ((KContextualizable) val).getSourceCode();
        } else if (val instanceof KUnit || val instanceof KCurrency) {
            return "\"" + val + "\"";
        }
        return val.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Parameters<String> getParameters() {
        return parameters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParameters(Parameters<String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Collection<String> getInteractiveParameters() {
        return interactiveParameterIds;
    }

    public ServiceCall copy() {
        return (ServiceCall) create(this.name, this.parameters);
    }

    @Override
    public int getParameterCount() {
        int n = 0;
        if (parameters.size() > 0) {
            for (String s : parameters.keySet()) {
                if (!s.startsWith("_")) {
                    n++;
                }
            }
        }
        return n;
    }

    @Override
    public KPrototype getPrototype() {
//        IExtensionService exts = Services.INSTANCE.getService(IExtensionService.class);
        return null; // exts == null ? null : exts.getPrototype(name);
    }

}
