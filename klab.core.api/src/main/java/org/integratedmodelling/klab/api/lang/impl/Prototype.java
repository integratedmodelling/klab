package org.integratedmodelling.klab.api.lang.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.documentation.KDocumentation;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.knowledge.KArtifact.Type;
import org.integratedmodelling.klab.api.lang.KPrototype;
import org.integratedmodelling.klab.api.lang.KServiceCall;
import org.integratedmodelling.klab.api.utils.Utils;

public class Prototype implements KPrototype {

	private static final long serialVersionUID = -9168391783660976848L;

    public static class ArgumentImpl implements KPrototype.Argument {

		public String name;
		public String shortName;
		public String description = "";
		public boolean option;
		public boolean optional;
		public boolean isFinal;
		public Type type;
		public boolean artifact;
		private boolean expression;
		private boolean parameter;
		// storing as string for serialization, use artifact type to return as POD
		public String defaultValue = null;
		public Set<String> enumValues = new HashSet<>();
		public String label = null;
		public String unit = null;
		public boolean isConst;
		
		public ArgumentImpl() {
		}

		public boolean isConst() {
			return isConst;
		}

		public void setConst(boolean isConst) {
			this.isConst = isConst;
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
		public String getDefaultValue() {
			return defaultValue;
		}

		@Override
		public String getShortName() {
			// if (shortName == null) {
			// shortName = computeShortName();
			// }
			return shortName;
		}

		public String computeShortName() {
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

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public void setEnumValues(Set<String> enumValues) {
			this.enumValues = enumValues;
		}

		@Override
		public boolean isFinal() {
			return isFinal;
		}

		@Override
		public boolean isArtifact() {
			return artifact;
		}

		public void setFinal(boolean isFinal) {
			this.isFinal = isFinal;
		}

		public void setArtifact(boolean artifact) {
			this.artifact = artifact;
		}

		@Override
		public String getLabel() {
			return label == null ? name : label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		@Override
		public boolean isParameter() {
			return parameter;
		}

		public void setParameter(boolean parameter) {
			this.parameter = parameter;
		}

		@Override
		public boolean isExpression() {
			return expression;
		}

		public void setExpression(boolean expression) {
			this.expression = expression;
		}

		@Override
		public String getUnit() {
			return this.unit;
		}

	}

	protected String name;
	// stable ordering reflecting that of the KDL arguments
	protected Map<String, ArgumentImpl> arguments = new LinkedHashMap<>();
	protected String description;
	protected Class<?> implementation;
	protected Type type;
	protected KGeometry geometry;
	protected boolean distributed;
	protected boolean contextualizer;
	protected boolean filter;
	protected String label = null;
	protected List<ArgumentImpl> exports = new ArrayList<>();
	protected List<ArgumentImpl> imports = new ArrayList<>();
	protected List<ArgumentImpl> inputAnnotations = new ArrayList<>();
	protected List<ArgumentImpl> outputAnnotations = new ArrayList<>();
	protected boolean isConst;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArguments(Map<String, ArgumentImpl> arguments) {
		this.arguments = arguments;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setGeometry(KGeometry geometry) {
		this.geometry = geometry;
	}

	public void setDistributed(boolean distributed) {
		this.distributed = distributed;
	}

	public void setContextualizer(boolean contextualizer) {
		this.contextualizer = contextualizer;
	}

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
	public List<KPrototype.Argument> listArguments() {
		return new ArrayList<>(arguments.values());
	}

	@Override
	public List<Pair<String, Level>> validate(KServiceCall function) {
		List<Pair<String, Level>> ret = new ArrayList<>();
		// validate existing arguments
		for (String arg : function.getParameters().keySet()) {
			ArgumentImpl argument = arguments.get(arg);
			if (argument == null) {
				ret.add(new Pair<>(name + ": argument " + arg + " is not recognized", Level.SEVERE));
			} else {
				Object val = function.getParameters().get(arg);
				if ((val = classify(val, argument)) == null) {
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
		// TODO does not check that invalid parameters are NOT passed. At the moment it
		// would break a lot of code so
		// do it when things are calm.
		return ret;
	}

	/*
	 * Validate the passed object as the type requested and return it (or its
	 * transformation if allowed) if valid; return null otherwise.
	 */
	private Object classify(Object val, ArgumentImpl argument) {
		if (val == null) {
			return true;
		}
		switch (argument.getType()) {
		case ANNOTATION:
			break;
		case BOOLEAN:
			if (!(val instanceof Boolean)) {
				return null;
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
				return null;
			}
			break;
		case LIST:
			if (!(val instanceof List)) {
				List<Object> ret = new ArrayList<>();
				ret.add(val);
				val = ret;
			}
			break;
		case NUMBER:
			if (!(val instanceof Number)) {
				return null;
			}
			break;
		case EXTENT:
		case SPATIALEXTENT:
		case TEMPORALEXTENT:
		case OBJECT:
			// TODO must be a map or table literal with proper type specs, or a symbol
			// defined as
			// such, if passed through k.IM.
			break;
		case PROCESS:
			break;
		case RANGE:
			break;
		case TEXT:
			if (!(val instanceof String)) {
				return null;
			}
			break;
		case VALUE:
			break;
		case VOID:
			// shouldn't happen
			break;
		default:
			break;

		}
		return val;
	}

	@Override
	public String getSynopsis(Integer... flags) {

		if (flags != null) {
			boolean tags = false;
			for (Integer flag : flags) {
				if (flag == KDocumentation.DOC_HTMLTAGS) {
					tags = true;
				}
			}

			String ret = Utils.Strings.justifyLeft(
			        Utils.Strings.pack(
							description == null || description.isEmpty() ? "No description provided." : description),
					80) + (tags ? "<p>" : "\n\n");
			if (tags) {
				ret += "<dl>";
			}
			for (String argument : arguments.keySet()) {
				Argument arg = arguments.get(argument);
				ret += "  " + (tags ? "<dt>" : "") + (arg.isOptional() ? "" : "* ") + argument + (tags ? "</dt>" : "")
						+ (tags ? "" : ":\n");
				String description = Utils.Strings.pack(
						arg.getDescription() == null || arg.getDescription().isEmpty() ? "No description provided."
								: arg.getDescription() + "\n");
				ret += tags ? ("<dd>" + description + "</dd>")
						: Utils.Strings.indent(Utils.Strings.justifyLeft(description, 50), 15);
				ret += (tags ? "" : "\n");
			}
			if (tags) {
				ret += "</dl>";
			}

			if (imports.size() > 0) {
				ret += "\n\n" + (tags ? "<p>" : "");
				ret += "Imports (match dependency names):" + (tags ? "</p>" : "") + "\n\n";
				if (tags) {
					ret += "<dl>";
				}
				for (Argument arg : imports) {
					ret += "  " + (tags ? "<dt>" : "") + (arg.isOptional() ? "" : "* ") + arg.getName()
							+ (tags ? "</dt>" : "") + (tags ? "" : ":\n");
					String description = Utils.Strings.pack(
							arg.getDescription() == null || arg.getDescription().isEmpty() ? "No description provided."
									: arg.getDescription());
					ret += tags ? ("<dd>" + description + "</dd>")
							: Utils.Strings.indent(Utils.Strings.justifyLeft(description, 50), 15);
					ret += (tags ? "" : "\n");
				}
				if (tags) {
					ret += "</dl>";
				}
			}

			if (exports.size() > 0) {
				ret += "\n\n" + (tags ? "<p>" : "");
				ret += "Exports (match output names):" + (tags ? "</p>" : "") + "\n\n";
				if (tags) {
					ret += "<dl>";
				}
				for (Argument arg : exports) {
					ret += "  " + (tags ? "<dt>" : "") + (arg.isOptional() ? "" : "* ") + arg.getName()
							+ (tags ? "</dt>" : "") + (tags ? "" : ":\n");
					String description = Utils.Strings.pack(
							arg.getDescription() == null || arg.getDescription().isEmpty() ? "No description provided."
									: arg.getDescription());
					ret += tags ? ("<dd>" + description + "</dd>")
							: Utils.Strings.indent(Utils.Strings.justifyLeft(description, 50), 15);
					ret += (tags ? "" : "\n");
				}
				if (tags) {
					ret += "</dl>";
				}
			}

			if (inputAnnotations.size() > 0) {
				ret += "\n\n" + (tags ? "<p>" : "");
				ret += "Annotation tags for inputs:" + (tags ? "</p>" : "") + "\n\n";
				if (tags) {
					ret += "<dl>";
				}
				for (Argument arg : outputAnnotations) {
					ret += "  " + (tags ? "<dt>" : "") + (arg.isOptional() ? "" : "* ") + arg.getName()
							+ (tags ? "</dt>" : "") + (tags ? "" : ":\n");
					String description = Utils.Strings.pack(
							arg.getDescription() == null || arg.getDescription().isEmpty() ? "No description provided."
									: arg.getDescription());
					ret += tags ? ("<dd>" + description + "</dd>")
							: Utils.Strings.indent(Utils.Strings.justifyLeft(description, 50), 15);
					ret += (tags ? "" : "\n");
				}
				if (tags) {
					ret += "</dl>";
				}
			}

			if (outputAnnotations.size() > 0) {
				ret += "\n\n" + (tags ? "<p>" : "");
				ret += "Annotation tags for outputs:" + (tags ? "</p>" : "") + "\n\n";
				if (tags) {
					ret += "<dl>";
				}
				for (Argument arg : outputAnnotations) {
					ret += "  " + (tags ? "<dt>" : "") + (arg.isOptional() ? "" : "* ") + arg.getName()
							+ (tags ? "</dt>" : "") + (tags ? "" : ":\n");
					String description = Utils.Strings.pack(
							arg.getDescription() == null || arg.getDescription().isEmpty() ? "No description provided."
									: arg.getDescription());
					ret += tags ? ("<dd>" + description + "</dd>")
							: Utils.Strings.indent(Utils.Strings.justifyLeft(description, 50), 15);
					ret += (tags ? "" : "\n");
				}
				if (tags) {
					ret += "</dl>";
				}
			}

			return ret;

		}
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
	public KGeometry getGeometry() {
		return geometry;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean isContextualizer() {
		return contextualizer;
	}

	@Override
	public List<Argument> listImports() {
		return new Utils.Casts<ArgumentImpl, Argument>().cast(imports);
	}

	@Override
	public List<Argument> listExports() {
		return new Utils.Casts<ArgumentImpl, Argument>().cast(exports);
	}

	public List<Argument> getImports() {
		return new Utils.Casts<ArgumentImpl, Argument>().cast(imports);
	}

	public List<Argument> getExports() {
		return new Utils.Casts<ArgumentImpl, Argument>().cast(exports);
	}

	public void setImports(List<ArgumentImpl> arguments) {
		this.imports = arguments;
	}

	public void setExports(List<ArgumentImpl> arguments) {
		this.exports = arguments;
	}

	@Override
	public Collection<Argument> listImportAnnotations() {
		return new Utils.Casts<ArgumentImpl, Argument>().cast(inputAnnotations);
	}

	@Override
	public boolean isFilter() {
		return filter;
	}

	@Override
	public Collection<Argument> listExportAnnotations() {
		return new Utils.Casts<ArgumentImpl, Argument>().cast(outputAnnotations);
	}
	
	@Override
	public boolean isFinal() {
		return isConst;
	}

}
