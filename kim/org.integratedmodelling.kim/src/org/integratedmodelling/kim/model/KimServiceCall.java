package org.integratedmodelling.kim.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.kim.Function;
import org.integratedmodelling.kim.kim.KeyValuePair;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;

public class KimServiceCall extends KimStatement implements IServiceCall {

	private static final long serialVersionUID = 8447771460330621498L;

	protected String name;
	protected Parameters parameters = new Parameters();

	protected KimServiceCall(EObject object) {
		super(object);
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

	public KimServiceCall(Function statement) {
		super(statement);
		if (statement != null) {
			KimNamespace namespace = Kim.INSTANCE.getNamespace(statement, false);
			this.name = statement.getName();
			if (statement.getParameters() != null) {
				if (statement.getParameters().getSingleValue() != null) {
					this.parameters.put(DEFAULT_PARAMETER_NAME,
							Kim.INSTANCE.parseValue(statement.getParameters().getSingleValue(), namespace));
				} else if (statement.getParameters().getPairs() != null) {
					for (KeyValuePair kv : statement.getParameters().getPairs()) {
						this.parameters.put(kv.getName(), Kim.INSTANCE.parseValue(kv.getValue(), namespace));
					}
				}
			}
		}
	}

	public List<KimNotification> validateUsage(Set<IPrototype.Type> expectedType) {
		return Kim.INSTANCE.validateFunctionCall(this, expectedType);
	}

	public KimServiceCall(EObject statement, String name, Map<String, Object> parameters) {
		super(statement);
		this.name = name;
		this.parameters.putAll(parameters);
	}

	public KimServiceCall(String name, Object[] parameters) {
		super((EObject) null);
		this.name = name;
		for (int i = 0; i < parameters.length; i++) {
			String key = parameters[i].toString();
			Object val = parameters[++i];
			this.parameters.put(key, val);
		}
	}

	@Override
	public String getSourceCode() {

		if (this.sourceCode == null || this.sourceCode.trim().isEmpty()) {
			String ret = name + "(";
			int i = 0;
			for (String key : parameters.keySet()) {
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
		}
		return val.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Parameters getParameters() {
		return parameters;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

}
