package org.integratedmodelling.klab.kim;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.utils.Pair;

public class KimValidator implements Kim.Validator {

	Monitor monitor;
	Map<String, Integer> recheckObservationNS = new HashMap<>();

	public KimValidator(Monitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public List<Pair<String, Level>> validateFunction(IServiceCall functionCall, Set<IArtifact.Type> expectedType) {
		List<Pair<String, Level>> ret = new ArrayList<>();
		IPrototype prototype = Extensions.INSTANCE.getPrototype(functionCall.getName());
		if (prototype != null) {
			ret.addAll(prototype.validate(functionCall));
			if (expectedType != null) {

			}
		} else {
			ret.add(Pair.create("Function " + functionCall.getName() + " is unknown", Level.SEVERE));
		}
		return ret;
	}

	@Override
	public UrnDescriptor classifyUrn(String urn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumSet<Type> classifyCoreType(String string, EnumSet<Type> statedType) {

		IConcept coreType = Concepts.INSTANCE.getConcept(string);
		if (coreType == null) {
			return EnumSet.noneOf(Type.class);
		}
		/*
		 * TODO check type
		 */
		return statedType;
	}

	@Override
	public boolean isFunctionKnown(String functionName) {
		return Extensions.INSTANCE.getPrototype(functionName) != null;
	}

	@Override
	public boolean isAnnotationKnown(String annotationName) {
		return Annotations.INSTANCE.getPrototype(annotationName) != null;
	}

	@Override
	public List<Pair<String, Level>> validateAnnotation(IServiceCall annotationCall, IKimStatement target) {
		List<Pair<String, Level>> ret = new ArrayList<>();
		IPrototype prototype = Annotations.INSTANCE.getPrototype(annotationCall.getName());
		if (prototype != null) {
			return prototype.validate(annotationCall);
		}
		// Annotations w/o prototype are allowed
		return ret;

	}

	@Override
	public IPrototype getFunctionPrototype(String functionId) {
		return Extensions.INSTANCE.getPrototype(functionId);
	}

	@Override
	public IPrototype getAnnotationPrototype(String functionId) {
		return Annotations.INSTANCE.getPrototype(functionId);
	}

}
