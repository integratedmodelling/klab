package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.kim.Function;
import org.integratedmodelling.kim.kim.KeyValuePair;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.services.IExtensionService;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;

public class KimServiceCall extends KimStatement implements IServiceCall {

	private static final long serialVersionUID = 8447771460330621498L;

	protected String name;
	protected Parameters<String> parameters = new Parameters<>();
	protected Set<String> interactiveParameterIds = new HashSet<>();

	protected KimServiceCall(EObject object, IKimStatement parent) {
		super(object, parent);
	}

	/**
	 * Create a function call from the passed parameters. All parameters after the
	 * name must be given in pairs: (string, value)*
	 * 
	 * @param name
	 * @param parameters
	 * @return a new service call
	 */
	public static IServiceCall create(String name, Object... parameters) {
		return new KimServiceCall(name, parameters);
	}

	public KimServiceCall(Function statement, IKimStatement parent) {
		super(statement, parent);
		if (statement != null) {
			KimNamespace namespace = Kim.INSTANCE.getNamespace(statement);
			this.name = statement.getName();
			if (statement.getParameters() != null) {
				if (statement.getParameters().getSingleValue() != null
						&& statement.getParameters().getSingleValue().size() > 0) {
					List<Object> objects = new ArrayList<>();
					for (Value value : statement.getParameters().getSingleValue()) {
						objects.add(Kim.INSTANCE.parseValue(value, namespace));
					}
					this.parameters.put(DEFAULT_PARAMETER_NAME, objects.size() == 1 ? objects.get(0) : objects);
				} else if (statement.getParameters().getPairs() != null) {
					for (KeyValuePair kv : statement.getParameters().getPairs()) {
						this.parameters.put(kv.getName(), Kim.INSTANCE.parseValue(kv.getValue(), namespace));
						if (kv.isInteractive()) {
							this.interactiveParameterIds.add(kv.getName());
						}
					}
				}
			}
		}
	}

	public List<KimNotification> validateUsage(Set<IArtifact.Type> expectedType) {
		return Kim.INSTANCE.validateFunctionCall(this, expectedType);
	}

	public KimServiceCall(EObject statement, String name, Map<String, Object> parameters, IKimStatement parent) {
		super(statement, parent);
		this.name = name;
		this.parameters.putAll(parameters);
	}

	@SuppressWarnings("unchecked")
	public KimServiceCall(String name, Object[] parameters) {
		super((EObject) null, null);
		this.name = name;
		if (parameters != null && parameters.length == 1 && parameters[0] instanceof IParameters) {
			this.parameters.putAll((IParameters<String>) (parameters[0]));
		} else if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				String key = parameters[i].toString();
				Object val = parameters[++i];
				this.parameters.put(key, val);
			}
		}
	}

	public KimServiceCall(String name, Map<String, Object> parameters) {
		super((EObject) null, null);
		this.name = name;
		this.parameters.putAll(parameters);
	}

	@Override
	public String getSourceCode() {

		if (this.sourceCode == null || this.sourceCode.trim().isEmpty()) {
			String ret = name + "(";
			int i = 0;
			for (String key : parameters.keySet()) {

				// internal parameters
				if (key.startsWith("__")) {
					continue;
				}
				ret += (i == 0 ? "" : ", ") + key + " = ";
				Object val = parameters.get(key);
				ret += val instanceof String ? ("\"" + Escape.forDoubleQuotedString((String) val, false) + "\"")
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
		} else if (val instanceof IClassification) {
			String ret = "";
			for (Pair<IConcept, IClassifier> o : ((IClassification) val)) {
				ret += (ret.isEmpty() ? "" : ", ") + o.getSecond().getSourceCode() + " : '" + o.getFirst() + "'";
			}
			return "{" + ret + "}";
		} else if (val instanceof ILookupTable) {
			String ret = "";
			// TODO table literal
			// TODO must also pass argument list to the same function...
			return "{{" + ret + "}}";
		} else if (val instanceof IKimExpression) {
			return "[" + ((IKimExpression)val).getCode() + "]";
		} else if (val instanceof KimServiceCall) {
			return ((KimServiceCall) val).getSourceCode();
		} else if (val instanceof ComputableResource) {
			return ((ComputableResource) val).getSourceCode();
		} else if (val instanceof IUnit || val instanceof ICurrency) {
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

	public KimServiceCall copy() {
		return (KimServiceCall) create(this.name, this.parameters);
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
	public IPrototype getPrototype() {
		IExtensionService exts = Services.INSTANCE.getService(IExtensionService.class);
		return exts == null ? null : exts.getPrototype(name);
	}

}
