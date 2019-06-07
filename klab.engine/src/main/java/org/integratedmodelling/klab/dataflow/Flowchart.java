package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.IExpression.Context;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Simple bean that holds the structure of the dataflow and the documentation
 * harvested from the computations. The DataflowGraph can compile this to an Elk
 * graph.
 * 
 * @author ferdinando.villa
 *
 */
public class Flowchart {

	private Element root;
	private List<Pair<String, String>> connections = new ArrayList<>();
	private Map<String, Element> elements = new HashMap<>();
	private Set<String> externalInputs = new HashSet<>();

	/*
	 * If this is true (not default), each element replicates external inputs and
	 * connects its computation to the input port. Otherwise links are created
	 * directly to the actuator output that produces the artifact. With false the
	 * diagram is cleaner, with true it's more respectful of the modularity in the
	 * dataflow.
	 * 
	 * Actuators that rename the inputs to internal names have the inputs anyway...
	 */
	private boolean makeActuatorPorts = false;
	/*
	 * ...unless this is set to false.
	 */
	private boolean makeRenamedPorts = true;

	public static class Element {

		public static enum Type {
			// actor types
			ACTUATOR, RESOURCE, INSTANTIATOR, RESOLVER, TABLE, CONDITIONAL,
			// link types
			FLOW, CONTEXTUALIZATION
		}

		private String id;
		private String name;
		private String label;
		private String description;
		private String documentation;
		private String tooltip;
		private Type type;

		private List<String> inputs = new ArrayList<>();
		private List<String> outputs = new ArrayList<>();
		private List<Element> children = new ArrayList<>();

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDocumentation() {
			return documentation;
		}

		public void setDocumentation(String documentation) {
			this.documentation = documentation;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public List<String> getInputs() {
			return inputs;
		}

		public String getOrCreateInput(String inputId) {
			String ret = id + "." + inputId;
			if (!inputs.contains(ret)) {
				inputs.add(ret);
			}
			return ret;
		}

		public String getOrCreateOutput(String inputId) {
			String ret = id + "." + inputId;
			if (!outputs.contains(ret)) {
				outputs.add(ret);
			}
			return ret;
		}

		public void setInputs(List<String> inputs) {
			this.inputs = inputs;
		}

		/**
		 * Inputs have the format containingelement.formalnameofinput
		 * 
		 * @return
		 */
		public List<String> getOutputs() {
			return outputs;
		}

		/**
		 * Outputs have the format containingelement.formalnameofoutput.
		 * 
		 * @return
		 */
		public void setOutputs(List<String> outputs) {
			this.outputs = outputs;
		}

		public List<Element> getChildren() {
			return children;
		}

		public void setChildren(List<Element> children) {
			this.children = children;
		}

		public String getTooltip() {
			return tooltip;
		}

		public void setTooltip(String tooltip) {
			this.tooltip = tooltip;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Element [id=" + id + ", name=" + name + ", type=" + type + ", inputs=" + inputs + ", outputs="
					+ outputs + ", children=" + children + "]";
		}

		public String getNodeId() {
			return type == null ? id : (type.name().toLowerCase() + "." + id);
		}

	}

	/**
	 * Create a flowchart from a dataflow.
	 * 
	 * @param dataflow
	 * @return
	 */
	public static Flowchart create(Dataflow dataflow) {
		Flowchart ret = new Flowchart();
		ret.initialize(dataflow.getActuators().get(0));
		ret.compile((Actuator) dataflow.getActuators().get(0), null);
		return ret;
	}

	/*
	 * Collect all symbols produced in this workflow, using their formal names only.
	 * Primarily meant to expose if an optional output is being used downstream, so
	 * that the corresponding producer is recorded. Also create list of external
	 * input symbols to simplify connections.
	 * 
	 * @param dataflow
	 */
	private void initialize(IActuator actuator) {
		if (!(actuator instanceof Dataflow)) {
			if (actuator.isInput()) {
				externalInputs.add(actuator.getName());
			} else if (!((Actuator) actuator).isReference()) {
				Element element = compileElement(actuator);
				if (this.root == null) {
					this.root = element;
				}
				elements.put(actuator.getName(), element);
			}
		}
		for (IActuator child : actuator.getActuators()) {
			initialize(child);
		}
	}

	private Element compileElement(IActuator actuator) {
		Element ret = new Element();
		ret.id = actuator.getDataflowId();
		ret.type = Element.Type.ACTUATOR;
		ret.setName(actuator.getName());
		return ret;
	}

	private Element compile(Actuator actuator, Element parent) {

		if (actuator.isReference() || actuator.isInput()) {
			return null;
		}

		Element element = elements.get(actuator.getName());

		/*
		 * Child actuators are either inputs to our computations, references to
		 * previously seen actuators, or external inputs. We map the local name for the
		 * input in this actuator to the corresponding output of the actuator that
		 * produces it, or to the external input where it will be available.
		 */
		Map<String, String> inputPathways = new HashMap<>();
		for (IActuator child : actuator.getActuators()) {

			/*
			 * Computations defined in here go in as children
			 */
			if (!child.isInput() && !((Actuator) child).isReference()) {
				Element cel = compile((Actuator) child, element);
				if (cel != null) {
					element.children.add(cel);
				}
			}

			/*
			 * Make this optional (if (separateInputs && ...))so we can also have all inputs
			 * connect to the actuator if wanted.
			 */
			String output = null;
			if (makeActuatorPorts || !(child.getAlias().equals(child.getName()) && makeRenamedPorts)) {
				/*
				 * Create an input into this element with the translated name and connect it to
				 * the original producer, then set the input ID in the map for the aliased name.
				 */
				String localInput = element.getOrCreateInput(child.getName());
				if (child.isInput()) {
					output = root.getOrCreateOutput(child.getName());
				} else {
					Element producer = elements.get(child.getName());
					if (producer != null) {
						output = producer.getOrCreateOutput(child.getName());
					}
				}
				if (output != null) {
					connections.add(new Pair<>(output, localInput));
					inputPathways.put(child.getAlias(), localInput);
				}

			} else {
				/*
				 * Find the producer, get the output ID and set it in the map for the aliased
				 * (YES - same if not aliased) name.
				 */
				Element producer = elements.get(child.getName());
				if (producer != null) {
					output = producer.getOrCreateOutput(child.getName());
					inputPathways.put(child.getAlias(), output);
				}
			}

		}

		/*
		 * each actuator has one or more tracks for its targets. Should have the map of
		 * localName -> current (latest) output for each target. The first time a target
		 * is mentioned, the link to its previous output should be created - either the
		 * original source or, if renamed, the renaming input on this element - and the
		 * new output should substitute the previous in the map.
		 */

		/*
		 * compile mediations for any of the inputs
		 */
		for (Pair<IServiceCall, IComputableResource> actor : actuator.getComputationStrategy()) {
			Element computation = compileComputation(actor, element, inputPathways, actuator);
			element.children.add(computation);
			for (String input : computation.getInputs()) {
				// create link to its inputs, follow track to next unless targetId is set
			}
		}

		/*
		 * go down into computations; filter inputs through local names. Track indirect
		 * targets and 'self' when the input is the same name as the actuator.
		 */
		for (Pair<IServiceCall, IComputableResource> actor : actuator.getComputationStrategy()) {
			Element computation = compileComputation(actor, element, inputPathways, actuator);
			element.children.add(computation);
			for (String input : computation.getInputs()) {
				// create link to its inputs, follow track to next unless targetId is set
			}
		}

		return element;
	}

	/*
	 * TODO needs actuator for model context (taginput) and local catalog with all
	 * names imported in actuator and their current input.
	 */
	private Element compileComputation(Pair<IServiceCall, IComputableResource> computation, Element parent,
			Map<String, String> inputPathways, Actuator context) {

		IPrototype callPrototype = Extensions.INSTANCE.getPrototype(computation.getFirst().getName());

		Element ret = new Element();

		parent.getChildren().add(ret);

		String computationTarget = computation.getSecond().getTarget() == null ? context.getAlias()
				: computation.getSecond().getTarget().getLocalName();
		String mainOutput = parent.getName();
		Set<String> additionalOutputs = new HashSet<>();
		Set<String> computationInputs = new HashSet<>();

		// TODO description, documentation (template with parameter substitution)
		ret.id = computation.getSecond().getDataflowId();
		ret.label = callPrototype.getLabel();
		ret.type = Element.Type.RESOLVER;

		if (computation.getSecond().getServiceCall() != null) {

			/*
			 * Functions: check any parameters that identify artifacts against local catalog
			 * and the taginput annotations. Use exports for additional outputs and check
			 * with output map.
			 */
			IPrototype prototype = Extensions.INSTANCE.getPrototype(computation.getSecond().getServiceCall().getName());
			if (prototype != null) {
				for (String arg : computation.getSecond().getServiceCall().getParameters().keySet()) {
					Argument argument = prototype.getArgument(arg);
					if (argument != null && argument.isArtifact()) {
						computationInputs
								.add(computation.getSecond().getServiceCall().getParameters().get(arg, String.class));
					}
				}
				IModel model = context.getModel();
				if (model != null) {
					for (String s : prototype.listInputTags()) {
						for (IObservable observable : model.getDependencies()) {
							if (Annotations.INSTANCE.hasAnnotation(observable, s)) {
								computationInputs.add(observable.getLocalName());
							}
						}
					}
				}

				if (prototype.getType() == IArtifact.Type.OBJECT) {
					ret.type = Element.Type.INSTANTIATOR;
				}
			}

		} else if (computation.getSecond().getExpression() != null) {

			/*
			 * Expressions: must use local IDs to process identifiers that are inputs
			 */
			Descriptor descriptor = Extensions.INSTANCE.getLanguageProcessor(computation.getSecond().getLanguage())
					.describe(computation.getSecond().getExpression(), getExpressionContext(context));

			for (String identifier : descriptor.getIdentifiers()) {
				if (inputPathways.containsKey(identifier)) {
					computationInputs.add(identifier);
				}
			}

		} else if (computation.getSecond().getUrn() != null) {

			ret.type = Element.Type.RESOURCE;

			IResource resource = Resources.INSTANCE.resolveResource(computation.getSecond().getUrn());
			if (resource != null) {

				// personalize label and description
				ret.label = resource.getAdapterType().toUpperCase() + " resource";
				ret.setTooltip(resource.getUrn());

				/*
				 * Resources: use inputs, check output map for additional outputs and add ret as
				 * a producer if used.
				 */
				for (Attribute input : resource.getInputs()) {
					if (inputPathways.containsKey(input.getName())) {
						computationInputs.add(input.getName());
					}
				}

				for (Attribute output : resource.getOutputs()) {
					if (elements.containsKey(output.getName())) {
						additionalOutputs.add(output.getName());
					}
				}

			}

		} else if (computation.getSecond().getClassification() != null) {

			/*
			 * input is simply the target of the computation. May have expressions in the
			 * classifiers but we can probably ignore them (they're 'global' within the
			 * actuator.
			 */
			computationInputs.add(computationTarget);
			ret.type = Element.Type.TABLE;

		} else if (computation.getSecond().getLookupTable() != null) {

			/*
			 * Lookup tables need their inputs and if the result column contains
			 * expressions, they will also need their expression inputs, but we ignore them
			 * as in classifications.
			 */
			for (String s : computation.getSecond().getLookupTable().getArguments()) {
				if (!"?".equals(s) && !"*".equals(s)) {
					computationInputs.add(s);
				}
			}
			ret.type = Element.Type.TABLE;

		}

		/*
		 * Create inputs and outputs and extend the pathway by one step. If there is an
		 * output create it in the parent element; connect directly (?).
		 */

		return ret;
	}

	/**
	 * Return an expression context that knows the inputs named in this actuator (by
	 * local name).
	 * 
	 * @param inputPathways
	 * @return
	 */
	private Context getExpressionContext(Actuator actuator) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * The root element represents the dataflow, with all the steps in its children.
	 * 
	 * @return
	 */
	public Element getRoot() {
		return root;
	}

	/**
	 * Connections are between inputs IDs and output IDs. These may be held in any
	 * Element.
	 * 
	 * @return all connections
	 */
	public List<Pair<String, String>> getConnections() {
		return connections;
	}

}
