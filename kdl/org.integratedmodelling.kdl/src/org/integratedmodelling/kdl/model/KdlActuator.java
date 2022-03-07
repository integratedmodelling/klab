package org.integratedmodelling.kdl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlAnnotation;
import org.integratedmodelling.kdl.api.IKdlComputation;
import org.integratedmodelling.kdl.api.IKdlContextualizer;
import org.integratedmodelling.kdl.kdl.ActorDefinition;
import org.integratedmodelling.kdl.kdl.Annotation;
import org.integratedmodelling.kdl.kdl.Function;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

public class KdlActuator extends KdlStatement implements IKdlActuator {

	private static final long serialVersionUID = -5395749953918710110L;

	List<IKdlActuator> inputs = new ArrayList<>();
	List<IKdlActuator> outputs = new ArrayList<>();
	List<IKdlActuator> parameters = new ArrayList<>();
	List<IKdlActuator> actors = new ArrayList<>();
	List<IKdlComputation> computations = new ArrayList<>();
	List<IKdlAnnotation> annotations = new ArrayList<>();

	String name;
	String alias;
	String description = "";
	String newObservationType;
	String newObservationUrn;
	String geometry;
	String semantics;
	String javaClass;
	String label;
	Type type;
	String unit;

	boolean exported = false;
	boolean imported = false;
	boolean optional = false;
	boolean parameter = false;
	boolean isFinal = false;
	boolean isAbstract = false;
	boolean isFilter = false;
	boolean isExpression = false;
	boolean isTaggingAnnotation = false;
	boolean multipleInstances;
	boolean moreInstancesAllowed;
	int instanceCount;

	Object defaultValue = null;
	Set<String> enumValues = new HashSet<>();
	Set<Target> targets = new HashSet<>();
	List<IKdlContextualizer> coverage = new ArrayList<>();

	private boolean isResolution;

	public KdlActuator(EObject o) {
		super(o);
	}

	public KdlActuator(ActorDefinition o, Map<String, KdlActuator> previousActuators) {

		super(o);

		for (Annotation annotation : o.getAnnotations()) {
			this.annotations.add(new KdlAnnotation(annotation));
		}

		if (o.getAnnotationTag() != null) {
			this.name = o.getAnnotationTag().substring(1);
			this.isTaggingAnnotation = true;
		} else {
			this.name = o.getName();
		}
		
		this.alias = o.getLocalName();
		this.exported = o.isExported();
		this.imported = o.isImported();
		this.optional = o.isOptional();
		this.parameter = o.isParameter();
		this.isFinal = o.isFinal();
		this.isAbstract = o.isAbstract();
		this.label = o.getLabel();
		this.isExpression = o.isExpression();
		this.isFilter = o.isFilter();
		this.setResolution(o.getType().equals("resolve"));

		if (o.getUnit() != null) {
			ICompositeNode node = NodeModelUtils.getNode(o.getUnit());
			this.unit = node.getText().trim();
		}

		for (String s : o.getEnumValues()) {
			this.enumValues.add(s);
		}

		if (o.getType() != null) {
			this.type = o.getType().equals("resolve") ? Type.VOID : Type.valueOf(o.getType().toUpperCase());
		}

		if (o.getDocstring() != null) {
			this.description = o.getDocstring();
		}

		if (o.getDefault() != null) {
			this.defaultValue = Kdl.INSTANCE.parseValue(o.getDefault());
			if (this.type == null /* which it should be */) {
				if (this.enumValues.size() > 0) {
					this.type = Type.ENUM;
				} else {
					this.type = Type.classify(this.defaultValue);
				}
			}
		}

		// instance count
		this.instanceCount = o.getArity();
		if (this.instanceCount > 0) {
			this.multipleInstances = true;
			this.moreInstancesAllowed = o.isMinimum();
		} else if (o.isMultiple()) {
			this.multipleInstances = true;
			this.instanceCount = 1;
		} else if (!this.optional) {
			this.instanceCount = 1;
		}

		if (o.getExtended() != null) {
			KdlActuator extended = previousActuators.get(o.getExtended());
			if (extended != null) {
				for (IKdlActuator actor : extended.getActors()) {
					this.actors.add(actor);
				}
				for (IKdlActuator actor : extended.getInputs()) {
					this.inputs.add(actor);
				}
				for (IKdlActuator actor : extended.getOutputs()) {
					this.outputs.add(actor);
				}
				for (IKdlActuator actor : extended.getParameters()) {
					this.parameters.add(actor);
				}
				for (IKdlAnnotation annotation : extended.getAnnotations()) {
					this.annotations.add(annotation);
				} 
			} else {
				this.errors.add("cannot find imported actuator " + o.getExtended());
			}
		}

		if (o.getBody() != null) {

			this.geometry = o.getBody().getGeometry();
			this.javaClass = o.getBody().getJavaClass();

			if (o.getBody().getComputations() != null) {
				KdlComputation kc = new KdlComputation();
				for (Function ctx : o.getBody().getComputations().getFunctions()) {
					kc.add(new KdlContextualizer(ctx));
				}
				this.computations.add(kc);
			}

			for (ActorDefinition actor : o.getBody().getDataflows()) {
				IKdlActuator act = new KdlActuator(actor, previousActuators);
				if (act.isImport()) {
					this.inputs.add(act);
				}
				if (act.isExport()) {
					this.outputs.add(act);
				}
				if (act.isParameter()) {
					this.parameters.add(act);
				}
				this.actors.add(act);
			}

			for (String target : o.getTargets()) {
				targets.add(Target.valueOf(target.toUpperCase()));
			}

		}

		for (Function coverage : o.getCoverage()) {
			this.coverage.add(new KdlContextualizer(coverage));
		}

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
	public Collection<IKdlComputation> getComputations() {
		return computations;
	}

	@Override
	public Collection<IKdlActuator> getInputs() {
		return inputs;
	}

	@Override
	public Collection<IKdlActuator> getParameters() {
		return parameters;
	}

	@Override
	public boolean isExport() {
		return exported;
	}

	@Override
	public boolean isImport() {
		return imported;
	}

	@Override
	public boolean isFilter() {
		return isFilter;
	}

	public Collection<IKdlActuator> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<IKdlActuator> outputs) {
		this.outputs = outputs;
	}

	@Override
	public List<IKdlActuator> getActors() {
		return actors;
	}

	public void setActors(List<IKdlActuator> actors) {
		this.actors = actors;
	}

	@Override
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	public String getSemantics() {
		return semantics;
	}

	public void setSemantics(String semantics) {
		this.semantics = semantics;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public void setInputs(List<IKdlActuator> inputs) {
		this.inputs = inputs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setComputation(List<IKdlComputation> computations) {
		this.computations = computations;
	}

	public void setExported(boolean exported) {
		this.exported = exported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}

	@Override
	public Type getType() {
		return type;
	}

	public String toString() {
		return type + " " + name + " (" + inputs.size() + " in, " + outputs.size() + " out)";
	}

	public boolean isMultipleInstances() {
		return multipleInstances;
	}

	public void setMultipleInstances(boolean multipleInstances) {
		this.multipleInstances = multipleInstances;
	}

	public boolean isMoreInstancesAllowed() {
		return moreInstancesAllowed;
	}

	public void setMoreInstancesAllowed(boolean moreInstancesAllowed) {
		this.moreInstancesAllowed = moreInstancesAllowed;
	}

	public int getInstanceCount() {
		return instanceCount;
	}

	public void setInstanceCount(int instanceCount) {
		this.instanceCount = instanceCount;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public boolean isParameter() {
		return parameter;
	}

	@Override
	public Object getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String getJavaClass() {
		return javaClass;
	}

	@Override
	public Set<String> getEnumValues() {
		return enumValues;
	}

	@Override
	public Collection<Target> getTargets() {
		return targets;
	}

	@Override
	public String getNewObservationType() {
		return newObservationType;
	}

	@Override
	public String getNewObservationUrn() {
		return newObservationUrn;
	}

	public void setParameters(List<IKdlActuator> parameters) {
		this.parameters = parameters;
	}

	public void setComputations(List<IKdlComputation> computations) {
		this.computations = computations;
	}

	public void setNewObservationType(String newObservationType) {
		this.newObservationType = newObservationType;
	}

	public void setNewObservationUrn(String newObservationUrn) {
		this.newObservationUrn = newObservationUrn;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	public void setParameter(boolean parameter) {
		this.parameter = parameter;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setEnumValues(Set<String> enumValues) {
		this.enumValues = enumValues;
	}

	public void setTargets(Set<Target> targets) {
		this.targets = targets;
	}

	@Override
	public List<IKdlContextualizer> getCoverage() {
		return coverage;
	}

	public void setCoverage(List<IKdlContextualizer> coverage) {
		this.coverage = coverage;
	}

	@Override
	public boolean isFinal() {
		return isFinal;
	}

	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean isAbstract() {
		return isAbstract;
	}

	@Override
	public boolean isExpression() {
		return isExpression;
	}

	@Override
	public List<IKdlAnnotation> getAnnotations() {
		return annotations;
	}

	@Override
	public boolean isResolution() {
		return isResolution;
	}

	public void setResolution(boolean isResolution) {
		this.isResolution = isResolution;
	}

	@Override
	public String getUnit() {
		return unit;
	}

	@Override
	public boolean isTaggingAnnotation() {
		return isTaggingAnnotation;
	}
}
