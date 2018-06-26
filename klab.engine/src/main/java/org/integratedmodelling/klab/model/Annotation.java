package org.integratedmodelling.klab.model;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.utils.Parameters;

public class Annotation extends Parameters implements IAnnotation {

	String name;

	public Annotation(IKimAnnotation statement) {
		this.name = statement.getName();
		for (String key : statement.getParameters().keySet()) {
			Object value = statement.getParameters().get(key);
			if (value instanceof IKimConcept) {
				value = Concepts.INSTANCE.declare((IKimConcept) value);
			}

			// TODO table

			this.put(key, value);
		}
	}

	@Override
	public String getName() {
		return name;
	}

}
