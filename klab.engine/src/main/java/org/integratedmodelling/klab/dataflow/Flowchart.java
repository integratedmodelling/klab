package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IServiceCall;
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
			ACTUATOR, RESOURCE, INSTANTIATOR, RESOLVER, TABLE, CONDITIONAL
		}

		private String id;
		private String label;
		private String description;
		private String documentation;
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
		
	}

	/**
	 * Create a flowchart from a dataflow.
	 * 
	 * @param dataflow
	 * @return
	 */
	public static Flowchart create(Dataflow dataflow) {
		Flowchart ret = new Flowchart();
		ret.collectSymbols(dataflow);
		ret.compile(dataflow, null, new HashMap<>());
		return ret;
	}

	/*
	 * Collect all symbols used in this workflow. Primarily meant to expose if an
	 * optional output is being used downstream, so that a producer can be added for
	 * it.
	 * 
	 * @param dataflow
	 */
	private void collectSymbols(Dataflow dataflow) {
		// TODO Auto-generated method stub
	}

	private Element compile(Actuator actuator, Element parent, Map<String, Element> producers) {

		if (actuator.isReference()) {
			return null;
		}
		if (actuator.isInput()) {
			/*
			 * ensure we have the input in the root node; set producer of formal name to
			 * root input.
			 */
			root.getOrCreateInput(actuator.getName());
			producers.put(actuator.getName(), root);
			return null;
		}

		Element element = elements.get(actuator.getDataflowId());
		if (element == null) {

			element = new Element();
			if (root == null) {
				root = element;
			}

			/*
			 * fill in with actuator detail
			 */
			element.id = actuator.getDataflowId();

			/*
			 * compile child actuators and record the local names if any. NOT like this: map
			 * the local name to the input in this actuator if we translate, or to the
			 * output in the producer if not.
			 */
			Map<String, String> inputSources = new HashMap<>();
			for (IActuator child : actuator.getActuators()) {

				if (child.getAlias() != null) {
					/*
					 * Make this optional (if (separateInputs && ...))so we can also have all inputs
					 * connect to the actuator if wanted.
					 */
					if (makeActuatorPorts || !(child.getAlias().equals(child.getName()) && makeRenamedPorts)) {
						/*
						 * TODO create an input with the translated name and connect it to the original
						 * producer, then set the input ID in the map for the aliased name.
						 */
					} else {
						/*
						 * Find the producer, get the output ID and set it in the map for the aliased
						 * (YES - same if not aliased) name.
						 */
					}
				}

				Element cel = compile((Actuator) child, element, producers);
				if (cel != null) {
					element.children.add(cel);
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
				for (String input : compileComputation(actor, element, inputSources).getInputs()) {
					// create link to its inputs, follow track to next unless targetId is set
				}
			}

			/*
			 * go down into computations; filter inputs through local names. Track indirect
			 * targets and 'self' when the input is the same name as the actuator.
			 */
			for (Pair<IServiceCall, IComputableResource> actor : actuator.getComputationStrategy()) {
				for (String input : compileComputation(actor, element, inputSources).getInputs()) {
					// create link to its inputs, follow track to next unless targetId is set
				}
			}
		}

		return element;
	}

	/*
	 * TODO needs actuator for model context (taginput) and local catalog with all
	 * names imported in actuator and their current input.
	 */
	private Element compileComputation(Pair<IServiceCall, IComputableResource> computation, Element parent,
			Map<String, String> localSources) {

		Element ret = new Element();
		parent.getChildren().add(ret);

		// TODO description

		/*
		 * for functions: check any parameters that identify artifacts against local
		 * catalog and the taginput annotations. Use exports for additional outputs and
		 * check with output map.
		 */

		/*
		 * for expressions: must use context to decompile the identifiers and resolve
		 * links
		 */

		/*
		 * for resources: use inputs, check output map for additional outputs and add
		 * ret as a producer if used.
		 */

		return ret;
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
