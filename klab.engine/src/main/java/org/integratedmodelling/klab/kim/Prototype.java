package org.integratedmodelling.klab.kim;

import java.util.Collection;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlAnnotation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * The k.LAB prototype specializes
 * {@link org.integratedmodelling.klab.common.Prototype} in the k.IM package by
 * adding a constructor that reads from a KDL specification.
 * 
 * Labels of internal parameters become short names.
 * 
 * @author ferdinando.villa
 *
 */
public class Prototype extends org.integratedmodelling.klab.common.Prototype {

	/**
	 * Create a prototype from an actuator, which is expected to be a valid
	 * parameter and not checked.
	 * 
	 * @param actuator
	 * @param namespace
	 */
	public Prototype(IKdlActuator actuator, String namespace) {

		this.name = (namespace == null ? "" : (namespace + ".")) + actuator.getName();
		this.type = IArtifact.Type.valueOf(actuator.getType().name());
		this.contextualizer = actuator.isExported();
		this.filter = actuator.isFilter();

		if (actuator.getDescription() != null) {
			this.description = StringUtil.pack(actuator.getDescription());
		}

		this.label = actuator.getLabel();

		/*
		 * The ordering of the options is the same as the KDL
		 */
		for (IKdlActuator arg : actuator.getActors()) {

			ArgumentImpl a = new ArgumentImpl();
			a.name = arg.getName();
			a.description = arg.getDescription() == null ? "" : StringUtil.pack(arg.getDescription()).trim();
			a.type = arg.getType() == null ? null : Type.valueOf(arg.getType().name());
			a.optional = arg.isOptional();
			a.shortName = arg.getLabel();
			a.isFinal = arg.isFinal();
			a.setParameter(arg.isParameter());
			a.enumValues.addAll(arg.getEnumValues());
			a.defaultValue = arg.getDefaultValue() == null ? null : arg.getDefaultValue().toString();
			a.artifact = arg.isImported();
			a.unit = arg.getUnit();
			a.setExpression(arg.isExpression());
			
			if (arg.getLabel() != null) {
				a.label = arg.getLabel();
			} else {
				a.label = StringUtil.capitalize(a.name).replaceAll("_", " ").replaceAll("\\-", " ");
			}

			if (arg.isImported()) {
				this.imports.add(a);
			} else if (arg.isExported()) {
				this.exports.add(a);
			} else {
				arguments.put(a.name, a);
			}
		}

		/*
		 * annotations may contain @taginput to specify runtime input tags
		 */
		for (IKdlAnnotation annotation : actuator.getAnnotations()) {
			if ("taginput".equals(annotation.getName())) {
				Object value = annotation.get(IKdlAnnotation.DEFAULT_PARAMETER_NAME);
				if (value instanceof Collection) {
					for (Object v : ((Collection<?>)value)) {
						this.inputTags.add(v.toString());
					}
				} else {
					this.inputTags.add(value.toString());
				}
			}
		}
		
		if (actuator.getJavaClass() != null) {
			try {
				implementation = Class.forName(actuator.getJavaClass(), true, actuator.getClass().getClassLoader());
			} catch (ClassNotFoundException e) {
				throw new KlabInternalErrorException(e);
			}
		}

		if (actuator.getGeometry() != null) {
			this.geometry = Geometry.create(actuator.getGeometry());
		} else {
			this.geometry = Geometry.empty();
		}
	}

}
