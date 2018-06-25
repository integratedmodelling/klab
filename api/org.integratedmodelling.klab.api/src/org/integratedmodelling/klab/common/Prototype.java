package org.integratedmodelling.klab.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.utils.Pair;

public class Prototype implements IPrototype {

	public static class ArgumentImpl implements IPrototype.Argument {

		public String name;
		public String shortName;
		public String description = "";
		public boolean option;
		public boolean optional;
		public Type type;
		public Object defaultValue = null;
		public Set<String> enumValues = new HashSet<>();

		public ArgumentImpl() {
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public boolean isOption() {
			return option;
		}

		@Override
		public boolean isOptional() {
			return optional;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public Set<String> getEnumValues() {
			return enumValues;
		}

		@Override
		public Object getDefaultValue() {
			return defaultValue;
		}

		@Override
		public String getShortName() {
			if (shortName == null) {
				shortName = computeShortName();
			}
			return shortName;
		}

		private String computeShortName() {
			// TODO Auto-generated method stub
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setOption(boolean option) {
			this.option = option;
		}

		public void setOptional(boolean optional) {
			this.optional = optional;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public void setDefaultValue(Object defaultValue) {
			this.defaultValue = defaultValue;
		}

		public void setEnumValues(Set<String> enumValues) {
			this.enumValues = enumValues;
		}
	}

	protected String name;
	protected Map<String, ArgumentImpl> arguments = new HashMap<>();
	protected String description;
	protected Class<?> implementation;
	protected Type type;
	protected Geometry geometry;
	protected boolean distributed;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Argument getArgument(String argumentId) {
		return arguments.get(argumentId);
	}

	public Map<String, ArgumentImpl> getArguments() {
		return arguments;
	}

	@Override
	public List<IPrototype.Argument> listArguments() {
		return new ArrayList<>(arguments.values());
	}

	@Override
	public List<Pair<String, Level>> validate(IServiceCall function) {
		List<Pair<String, Level>> ret = new ArrayList<>();
		// validate existing arguments
		for (String arg : function.getParameters().keySet()) {
			ArgumentImpl argument = arguments.get(arg);
			if (argument == null) {
				ret.add(new Pair<>(name + ": argument " + arg + " is not recognized", Level.SEVERE));
			} else {
				Object val = function.getParameters().get(arg);
				if (!classify(val, argument)) {
					ret.add(new Pair<>(name + ": argument " + arg + " is of incompatible type: "
							+ (argument.getType() == Type.ENUM
									? ("one of " + Arrays.toString(
											argument.enumValues.toArray(new String[argument.enumValues.size()])))
									: argument.getType().name().toLowerCase())
							+ " expected", Level.SEVERE));
				}
			}
		}
		// ensure that all mandatory args are there
		for (ArgumentImpl arg : arguments.values()) {
			if (!arg.isOptional() && !function.getParameters().containsKey(arg.name)) {
				ret.add(new Pair<>(name + ": mandatory argument " + arg.name + " was not passed", Level.SEVERE));
			}
		}
		return ret;
	}

	private boolean classify(Object val, ArgumentImpl argument) {
		if (val == null) {
			return true;
		}
		switch (argument.getType()) {
		case ANNOTATION:
			break;
		case BOOLEAN:
			if (!(val instanceof Boolean)) {
				return false;
			}
			break;
		case CONCEPT:
			// IConceptDescriptor cd = Kim.INSTANCE.getConceptDescriptor(val.toString());
			// if (cd == null) {
			// return false;
			// }
			break;
		case ENUM:
			if (argument.enumValues == null || !argument.enumValues.contains(val.toString())) {
				return false;
			}
			break;
		case LIST:
			if (!(val instanceof List)) {
				return false;
			}
			break;
		case NUMBER:
			if (!(val instanceof Number)) {
				return false;
			}
			break;
		case EXTENT:
		case SPATIALEXTENT:
		case TEMPORALEXTENT:
		case OBJECT:
			// TODO must be a map or table literal with proper type specs, or a symbol defined as
			// such, if passed through k.IM.
			break;
		case PROCESS:
			break;
		case RANGE:
			break;
		case TEXT:
			if (!(val instanceof String)) {
				return false;
			}
			break;
		case VALUE:
			break;
		case TABLE:
			if (!(val instanceof Map || val instanceof ITable)) {
				return false;
			}
			break;
		case VOID:
			// shoulnd't happen
			break;
		default:
			break;

		}
		return true;
	}

	@Override
	public String getSynopsis() {
		return getShortSynopsis();
	}

	@Override
	public String getShortSynopsis() {

		String ret = getName();

		for (ArgumentImpl arg : arguments.values()) {
			if (arg.isOptional()) {
				ret += " [" + arg.getName() + "=" + arg.type + printEnumValues(arg) + "]";
			} else {
				ret += " " + arg.name + "=" + arg.type + printEnumValues(arg);
			}
		}

		return ret;
	}

	private String printEnumValues(ArgumentImpl arg) {
		String ret = "";
		if (arg.type == Type.ENUM) {
			ret += "(";
			for (String s : arg.enumValues) {
				ret += (ret.length() == 1 ? "" : ",") + s;
			}
			ret += ")";
		}
		return ret;
	}

	@Override
	public Class<?> getExecutorClass() {
		return implementation;
	}

	@Override
	public boolean isDistributed() {
		return distributed;
	}

	@Override
	public Collection<String> getExtentParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Geometry getGeometry() {
		return geometry;
	}

}
