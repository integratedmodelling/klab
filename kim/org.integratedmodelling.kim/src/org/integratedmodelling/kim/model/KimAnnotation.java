package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.Annotation;
import org.integratedmodelling.kim.kim.KeyValuePair;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.validation.KimNotification;

public class KimAnnotation extends KimServiceCall implements IKimAnnotation {

	private static final long serialVersionUID = 5762837796346002863L;

	public KimAnnotation(Annotation statement, IKimNamespace namespace, IKimStatement parent) {
        super(statement, parent);
        this.name = statement.getName().substring(1);
        if (statement.getParameters() != null) {
            if (statement.getParameters().getSingleValue() != null && statement.getParameters().getSingleValue().size() > 0) {
            	List<Object> objects = new ArrayList<>();
            	for (Value value : statement.getParameters().getSingleValue()) {
            		objects.add(Kim.INSTANCE.parseValue(value, namespace));
            	}
                this.parameters.put(DEFAULT_PARAMETER_NAME, objects.size() == 1 ? objects.get(0) : objects);
            } 
            if (statement.getParameters().getPairs() != null) {
                for (KeyValuePair kv : statement.getParameters().getPairs()) {
                    this.parameters.put(kv.getName(), Kim.INSTANCE.parseValue(kv.getValue(), namespace));
                    if (kv.isInteractive()) {
                        this.interactiveParameterIds.add(kv.getName());
                    }
                }
            }
        }
    }

	public List<KimNotification> validateUsage(IKimStatement statement) {
		return Kim.INSTANCE.validateAnnotation(this, statement);
	}

}
