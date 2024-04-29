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
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API.PUBLIC.Export;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerScope;
import org.integratedmodelling.klab.api.data.general.IExpression.Forcing;
import org.integratedmodelling.klab.api.data.general.IExpression.Scope;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.provenance.IProvenance.Node;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.documentation.DataflowDocumentation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.provenance.Provenance.NodeWrapper;
import org.integratedmodelling.klab.provenance.ProvenanceEdge;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Simple bean that holds the structure of the dataflow and the documentation
 * harvested from the computations. Can also handle the simple and full
 * provenance graphs. All flowcharts can be compiled into an Elk graph.
 * 
 * @author ferdinando.villa
 *
 */
public class Flowchart {

	private Element root = new Element();
	private Dataflow dataflow;
	private Provenance provenance;
	private List<Pair<String, String>> connections = new ArrayList<>();
	private Map<Pair<String, String>, String> connectionLabels = new HashMap<>();
	private Map<String, Element> elementsByName = new HashMap<>();
	private Map<String, Element> elementsById = new HashMap<>();
	private Map<String, String> externalInputs = new HashMap<>();
	public IRuntimeScope runtimeScope;
	private Graph<IActuator, DefaultEdge> graph;
	private KlabElkGraphFactory kelk = KlabElkGraphFactory.keINSTANCE;

	/*
	 * below filled on compilation
	 */
	private Map<String, ElkConnectableShape> nodes;
	private Map<String, String> computationToNodeId;
	private Map<String, Element> elements;

	public static enum ElementType {
		// node types: dataflow...
		ACTUATOR, RESOURCE, INSTANTIATOR, RESOLVER, TABLE, CONDITIONAL,
		// for provenance (distinguishes semantic entities, aka observations, from
		// non-semantic resources) and the two main agents, respectively controlling
		// tasks (user agent) and the Elephant (k.LAB agent).
		RESOURCE_ENTITY, SEMANTIC_ENTITY, LITERAL_ENTITY, MODEL_ACTIVITY, TASK_ACTIVITY, DATAFLOW_PLAN, KLAB_AGENT,
		USER_AGENT, VIEW_ENTITY
	}

	public static enum LinkType {
		// link types. Dataflow charts use a single, implicit FLOW edge type. Provenance
		// graphs
		// use all the others and may be differentiated by coverage.
		FLOW, WAS_GENERATED_BY, WAS_TRIGGERED_BY, WAS_CONTROLLED_BY, WAS_DERIVED_FROM, USES
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
		
		private Element(String id) {
			this.id = id;
		}

		Element(IProvenance.Node node) {
			this.id = node.getId();
			this.name = node.toString();
			this.label = node.toString();
			this.type = node instanceof NodeWrapper ? ((NodeWrapper) node).getType() : ElementType.RESOURCE_ENTITY;
			this.documentation = DataflowDocumentation.INSTANCE.getDocumentation(this,
					node instanceof NodeWrapper ? ((NodeWrapper) node).getDelegate() : node,
					Flowchart.this.runtimeScope);
			elementsByName.put(this.name, this);
			elementsById.put(this.id, this);
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
		

		// only for the root element
		private Element() {
			this.id = "root";
			this.type = ElementType.RESOLVER;
			this.label = "";
			this.name = "";
		}

		public Element newChild(String id) {
			Element ret = new Element(id);
			this.children.add(ret);
			return ret;
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

		/**
		 * Inputs have the format containingelement.formalnameofinput
		 * 
		 * @return
		 */
		public List<String> getOutputs() {
			return new ArrayList<>(outputs.values());
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
			return "Element [id=" + id + ", name=" + name + ", type=" + type + ", label=" + label + "]";
		}

		public String getNodeId() {
			return type == null ? id : (type.name().toLowerCase() + "." + id);
		}

		public void addChild(Element cel) {
			children.add(cel);
		}

	}

	public Flowchart(Dataflow dataflow, IRuntimeScope scope, Graph<IActuator, DefaultEdge> dgraph) {
		this.dataflow = dataflow;
		this.runtimeScope = scope;
		this.graph = dgraph;
	}

	public Flowchart(Provenance provenance, IRuntimeScope scope) {
		this.provenance = provenance;
		this.runtimeScope = scope;
	}

	/**
	 * Create a flowchart from a dataflow. Pull out the passed output(s) if not
	 * null.
	 * 
	 * @param dataflow
	 * @return
	 */
	public static Flowchart create(Dataflow dataflow, IActuator rootActuator, Graph<IActuator, DefaultEdge> structure,
			IRuntimeScope scope) {

		Flowchart ret = new Flowchart(dataflow, scope, structure);
		ret.root = ret.compile((Actuator) rootActuator, null);
		return ret;
	}

	public static Flowchart create(Provenance provenance, IRuntimeScope scope,
			Graph<IProvenance.Node, ProvenanceEdge> graph) {

		Flowchart ret = new Flowchart(provenance, scope);
		ret.compile(graph);
		return ret;
	}

	/*
	 * basic: create all elements, then all links, no hierarchy here or multiple
	 * ports per element
	 */
	private void compile(Graph<IProvenance.Node, ProvenanceEdge> graph) {

		for (Node node : graph.vertexSet()) {
			Element element = new Element(node);
			root.children.add(element);
		}
		for (ProvenanceEdge edge : graph.edgeSet()) {
			Pair<String, String> key = new Pair<String, String>(graph.getEdgeSource(edge).getId(),
					graph.getEdgeTarget(edge).getId());
			connections.add(key);
			connectionLabels.put(key, edge.toString());
		}

	}

	private Element compile(Actuator actuator, Element parentNode) {

		if (actuator.isReference() || actuator.isInput()) {
			return null;
		}

		Element ret = new Element(actuator);

		if (parentNode != null) {
			parentNode.children.add(ret);
		}

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
				queriedConnections.put(child, queriedOutput);
			}
		}

		/*
		 * inputs and outputs from the internal model, using their reference names.
		 * Register the legitimate outputs.
		 */
		if (actuator.getModel() != null) {
			for (IObservable observable : actuator.getModel().getObservables()) {
				ret.getOrCreateOutput(observable.getReferenceName());
			}
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
				if (elementsByName.containsKey(childActuator.getName())) {
					connect(elementsByName.get(childActuator.getName()).getMainOutput(),
							ret.getOrCreateInput(childActuator.getName()));
				} else {
					throw new KlabInternalErrorException(
							"Unresolved reference in dataflow: " + childActuator.getName());
				}
			}

			/*
			 * if we have queried outputs, connect the main output of the actuator
			 */
			if (child != null && queriedConnections.containsKey(childActuator)) {
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
		 * inputs from the computation come from the parent's actuators, which have been
		 * computed upstream
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
		if (element.inputs.containsKey(input)) {
			return element.inputs.get(input);
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

				/*
				 * check if the adapter has flowchart capabilities. TODO/FIXME: this should be
				 * from the (non-existent) adapter capabilities, and there must be a remote API
				 * for this.
				 * 
				 * The adapter should also ensure that labels, names and tooltips are OK for the
				 * main element. It will enter the function with the default ones.
				 * 
				 */
				IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
				if (adapter != null) {
					IResourceEncoder encoder = adapter.getEncoder();
					if (encoder instanceof FlowchartProvider) {
						((FlowchartProvider) encoder).createFlowchart(resource, element, this);
					}
				}
			}

		} else if (contextualizable.getType() == IContextualizable.Type.CLASSIFICATION) {

			element.outputs.put(actuator.getName(), element.getOrCreateOutput(actuator.getName()));
			element.inputs.put(actuator.getName(), element.getOrCreateInput(actuator.getName()));
			element.type = ElementType.TABLE;

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
			// TODO happens with literal resources
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

	private Actuator findActuator(Object pointer, Actuator actuator) {
		if (pointer == null) {
			return null;
		}
		if (pointer instanceof IKimConcept || pointer instanceof IKimObservable) {
			pointer = pointer instanceof IKimConcept ? Concepts.INSTANCE.declare((IKimConcept) pointer)
					: Observables.INSTANCE.declare((IKimObservable) pointer, runtimeScope.getMonitor()).getType();
		}
		if ("self".equals(pointer)) {
			return actuator;
		}
		for (DefaultEdge edge : graph.incomingEdgesOf(actuator)) {
			IActuator child = graph.getEdgeSource(edge);
			if (pointer instanceof String) {
				if (((String) pointer).equals(child.getName()) || ((String) pointer).equals(child.getAlias())) {
					return (Actuator) child;
				}
			} else if (pointer instanceof ISemantic) {
				if (((Actuator) child).getObservable().is((ISemantic) pointer)) {
					return (Actuator) child;
				}
				// not found and semantics is a predicate: check for "adoption" of role or
				// attribute. SHIT won't work before the dataflow is completely computed.
				if (((ISemantic) pointer).getType().is(Type.ROLE)) {
					Map<IObservedConcept, IObservation> catalog = runtimeScope.getCatalog();
					for (IObservedConcept key : catalog.keySet()) {
						IObservation artifact = catalog.get(key);
						for (IConcept role : artifact.getObservable().getContextualRoles()) {
							if (((RuntimeScope) runtimeScope).cached_is(role, ((ISemantic) pointer).getType())) {
								return dataflow.getActuator(key.getObservable().getReferenceName());
							}
						}
					}
					for (IConcept role : actuator.getObservable().getContextualRoles()) {
						if (((RuntimeScope) runtimeScope).cached_is(role, ((ISemantic) pointer).getType())) {
							return actuator;
						}
					}
				}
			}
		}
		return null;
	}

	private void analyzeServiceCall(IServiceCall serviceCall, Actuator context, Element element, Element parent,
			String computationTarget) {

		/*
		 * Functions: check any parameters that identify artifacts against local catalog
		 * and the taginput annotations. Use exports for additional outputs and check
		 * with output map.
		 */
		IPrototype prototype = Extensions.INSTANCE.getPrototype(serviceCall.getName());

		if (prototype != null) {

			element.label = Extensions.INSTANCE.getServiceLabel(serviceCall);

			// if (prototype.getType() != IArtifact.Type.VOID) {
			element.outputs.put(computationTarget, element.getOrCreateOutput(computationTarget));
			// }

			/*
			 * match imported artifacts declared in the prototype to imports in the
			 * function. If not available, store the ID so that we can look for artifact
			 * parameters later.
			 */
			Set<String> importArgs = new HashSet<>();
			for (Argument arg : prototype.listImports()) {

				List<Actuator> actuators = new ArrayList<>();
				Object imp = serviceCall.getParameters().get(arg.getName());
				if (imp instanceof Collection) {
					for (Object o : ((Collection<?>) imp)) {
						actuators.add(findActuator(o, context));
					}
				} else {
					actuators.add(findActuator(imp, context));
				}

				for (Actuator dependency : actuators) {
					if (dependency != null) {
						element.inputs.put(dependency.getName(), element.getOrCreateInput(dependency.getName()));
						if (!dependency.getName().equals(arg.getName())) {
							element.localNames.put(dependency.getName(), arg.getName());
						}
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

						// // must be a known observables, which we will link through its alias
						// IConcept concept = parameter instanceof IConcept ? ((IConcept) parameter)
						// : ((IObservable) parameter).getType();
						//
						// for (IActuator dependency : context.getActuators()) {
						// if (concept.getSemanticDistance(((Actuator)
						// dependency).getObservable().getType()) >= 0) {
						// parameter = ((Actuator) dependency).getAlias();
						// break;
						// }
						// }
						// }
						//
						// computationInputs.add(parameter.toString());

					} else if (argument == null) {
						continue;
					} else if (argument.isExpression()) {
						Object expression = serviceCall.getParameters().get(arg);
						String expcode = expression instanceof IKimExpression ? ((IKimExpression) expression).getCode()
								: expression.toString();
						String explang = expression instanceof IKimExpression
								? ((IKimExpression) expression).getLanguage()
								: null;
						for (String input : getExpressionInputs(expcode, explang, context)) {
							Actuator dependency = findActuator(input, context);
							if (dependency != null) {
								element.inputs.put(dependency.getName(),
										element.getOrCreateInput(dependency.getName()));
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
								element.inputs.put(observable.getReferenceName(),
										element.getOrCreateInput(observable.getReferenceName()));
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
			// for (Argument arg : prototype.listExports()) {
			// if (elementsByName.containsKey(arg.getName())) {
			// computationOutputs.add(arg.getName());
			// }
			// }

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

			@Override
			public CompilerScope getCompilerScope() {
				return CompilerScope.Contextual;
			}

			@Override
			public Scope scalar(Forcing forcing) {
				// TODO Auto-generated method stub
				return this;
			}

			@Override
			public IContextualizationScope getRuntimeScope() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isForcedScalar() {
				// TODO Auto-generated method stub
				return false;
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

	public ElkConnectableShape getOutput(String observable) {
		String suffix = ".out." + observable;
		for (String node : nodes.keySet()) {
			if (node.endsWith(suffix)) {
				return nodes.get(node);
			}
		}
		return null;
	}

	public ElkNode compile(Export type) {

		this.nodes = new HashMap<>();
		this.elements = new HashMap<>();
		this.computationToNodeId = new HashMap<>();

		ElkNode ret = kelk.createGraph(runtimeScope.getContextObservation().getId(), type);
		compile(getRoot(), ret, type);

		for (Pair<String, String> connection : getConnections()) {
			ElkConnectableShape source = nodes.get(connection.getFirst());
			ElkConnectableShape target = nodes.get(connection.getSecond());
			String label = connectionLabels.get(connection);
			if (source != null && target != null) {
				if (label == null) {
					kelk.createSimpleEdge(source, target, null, type);
				} else {
					kelk.createSimpleEdge(source, target, label == null ? null : (source + "_" + target), label, type);
				}
			}
		}

		for (String s : getExternalInputs().keySet()) {
			ElkConnectableShape source = getOutput(s);
			ElkConnectableShape target = nodes.get(getExternalInputs().get(s));
			if (source != null && target != null) {
				kelk.createSimpleEdge(source, target, null, type);
			}
		}

		return ret;
	}

	public ElkNode compile(Element element, ElkNode parentNode, Export type) {

		ElkNode ret = element.getType() == ElementType.ACTUATOR
				? kelk.createActuatorNode(element.getNodeId(), parentNode, type)
				: kelk.createServiceNode(element.getNodeId(), parentNode, type);

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
			compile(child, ret, type);
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

	public String getJsonLayout(Export type) {

		RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
		ElkNode rootNode = compile(type);
		engine.layout(rootNode, new BasicProgressMonitor());
		return ElkGraphJson.forGraph(rootNode).omitLayout(false).omitZeroDimension(true).omitZeroPositions(true)
				.shortLayoutOptionKeys(true).prettyPrint(true).toJson();

	}

	public Element getElement(String nodeId) {
		return elementsById.get(Path.getLast(nodeId, '.'));
	}

	public String getResourceNodeId(IContextualizable resource) {
		Element node = elementsById.get("res" + ((ComputableResource) resource).getId());
		return node == null ? null : node.getNodeId();
	}

}
