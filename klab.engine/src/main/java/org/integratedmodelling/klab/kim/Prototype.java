package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * The k.LAB prototype specializes {@link org.integratedmodelling.klab.common.Prototype} in the k.IM
 * package by adding a constructor that reads from a KDL specification.
 * 
 * @author ferdinando.villa
 *
 */
public class Prototype extends org.integratedmodelling.klab.common.Prototype {

  /**
   * Create a prototype from an actuator, which is expected to be a valid parameter and not checked.
   * 
   * @param actuator
   * @param namespace
   */
  public Prototype(IKdlActuator actuator, String namespace) {

    this.name = (namespace == null ? "" : (namespace + ".")) + actuator.getName();
    this.type = IArtifact.Type.valueOf(actuator.getType().name());

    if (actuator.getDescription() != null) {
      this.description = StringUtils.pack(actuator.getDescription());
    }
    
    for (IKdlActuator arg : actuator.getActors()) {

      ArgumentImpl a = new ArgumentImpl();
      a.name = arg.getName();
      a.description =
          arg.getDescription() == null ? "" : StringUtils.pack(arg.getDescription()).trim();
      a.type = arg.getType() == null ? null : Type.valueOf(arg.getType().name());
      a.optional = arg.isOptional();
      a.enumValues.addAll(arg.getEnumValues());
      a.defaultValue = arg.getDefaultValue();

      arguments.put(a.name, a);
    }

    if (actuator.getJavaClass() != null) {
      try {
        implementation =
            Class.forName(actuator.getJavaClass(), true, actuator.getClass().getClassLoader());
      } catch (ClassNotFoundException e) {
        throw new KlabInternalErrorException(e);
      }
    }

    if (actuator.getGeometry() != null) {
      this.geometry = Geometry.create(actuator.getGeometry());
    } else {
      this.geometry = Geometry.empty();
    }
    
    // compute short arguments
    for (Argument argument : arguments.values()) {
    	// TODO
    }
  }

  @Override
  public String getSynopsis() {

    String s = getShortSynopsis();

    s += "\n\n" + StringUtils.leftIndent(StringUtils.justifyLeft(description, 70), 3) + "\n";

    if (arguments.size() > 0) {
      s += "Arguments:\n\n";
      for (Argument arg : arguments.values()) {
        s += "  " + arg.getName() + (arg.isOptional() ? " (optional)" : " (required)") + ": \n"
            + StringUtils.leftIndent(StringUtils.justifyLeft(arg.getDescription(), 60), 6) + "\n";
      }
      s += "\n";
    }

    return s;
  }

}
