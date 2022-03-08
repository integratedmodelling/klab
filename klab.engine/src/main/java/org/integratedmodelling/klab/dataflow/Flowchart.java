package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.IExpression.Scope;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.documentation.DataflowDocumentation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Simple bean that holds the structure of the dataflow and the documentation
 * harvested from the computations. The DataflowGraph can compile this to an Elk
 * graph.
 * 
 * @author ferdinando.villa
 *
 */
public class Flowchart {

	private Element root = new Element();
	private List<Pair<String, String>> connections = new ArrayList<>();
	private Map<String, Element> elementsByName = new HashMap<>();
	private Map<String, Element> elementsById = new HashMap<>();
	private Map<String, String> externalInputs = new HashMap<>();
	private Map<String, String> outputs = new HashMap<>();
	public IRuntimeScope runtimeScope;
	private Graph<IActuator, DefaultEdge> graph;
	private KlabElkGraphFactory kelk = KlabElkGraphFactory.keINSTANCE;

	/*
	 * below filled on compilation
	 */
	private Map<String, ElkConnectableShape> nodes;
	private Map<String, String> computationToNodeId;
	private Map<String, Element> elements;
	// private String id;

	public static enum ElementType {
		// actor types
		ACTUATOR, RESOURCE, INSTANTIATOR, RESOLVER, TABLE, CONDITIONAL,
		// link types
		FLOW, CONTEXTUALIZATION
	}

	public class Element {

		private String id;
		private String name;
		private String label;
		private String description;
		private String documentation;
		private String tooltip;
		private ElementType type;

		/*
		 * inputs and outputs map the reference name of the observable to the
		 * correspondent port ID in the element.
		 */
		private Map<String, String> inputs = new LinkedHashMap<>();
		private Map<String, String> outputs = new LinkedHashMap<>();
		private Map<String, String> localNames = new HashMap<>();
		private List<Element> children = new ArrayList<>();

		/**
		 * This contains the last child element of the chain that produces the named
		 * path for each output handled by the actuator. When added, they only handle
		 * their inputs; outputs are pulled from the last element when the next
		 * operation or the final output request them.
		 */
		private Map<String, Element> datapaths = new HashMap<>();

		Element(Actuator actuator) {

			this.id = actuator.getId();
			this.type = ElementType.ACTUATOR;
			this.name = actuator.getName();
			this.label = StringUtils
					.capitalize(Observables.INSTANCE.getDisplayName(actuator.getObservable()).replaceAll("_", " "));
			this.documentation = DataflowDocumentation.INSTANCE.getDocumentation(this, actuator,
					Flowchart.this.runtimeScope);

			elementsByName.put(actuator.getName(), this);
			elementsById.put(this.id, this);
			if (root == null) {
				root = this;
			}
		}

		Element(IServiceCall serviceCall, IContextualizable contextualizable, Element parent) {
			this.id = "res" + ((ComputableResource) contextualizable).getId();
			this.type = ElementType.RESOLVER;
			this.label = Extensions.INSTANCE.getServiceLabel(serviceCall);
			this.name = serviceCall.getName();
			this.documentation = DataflowDocumentation.INSTANCE.getDocumentation(this,
					new Pair<>(serviceCall, contextualizable), Flowchart.this.runtimeScope);
			elementsById.put(this.id, this);
			parent.children.add(this);
		}

//		Element(Actuator actuator, Pair<IServiceCall, IContextualizable> resource) {
//			this.id = ((ComputableResource) resource.getSecond()).getId();
//			this.type = ElementType.RESOLVER;
//			this.label = Extensions.INSTANCE.getServiceLabel(resource.getFirst());
//			this.name = resource.getFirst().getName();
//			this.documentation = DataflowDocumentation.INSTANCE.getDocumentation(this, resource,
//					Flowchart.this.runtimeScope);
//			elementsById.put(this.id, this);
//		}

		// only for the root element
		private Element() {
			this.id = "root";
			this.type = ElementType.RESOLVER;
			this.label = "";
			this.name = "";
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getLabel() {
			return label == null ? getName() : label;
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

		public ElementType getType() {
			return type;
		}

		public void setType(ElementType type) {
			this.type = type;
		}

		public List<String> getInputs() {
			return new ArrayList<>(inputs.values());
		}

		public String getMainOutput() {
			if (this.outputs.isEmpty()) {
				return null;
			}
			return this.outputs.get(this.outputs.keySet().iterator().next());
		}

		public String getOrCreateInput(String inputId, String... infixes) {

			if (inputs.containsKey(inputId)) {
				return inputs.get(inputId);
			}

			String ret = id + ".in.";
			if (infixes != null) {
				for (String infix : infixes) {
					ret += infix + ".";
				}
			}
			ret += inputId;
			inputs.put(inputId, ret);

			return ret;
		}

		public String getOrCreateOutput(String inputId, String... infixes) {

			if (outputs.containsKey(inputId)) {
				return outputs.get(inputId);
			}

			String ret = id + ".out.";
			if (infixes != null) {
				for (String infix : infixes) {
					ret += infix + ".";
				}
			}
			ret += inputId;
			outputs.put(inputId, ret);

			return ret;
		}

//		public void setInputs(List<String> inputs) {
//			this.inputs = inputs;
//		}

		/**
		 * Inputs have the format containingelement.formalnameofinput
		 * 
		 * @return
		 */
		public List<String> getOutputs() {
			return new ArrayList<>(outputs.values());
		}

//		/**
//		 * Outputs have the format containingelement.formalnameofoutput.
//		 * 
//		 * @return
//		 */
//		public void setOutputs(List<String> outputs) {
//			this.outputs = outputs;
//		}

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
			return "Element [id=" + id + ", name=" + name + ", type=" + type + ", label=" + label + "]";
		}

		public String getNodeId() {
			return type == null ? id : (type.name().toLowerCase() + "." + id);
		}

		public void addChild(Element cel) {
			children.add(cel);
		}

	}

	public Flowchart(IRuntimeScope scope, Graph<IActuator, DefaultEdge> dataflow) {
		this.runtimeScope = scope;
		this.graph = dataflow;
	}

	/**
	 * Create a flowchart from a dataflow. Pull out the passed output(s) if not
	 * null.
	 * 
	 * @param dataflow
	 * @return
	 */
	public static Flowchart create(IActuator dataflow, Graph<IActuator, DefaultEdge> structure, IRuntimeScope scope) {

		Flowchart ret = new Flowchart(scope, structure);
		ret.root = ret.compile((Actuator) dataflow, null);
		return ret;
	}

	private Element compile(Actuator actuator, Element parentNode) {

		Element ret = new Element(actuator);

		if (parentNode != null) {
			parentNode.children.add(ret);
		}

		// these to avoid double counting
		Set<String> outputsDefined = new HashSet<>();
		Set<String> inputsDefined = new HashSet<>();
		Map<IActuator, String> queriedConnections = new HashMap<>();

		/*
		 * outputs: all queried outputs for a resolver, or the dependencies for an
		 * actuator with a model.
		 */

		if (actuator.getType() == IArtifact.Type.RESOLVE) {
			for (DefaultEdge edge : graph.incomingEdgesOf(actuator)) {
				IActuator child = graph.getEdgeSource(edge);
				String queriedOutput = ret
						.getOrCreateOutput("queried." + ((Actuator) child).getObservable().getReferenceName());
				outputsDefined.add(queriedOutput);
				queriedConnections.put(child, queriedOutput);
			}
		} else {
			elementsByName.put(actuator.getName(), ret);
		}

		/*
		 * inputs and outputs from the internal model, using their reference names.
		 * Register the legitimate outputs.
		 */
		if (actuator.getModel() != null) {
			for (IObservable observable : actuator.getModel().getObservables()) {
				outputsDefined.add(ret.getOrCreateOutput(observable.getReferenceName()));
			}
//			for (IObservable dependency : actuator.getModel().getDependencies()) {
//				inputsDefined.add(ret.getOrCreateInput(dependency.getReferenceName()));
//			}
		}

		/*
		 * internal structure; gather imports
		 */
		for (DefaultEdge edge : graph.incomingEdgesOf(actuator)) {

			IActuator childActuator = graph.getEdgeSource(edge);
			Element child = compile((Actuator) childActuator, ret);

			if (childActuator.isReference() || childActuator.isInput()) {
				/*
				 * record actuator as an input, using the local name. We must have the reference
				 * name in the inputs already from the model.
				 */
			}

			/*
			 * if we have queried outputs, connect the main output of the actuator
			 */
			if (queriedConnections.containsKey(childActuator)) {
				connect(child.getMainOutput(), queriedConnections.get(childActuator));
			}

		}

		/*
		 * compile mediations for any of the inputs. These will extend the input
		 * pathways.
		 */
		for (Pair<IServiceCall, IContextualizable> actor : actuator.getMediationStrategy()) {
			compile(actor.getFirst(), actor.getSecond(), ret, actuator, true);
		}

		/*
		 * go down into computations; filter inputs through local names. Track indirect
		 * targets and 'self' when the input is the same name as the actuator.
		 */
		for (Pair<IServiceCall, IContextualizable> actor : actuator.getComputationStrategy()) {
			compile(actor.getFirst(), actor.getSecond(), ret, actuator, false);
		}
		
		/*
		 * connect the final leg of all instantiated datapaths to its output for the
		 * actuator.
		 */
		for (String path : ret.datapaths.keySet()) {
			connect(ret.datapaths.get(path).getOrCreateOutput(path), ret.getOrCreateOutput(path));
		}

		return ret;
	}

	private void compile(IServiceCall computedDeclaration, IContextualizable contextualizable, Element parent,
			Actuator actuator, boolean isMediation) {

		/*
		 * computation may instantiate a datapath or pull an output from a previously
		 * generated one. In all cases it will use the local names from the actuator.
		 */

		Element ret = new Element(computedDeclaration, contextualizable, parent);

		computeDatapaths(computedDeclaration, contextualizable, ret, actuator, isMediation, parent);

		/*
		 * inputs from the computation come from the parent's actuators, which have been computed upstream
		 */
		for (String input : ret.inputs.keySet()) {
			connect(findProvider(input, parent), ret.inputs.get(input));
		}
		
		for (String output : ret.outputs.keySet()) {
			if (parent.datapaths.containsKey(output)) {
				/*
				 * connect the correspondent output of the current element to the input of the
				 * next
				 */
				Element current = parent.datapaths.get(output);
				connect(current.getOrCreateOutput(output), ret.getOrCreateInput(output));
			}
			parent.datapaths.put(output, ret);
		}
	}

	private String findProvider(String input, Element element) {
		for (Element child : element.children) {
			if (input.equals(child.name)) {
				return child.getMainOutput();
			}
		}
		return null;
	}

	/**
	 * Compute inputs and outputs for the passed resource and insert them into the
	 * containers in the element that describes them. This is contextual for the
	 * contextualizers that use annotations to define their inputs (depends on
	 * annotations that will be inherited by the states through their observables,
	 * so we can find them in the dependent actuators' observables).
	 * 
	 * @param computedDeclaration
	 * @param contextualizable
	 * @param element
	 * @param actuator
	 * @param isMediation
	 */
	private void computeDatapaths(IServiceCall computedDeclaration, IContextualizable contextualizable, Element element,
			Actuator actuator, boolean isMediation, Element parent) {

		if (actuator.getAlias() != null) {
			element.localNames.put(actuator.getName(), actuator.getAlias());
		}

		Actuator target = actuator;
		String contextualizationTarget = actuator.getName();
		if (contextualizable.getTargetId() != null) {
			target = findActuator(contextualizable.getTargetId(), actuator);
			if (target != null) {
				contextualizationTarget = target.getName();
			}
		}

		if (contextualizable.getServiceCall() != null) {

			analyzeServiceCall(contextualizable.getServiceCall(), actuator, element, parent, contextualizationTarget);

		} else if (contextualizable.getExpression() != null) {

			if (target != null) {

				element.outputs.put(target.getName(), element.getOrCreateOutput(target.getName()));

				/*
				 * the calling function is important if the expression is passed to a classifier
				 * or something more complex.
				 */
				analyzeServiceCall(computedDeclaration, actuator, element, parent, target.getName());

				for (String input : getExpressionInputs(contextualizable.getExpression().getCode(),
						contextualizable.getLanguage(), actuator)) {
					Actuator dependency = findActuator(input, actuator);
					if (dependency != null) {
						element.inputs.put(dependency.getName(), element.getOrCreateInput(dependency.getName()));
						if (!dependency.getName().equals(input)) {
							element.localNames.put(dependency.getName(), input);
						}
					}
				}
			}

		} else if (contextualizable.getUrn() != null) {

			element.type = ElementType.RESOURCE;
			IResource resource = Resources.INSTANCE.resolveResource(contextualizable.getUrn());
			if (resource != null) {

				if (resource.getType() != IArtifact.Type.VOID) {
					element.outputs.put(actuator.getName(), element.getOrCreateOutput(actuator.getName()));
				}

				// personalize label and description
				element.name = resource.getUrn();
				element.label = resource.getAdapterType().toUpperCase() + " resource";
				element.setTooltip("Contextualize URN " + resource.getUrn());

				/*
				 * Resources: use inputs, check output map for additional outputs and add ret as
				 * a producer if used.
				 */
				for (Attribute input : resource.getInputs()) {
					Actuator dependency = findActuator(input.getName(), actuator);
					if (dependency != null) {
						element.inputs.put(dependency.getName(), element.getOrCreateInput(dependency.getName()));
						if (!dependency.getName().equals(input.getName())) {
							element.localNames.put(dependency.getName(), input.getName());
						}
					}
				}

				/*
				 * additional outputs only if they are linked through the model's observables
				 */
				for (Attribute output : resource.getOutputs()) {
					IObservable out = findOutput(output.getName(), actuator);
					if (out != null) {
						element.outputs.put(out.getReferenceName(), element.getOrCreateOutput(out.getReferenceName()));
					}
				}
			}

		} else if (contextualizable.getType() == IContextualizable.Type.CLASSIFICATION) {

			// works as a filter, so the main observable gets in and out
			element.outputs.put(actuator.getName(), element.getOrCreateOutput(actuator.getName()));
			element.inputs.put(actuator.getName(), element.getOrCreateInput(actuator.getName()));
			element.type = ElementType.TABLE;
//
			if (((ComputableResource) contextualizable).getValidatedResource(Object.class) instanceof Classification) {

				for (IKimExpression expression : ((ComputableResource) contextualizable)
						.getValidatedResource(Classification.class).getUniqueExpressions()) {
					String expcode = expression.getCode();
					String explang = expression.getLanguage();
					for (String input : getExpressionInputs(expcode, explang, actuator)) {
						Actuator dependency = findActuator(input, actuator);
						if (dependency != null) {
							element.inputs.put(dependency.getName(), element.getOrCreateInput(dependency.getName()));
							if (!dependency.getName().equals(input)) {
								element.localNames.put(dependency.getName(), input);
							}
						}
					}
				}
			}

		} else if (contextualizable.getLookupTable() != null) {

			element.outputs.put(actuator.getName(), element.getOrCreateOutput(actuator.getName()));

			/*
			 * Lookup tables need their inputs and if the result column contains
			 * expressions, they will also need their expression inputs, but we ignore them
			 * as in classifications.
			 */
			for (IKimLookupTable.Argument s : contextualizable.getLookupTable().getArguments()) {
				if (s.id != null) {
					String input = s.id;
					if ("self".equals(s.id)) {
						input = actuator.getName();
					}
					if (!"?".equals(input) && !"*".equals(input)) {
						Actuator dependency = findActuator(input, actuator);
						if (dependency != null) {
							element.inputs.put(dependency.getName(), element.getOrCreateInput(dependency.getName()));
							if (!dependency.getName().equals(input)) {
								element.localNames.put(dependency.getName(), input);
							}
						}
					}
				}
			}

			if (((ComputableResource) contextualizable).getValidatedResource(Object.class) instanceof LookupTable) {

				for (IKimExpression expression : ((ComputableResource) contextualizable)
						.getValidatedResource(LookupTable.class).getUniqueExpressions()) {
					String expcode = expression.getCode();
					String explang = expression.getLanguage();
					for (String input : getExpressionInputs(expcode, explang, actuator)) {
						Actuator dependency = findActuator(input, actuator);
						if (dependency != null) {
							element.inputs.put(dependency.getName(), element.getOrCreateInput(dependency.getName()));
							if (!dependency.getName().equals(input)) {
								element.localNames.put(dependency.getName(), input);
							}
						}
					}
				}
			}

			element.type = ElementType.TABLE;

		} else if (contextualizable.getType() == IContextualizable.Type.CONVERSION) {

			if (contextualizable.getServiceCall() != null) {
				element.label = Extensions.INSTANCE.getServiceLabel(contextualizable.getServiceCall());
			}

			element.outputs.put(actuator.getName(), element.getOrCreateOutput(actuator.getName()));
			element.inputs.put(actuator.getName(), element.getOrCreateInput(actuator.getName()));

		} else {
			Logging.INSTANCE.warn("INTERNAL: unhandled computation in dataflow graph: " + contextualizable);
		}

	}

	/**
	 * FIXME needs to account for resources with custom output naming strategies.
	 * 
	 * @param name
	 * @param actuator
	 * @return
	 */
	private IObservable findOutput(String name, Actuator actuator) {
		if (actuator.getModel() != null) {
			for (int n = 1; n < actuator.getModel().getObservables().size(); n++) {
				if (name.equals(actuator.getModel().getObservables().get(n).getName())) {
					return actuator.getModel().getObservables().get(n);
				}
			}
		}
		return null;
	}

	private Actuator findActuator(String name, Actuator actuator) {
		if ("self".equals(name)) {
			return actuator;
		}
		for (DefaultEdge edge : graph.incomingEdgesOf(actuator)) {
			IActuator child = graph.getEdgeSource(edge);
			if (name.equals(child.getName()) || name.equals(child.getAlias())) {
				return (Actuator) child;
			}
		}
		return null;
	}

//	private Element compileActuator(Actuator actuator, Element parent) {
//
//		if (actuator.isReference()) {
//			return null;
//		}
//
//		if (actuator.isInput()) {
//			// TODO an import may be compiled into something different
//			return null;
//		}
//
//		Element element = new Element(actuator);
//
//		/*
//		 * Compile the outputs first: if a resolver, one output per actuator contained
//		 * in the graph plus any model output if there is a model; otherwise, put ports
//		 * for all model outputs except processes and only connect those that get used.
//		 */
//
//		for (DefaultEdge edge : graph.incomingEdgesOf(actuator)) {
//
//			IActuator child = graph.getEdgeSource(edge);
//
//			Element cel = compileActuator((Actuator) child, element);
//			if (cel != null) {
//				element.addChild(cel);
//			} else if (child.isInput()) {
//				externalInputs.put(child.getName(), root.getOrCreateInput(child.getName(), "import"));
//			}
//		}
//
//		/*
//		 * compile mediations for any of the inputs. These will extend the input
//		 * pathways.
//		 */
//		for (Pair<IServiceCall, IContextualizable> actor : actuator.getMediationStrategy()) {
//			compileComputation(actor, element, actuator);
//		}
//
//		/*
//		 * go down into computations; filter inputs through local names. Track indirect
//		 * targets and 'self' when the input is the same name as the actuator.
//		 */
//		for (Pair<IServiceCall, IContextualizable> actor : actuator.getComputationStrategy()) {
//			if (actor.getFirst() != null) {
//				compileComputation(actor, element, actuator);
//			}
//		}
//
//		return element;
//	}

	// private Element compileComputation(Pair<IServiceCall, IContextualizable>
	// computation, Element parent,
//			Actuator context) {
//
//		Element ret = new Element(context, computation);
//
//		// TODO description, documentation (template with parameter substitution)
//		ret.label = Extensions.INSTANCE.getServiceLabel(computation.getFirst());
//
//		parent.addChild(ret);
//
//		String computationTarget = (computation.getSecond().getTarget() == null
//				|| computation.getSecond().getTarget().equals(context.getObservable())) ? context.getName()
//						: computation.getSecond().getTarget().getName();
//		if (computation.getSecond().isMediation()) {
//			computationTarget = formalNameOf(computation.getSecond().getMediationTargetId(), context);
//		}
//
//		Set<String> computationOutputs = new HashSet<>();
//		Set<String> computationInputs = new HashSet<>();
//
//		/*
//		 * Normally we have the literal resource as second element and the service call
//		 * that contextualizes it as first.
//		 * 
//		 * If the second element is an expression, the inputs may or may not be
//		 * expressed as parameters in it.
//		 */
//
//		if (computation.getSecond().getServiceCall() != null) {
//
//			analyzeServiceCall(computation.getSecond().getServiceCall(), context, ret, parent, computationTarget,
//					computationInputs, computationOutputs);
//
//		} else if (computation.getSecond().getExpression() != null) {
//
//			computationOutputs.add(computationTarget);
//
//			/*
//			 * the calling function is important if the expression is passed to a classifier
//			 * or something more complex.
//			 */
//			analyzeServiceCall(computation.getFirst(), context, ret, parent, computationTarget, computationInputs,
//					computationOutputs);
//
//			for (String input : getExpressionInputs(computation.getSecond().getExpression().getCode(),
//					computation.getSecond().getLanguage(), context)) {
//				computationInputs.add("self".equals(input) ? computationTarget : input);
//			}
//
//		} else if (computation.getSecond().getUrn() != null) {
//
//			ret.type = ElementType.RESOURCE;
//			IResource resource = Resources.INSTANCE.resolveResource(computation.getSecond().getUrn());
//			if (resource != null) {
//
//				if (resource.getType() != IArtifact.Type.VOID) {
//					computationOutputs.add(computationTarget);
//				}
//
//				// personalize label and description
//				ret.name = resource.getUrn();
//				ret.label = resource.getAdapterType().toUpperCase() + " resource";
//				ret.setTooltip("Contextualize URN " + resource.getUrn());
//
//				/*
//				 * Resources: use inputs, check output map for additional outputs and add ret as
//				 * a producer if used.
//				 */
//				for (Attribute input : resource.getInputs()) {
//					String name = localNameFor(input.getName(), context);
//					if (parent.datapaths.containsKey(name)) {
//						computationInputs.add(input.getName());
//					}
//				}
//
//				for (Attribute output : resource.getOutputs()) {
//					if (elementsByName.containsKey(output.getName())) {
//						computationOutputs.add(output.getName());
//					}
//				}
//			}
//
//		} else if (computation.getSecond().getType() == IContextualizable.Type.CLASSIFICATION) {
//
//			// works as a filter. Ignore expressions in classifiers for now.
//			computationInputs.add(computationTarget);
//			computationOutputs.add(computationTarget);
//			ret.type = ElementType.TABLE;
//
//			if (((ComputableResource) computation.getSecond())
//					.getValidatedResource(Object.class) instanceof Classification) {
//
//				for (IKimExpression expression : ((ComputableResource) computation.getSecond())
//						.getValidatedResource(Classification.class).getUniqueExpressions()) {
//					String expcode = expression.getCode();
//					String explang = expression.getLanguage();
//					for (String input : getExpressionInputs(expcode, explang, context)) {
//						if (!computationInputs.contains(input)) {
//							computationInputs.add(input);
//						}
//					}
//				}
//			}
//
//		} else if (computation.getSecond().getLookupTable() != null) {
//
//			computationOutputs.add(computationTarget);
//
//			/*
//			 * Lookup tables need their inputs and if the result column contains
//			 * expressions, they will also need their expression inputs, but we ignore them
//			 * as in classifications.
//			 */
//			for (IKimLookupTable.Argument s : computation.getSecond().getLookupTable().getArguments()) {
//				if (s.id != null) {
//					String iid = s.id;
//					if ("self".equals(s.id)) {
//						iid = computationTarget;
//					}
//					if (!"?".equals(iid) && !"*".equals(iid)) {
//						computationInputs.add(iid);
//					}
//				}
//			}
//
//			if (((ComputableResource) computation.getSecond())
//					.getValidatedResource(Object.class) instanceof LookupTable) {
//
//				for (IKimExpression expression : ((ComputableResource) computation.getSecond())
//						.getValidatedResource(LookupTable.class).getUniqueExpressions()) {
//					String expcode = expression.getCode();
//					String explang = expression.getLanguage();
//					for (String input : getExpressionInputs(expcode, explang, context)) {
//						if (!computationInputs.contains(input)) {
//							computationInputs.add(input);
//						}
//					}
//				}
//			}
//
//			ret.type = ElementType.TABLE;
//
//		} else if (computation.getSecond().getType() == IContextualizable.Type.CONVERSION) {
//
//			if (computation.getSecond().getServiceCall() != null) {
//				ret.label = Extensions.INSTANCE.getServiceLabel(computation.getSecond().getServiceCall());
//			}
//
//			computationInputs.add(computationTarget);
//			computationOutputs.add(computationTarget);
//
//		} else {
//			Logging.INSTANCE.warn("INTERNAL: unhandled computation in dataflow graph: " + computation.getSecond());
//		}
//
//		for (String input : computationInputs) {
//
//			String inport = ret.getOrCreateInput(input);
//			if (!parent.datapaths.containsKey(input)) {
//				String actualName = formalNameOf(input, context);
//				Element producer = elementsByName.get(actualName);
//				if (producer != null) {
//					String output = producer.getOrCreateOutput(actualName);
////					parent.datapaths.put(input, output);
//				} else if (externalInputs.containsKey(actualName)) {
////					parent.datapaths.put(input, externalInputs.get(actualName));
//				}
//			}
////			connections.add(new Pair<>(parent.datapaths.get(input), inport));
//		}
//
//		for (String output : computationOutputs) {
////			parent.datapaths.put(output, ret.id);
//		}
//
//		return ret;
//	}

	private void analyzeServiceCall(IServiceCall serviceCall, Actuator context, Element element, Element parent,
			String computationTarget/* , Set<String> computationInputs, Set<String> computationOutputs */) {

		/*
		 * Functions: check any parameters that identify artifacts against local catalog
		 * and the taginput annotations. Use exports for additional outputs and check
		 * with output map.
		 */
		IPrototype prototype = Extensions.INSTANCE.getPrototype(serviceCall.getName());

		if (prototype != null) {

			element.label = Extensions.INSTANCE.getServiceLabel(serviceCall);

			if (prototype.getType() != IArtifact.Type.VOID) {
				element.outputs.put(computationTarget, element.getOrCreateOutput(computationTarget));
			}

			/*
			 * match imported artifacts declared in the prototype to imports in the
			 * function. If not available, store the ID so that we can look for artifact
			 * parameters later.
			 */
			Set<String> importArgs = new HashSet<>();
			for (Argument arg : prototype.listImports()) {
				Actuator dependency = findActuator(arg.getName(), context);
				if (dependency != null) {
					element.inputs.put(dependency.getName(), element.getOrCreateInput(dependency.getName()));
					if (!dependency.getName().equals(arg.getName())) {
						element.localNames.put(dependency.getName(), arg.getName());
					}
				}

			}

			/*
			 * Add any additional parameters marked as artifact (passing the parameter) or
			 * named in expressions.
			 */
			for (String arg : serviceCall.getParameters().keySet()) {
				Object parameter = serviceCall.getParameters().get(arg);
				Argument argument = prototype.getArgument(arg);
				if (importArgs.contains(arg)) {
					if (parameter instanceof IConcept || parameter instanceof IObservable) {

//						// must be a known observables, which we will link through its alias
//						IConcept concept = parameter instanceof IConcept ? ((IConcept) parameter)
//								: ((IObservable) parameter).getType();
//
//						for (IActuator dependency : context.getActuators()) {
//							if (concept.getSemanticDistance(((Actuator) dependency).getObservable().getType()) >= 0) {
//								parameter = ((Actuator) dependency).getAlias();
//								break;
//							}
//						}
//					}
//
//					computationInputs.add(parameter.toString());

				} else if (argument == null) {
					continue;
				} else if (argument.isExpression()) {
					Object expression = serviceCall.getParameters().get(arg);
					String expcode = expression instanceof IKimExpression ? ((IKimExpression) expression).getCode()
							: expression.toString();
					String explang = expression instanceof IKimExpression ? ((IKimExpression) expression).getLanguage()
							: null;
					for (String input : getExpressionInputs(expcode, explang, context)) {
						Actuator dependency = findActuator(input, context);
						if (dependency != null) {
							element.inputs.put(dependency.getName(), element.getOrCreateInput(dependency.getName()));
							if (!dependency.getName().equals(input)) {
								element.localNames.put(dependency.getName(), input);
							}
						}

					}
				}
			}

			IModel model = context.getModel();
			if (model != null) {
				for (Argument s : prototype.listImportAnnotations()) {
					for (IObservable observable : model.getDependencies()) {
						if (Annotations.INSTANCE.hasAnnotation(observable, s.getName())) {
								element.inputs.put(observable.getReferenceName(), element.getOrCreateInput(observable.getReferenceName()));
							}
						}
					}
				}
			}

			/*
			 * Check if this is a transformation type so we can add the default target as
			 * argument if not specified.
			 */
			boolean singleArtifact = prototype.listImports().size() == 1;

			/*
			 * add any further outputs if it's used.
			 */
//			for (Argument arg : prototype.listExports()) {
//				if (elementsByName.containsKey(arg.getName())) {
//					computationOutputs.add(arg.getName());
//				}
//			}

			/*
			 * we let computations with a single artifact parameter default their argument
			 * to the main target.
			 */
			if (element.inputs.isEmpty() && singleArtifact) {
				element.inputs.put(computationTarget, element.getOrCreateInput(computationTarget));
			}

			if (prototype.getType().isCountable()) {
				element.type = ElementType.INSTANTIATOR;
			}

		} else {
			// shouldn't happen but here goes
			throw new KlabInternalErrorException("function " + serviceCall.getName() + " is undeclared in dataflow");
		}

	}

	@Override
	public String toString() {
		return dump();
	}

	/**
	 * Get formal name for passed input in context actuator
	 * 
	 * @param input
	 * @param context
	 * @return
	 */
	private String formalNameOf(String input, Actuator context) {
		if (input.equals(context.getName())) {
			return input;
		}
		if ("self".equals(input)) {
			return context.getName();
		}
		for (IActuator child : context.getActuators()) {
			if (child.getAlias() != null && child.getAlias().equals(input)) {
				return child.getName();
			}
		}
		return null;
	}

	private Collection<String> getExpressionInputs(String expcode, String explang, final Actuator context) {

		/*
		 * Expressions: must use local IDs to process identifiers that are inputs
		 */
		final Set<String> aliases = new HashSet<>();
		final Set<String> stateAliases = new HashSet<>();
		final Map<String, IKimConcept.Type> types = new HashMap<>();
		for (IActuator act : context.getActuators()) {
			aliases.add(act.getAlias());
			if (((Actuator) act).getObservable().getArtifactType().isState()) {
				stateAliases.add(act.getAlias());
			}
			types.put(act.getAlias(), Observables.INSTANCE.getObservableType(((Actuator) act).getObservable(), true));
		}
		if (context.getModel() != null) {
			for (IObservable dependency : context.getModel().getDependencies()) {
				if (dependency.is(IKimConcept.Type.QUALITY)) {
					stateAliases.add(dependency.getName());
					types.put(dependency.getName(), Observables.INSTANCE.getObservableType(dependency, true));
				}
			}
		}
		Descriptor descriptor = Extensions.INSTANCE.getLanguageProcessor(explang).describe(expcode, new Scope() {

			@Override
			public Collection<String> getStateIdentifiers() {
				return stateAliases;
			}

			@Override
			public IScale getScale() {
				return runtimeScope.getMergedScale(context);
			}

			@Override
			public Type getReturnType() {
				return Type.VALUE;
			}

			@Override
			public INamespace getNamespace() {
				return ((Actuator) context).getNamespace();
			}

			@Override
			public IMonitor getMonitor() {
				return runtimeScope.getSession().getMonitor();
			}

			@Override
			public Collection<String> getIdentifiers() {
				return aliases;
			}

			@Override
			public Type getIdentifierType(String identifier) {
				return types.get(identifier);
			}

			@Override
			public void addKnownIdentifier(String id, IKimConcept.Type type) {
				// TODO Auto-generated method stub

			}

		});

		return descriptor.getIdentifiers();
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

	public String dump() {
		String ret = dump(root, 0);
		for (Pair<String, String> connection : connections) {
			ret += "\n" + connection.getFirst() + " --> " + connection.getSecond();
		}
		return ret;
	}

	private void connect(String source, String destination) {
		if (source != null && destination != null) {
			this.connections.add(new Pair<>(source, destination));
		}
	}

	private String dump(Element element, int indent) {
		String prefix = StringUtils.spaces(indent);
		String ret = prefix + "E: " + element;
		for (String input : element.getInputs()) {
			ret += "\n" + prefix + "   I: " + input;
		}
		for (String input : element.getOutputs()) {
			ret += "\n" + prefix + "   O: " + input;
		}
		for (Element e : element.getChildren()) {
			ret += "\n" + dump(e, indent + 3);
		}

		return ret;
	}

	public Map<String, String> getExternalInputs() {
		return externalInputs;
	}

	public Collection<String> getOutputs() {
		return root.getOutputs();
	}

	public Element getElementById(String nodeId) {
		return elementsById.get(nodeId);
	}

	@Deprecated
	public String pullOutput(String input) {

		if (elementsByName.containsKey(input)) {
			String ret = root.getOrCreateOutput(input, "export");
			Element element = elementsByName.get(input);
			if (!element.getId().equals(root.getId())) {
				String eout = element.getOrCreateOutput(input);
				connections.add(new Pair<>(eout, ret));
			}
			return ret;
		}
		return null;
	}

	public Collection<ElkConnectableShape> getOutputShapes() {
		List<ElkConnectableShape> ret = new ArrayList<>();
		for (String s : getOutputs()) {
			ret.add(nodes.get(s));
		}
		return ret;
	}

	public ElkConnectableShape getOutput(String observable) {
		String suffix = ".out." + observable;
		for (String node : nodes.keySet()) {
			if (node.endsWith(suffix)) {
				return nodes.get(node);
			}
		}
		return null;
	}

	public ElkNode compile() {

		this.nodes = new HashMap<>();
		this.elements = new HashMap<>();
		this.computationToNodeId = new HashMap<>();

		ElkNode ret = kelk.createGraph(runtimeScope.getContextObservation().getId());
		compile(getRoot(), ret);

		for (Pair<String, String> connection : getConnections()) {
			ElkConnectableShape source = nodes.get(connection.getFirst());
			ElkConnectableShape target = nodes.get(connection.getSecond());
			if (source != null && target != null) {
				kelk.createSimpleEdge(source, target, null);
			}
		}

		for (String s : getExternalInputs().keySet()) {
			ElkConnectableShape source = getOutput(s);
			ElkConnectableShape target = nodes.get(getExternalInputs().get(s));
			if (source != null && target != null) {
				kelk.createSimpleEdge(source, target, null);
			}
		}

		return ret;
	}

	public ElkNode compile(Element element, ElkNode parentNode) {

		ElkNode ret = element.getType() == ElementType.ACTUATOR
				? kelk.createActuatorNode(element.getNodeId(), parentNode)
				: kelk.createServiceNode(element.getNodeId(), parentNode);

		computationToNodeId.put(element.getId(), ret.getIdentifier());

		nodes.put(element.getId(), ret);
		elements.put(ret.getIdentifier(), element);

		ret.getLabels().add(kelk.createLabel(element.getLabel(), element.getId(), ret));

		for (String input : element.getInputs()) {
			ElkPort port = kelk.createPort(input, ret, getPortSide(input, PortSide.WEST));
			nodes.put(input, port);
		}

		for (String output : element.getOutputs()) {
			ElkPort port = kelk.createPort(output, ret, getPortSide(output, PortSide.EAST));
			nodes.put(output, port);
		}

		for (Element child : element.getChildren()) {
			compile(child, ret);
		}

		return ret;
	}

	private PortSide getPortSide(String input, PortSide defaultValue) {
		PortSide ret = defaultValue;
		if (input.contains(".import.")) {
			return PortSide.NORTH;
		} else if (input.contains(".export.")) {
			return PortSide.SOUTH;
		}
		return ret;
	}

	public String getJsonLayout() {

		RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
		ElkNode rootNode = compile();
		engine.layout(rootNode, new BasicProgressMonitor());
		System.out.println(dump());
		return ElkGraphJson.forGraph(rootNode).omitLayout(false).omitZeroDimension(true).omitZeroPositions(true)
				.shortLayoutOptionKeys(true).prettyPrint(true).toJson();

	}

}
