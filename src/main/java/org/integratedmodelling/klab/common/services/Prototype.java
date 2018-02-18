package org.integratedmodelling.klab.common.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.data.Geometry;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.StringUtils;

public class Prototype implements IPrototype {

    class Argument implements IPrototype.Argument {

        String      name;
        String      description  = "";
        boolean     option;
        boolean     optional;
        Type        type;
        Object      defaultValue = null;
        Set<String> enumValues   = new HashSet<>();

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

    }

    private String                name;
    private Map<String, Argument> arguments = new HashMap<>();
    private String                description;
    private Class<?>              implementation;
    private Type                  type;
    private IGeometry             geometry;

    /**
     * Create a prototype from an actuator, which is expected to be a valid parameter and not checked.
     * 
     * @param actuator
     * @param namespace 
     */
    public Prototype(IKdlActuator actuator, String namespace) {

        this.name = (namespace == null ? "" : (namespace + ".")) + actuator.getName();
        this.type = Type.valueOf(actuator.getType().name());

        if (actuator.getDescription() != null) {
            this.description = StringUtils.pack(actuator.getDescription());
        }
        for (IKdlActuator arg : actuator.getParameters()) {

            Argument a = new Argument();
            a.name = arg.getName();
            a.description = arg.getDescription() == null ? "" : StringUtils.pack(arg.getDescription()).trim();
            a.type = arg.getType() == null ? null : Type.valueOf(arg.getType().name());
            a.optional = arg.isOptional();
            a.enumValues.addAll(arg.getEnumValues());
            a.defaultValue = arg.getDefaultValue();

            arguments.put(a.name, a);
        }

        if (actuator.getJavaClass() != null) {
            try {
                implementation = Class
                        .forName(actuator.getJavaClass(), true, actuator.getClass().getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new KlabRuntimeException(e);
            }
        }
        
        if (actuator.getGeometry() != null) {
            this.geometry = Geometry.create(actuator.getGeometry());
        } else {
            this.geometry = Geometry.empty();
        }
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

    @Override
    public List<IPrototype.Argument> getArguments() {
        return new ArrayList<>(arguments.values());
    }

    @Override
    public void validate(IKimFunctionCall function) throws KlabValidationException {
        // TODO Auto-generated method stub

    }

    @Override
    public String getSynopsis() {

        String s = getShortSynopsis();

        s += "\n\n" + StringUtils.leftIndent(StringUtils.justifyLeft(description, 70), 3) + "\n";

        if (arguments.size() > 0) {
            s += "Arguments:\n\n";
            for (Argument arg : arguments.values()) {
                s += "  " + arg.getName() + (arg.isOptional() ? " (optional)" : " (required)") + ": \n"
                        + StringUtils.leftIndent(StringUtils.justifyLeft(arg.getDescription(), 60), 6)
                        + "\n";
            }
            s += "\n";
        }

        return s;
    }

    @Override
    public String getShortSynopsis() {

        String ret = getName();

        for (Argument arg : arguments.values()) {
            if (arg.isOptional()) {
                ret += " [" + arg.getName() + "=" + arg.type + printEnumValues(arg) + "]";
            } else {
                ret += " " + arg.name + "=" + arg.type + printEnumValues(arg);
            }
        }

        return ret;
    }

    private String printEnumValues(Argument arg) {
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<String> getExtentParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGeometry getGeometry() {
        return geometry;
    }

}
