package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kactors.kactors.Annotation;
import org.integratedmodelling.kactors.kactors.KeyValuePair;
import org.integratedmodelling.kactors.kactors.Value;
import org.integratedmodelling.kdl.api.IKdlAnnotation;
import org.integratedmodelling.klab.utils.Parameters;

public class KActorsAnnotation extends Parameters<String> implements IKdlAnnotation {

	private String name;
	private Set<String> interactiveParameterIds = new HashSet<>();
	
	public KActorsAnnotation(Annotation statement) {

		this.name = statement.getName().substring(1);

		if (statement.getParameters() != null) {
			if (statement.getParameters().getPairs() != null) {
//				if (statement.getParameters().getValues().size() == 1) {
//					this.put(DEFAULT_PARAMETER_NAME, KActors.INSTANCE.parseValue(statement.getParameters().getValues().get(0)));
//				} else {
//					List<Object> values = new ArrayList<>();
////					for (Value value : statement.getParameters().getValues()) {
////						values.add(Kdl.INSTANCE.parseValue(value));
//					}
//					this.put(DEFAULT_PARAMETER_NAME, values);
//				}
//			} else if (statement.getParameters().getPairs() != null) {
//				for (KeyValuePair kv : statement.getParameters().getPairs()) {
//					this.put(kv.getName(), Kdl.INSTANCE.parseValue(kv.getValue()));
//					if (kv.isInteractive()) {
//						this.interactiveParameterIds.add(kv.getName());
//					}
//				}
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}
}
