package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.IExpression.Context;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtils;

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
    private Map<String, Element> elementsByName = new HashMap<>();
    private Map<String, Element> elementsById = new HashMap<>();
    private Map<String, String> externalInputs = new HashMap<>();
    private Map<String, String> externalOutputs = new HashMap<>();

    public static enum ElementType {
        // actor types
        ACTUATOR,
        RESOURCE,
        INSTANTIATOR,
        RESOLVER,
        TABLE,
        CONDITIONAL,
        // link types
        FLOW,
        CONTEXTUALIZATION
    }

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

    public class Element {

        private String id;
        private String name;
        private String label;
        private String description;
        private String documentation;
        private String tooltip;
        private ElementType type;

        private List<String> inputs = new ArrayList<>();
        private List<String> outputs = new ArrayList<>();
        private List<Element> children = new ArrayList<>();

        private Map<String, String> datapaths = new HashMap<>();

        Element(Actuator actuator) {

            this.id = actuator.getDataflowId();
            this.type = ElementType.ACTUATOR;
            this.name = actuator.getName();
            this.label = StringUtils.capitalize(actuator.getName().replaceAll("_", " "));

            elementsByName.put(actuator.getName(), this);
            elementsById.put(this.id, this);
            if (root == null) {
                root = this;
            }
        }

        Element(Pair<IServiceCall, IComputableResource> resource) {
            this.id = resource.getSecond().getDataflowId();
            this.type = ElementType.RESOLVER;
            this.label = Extensions.INSTANCE.getServiceLabel(resource.getFirst());
            this.name = resource.getFirst().getName();
            elementsById.put(this.id, this);
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
            return inputs;
        }

        public String getOrCreateInput(String inputId) {
            String ret = id + ".in." + inputId;
            if (!inputs.contains(ret)) {
                inputs.add(ret);
            }
            return ret;
        }

        public String getOrCreateOutput(String inputId) {
            String ret = id + ".out." + inputId;
            if (!outputs.contains(ret)) {
                outputs.add(ret);
                if (this.type == ElementType.ACTUATOR) {
                    // if this comes from an internal computation, connect it to the output port
                	boolean done = false;
                	String provider = this.datapaths.get(inputId);
                	if (provider == null && this.datapaths.size() == 1) {
                		provider = this.datapaths.get(this.datapaths.keySet().iterator().next());
                	}
                    if (provider != null && !provider.contains(".")) {
                        Element element = elementsById.get(provider);
                        if (element != null) {
                            provider = element.getOrCreateOutput(inputId);
                        }
                        done = true;
                        connections.add(new Pair<>(provider, ret));
                    }
                }
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
            return "Element [id=" + id + ", name=" + name + ", type=" + type + ", label=" + label + "]";
        }

        public String getNodeId() {
            return type == null ? id : (type.name().toLowerCase() + "." + id);
        }

        public void addChild(Element cel) {
            children.add(cel);
        }

    }

    /**
     * Create a flowchart from a dataflow. Pull out the passed output(s) if not
     * null.
     * 
     * @param dataflow
     * @return
     */
    public static Flowchart create(Dataflow dataflow, String... outputs) {
        Flowchart ret = new Flowchart();
        Element root = ret.compileActuator((Actuator) dataflow.getActuators().get(0), null);
        if (outputs != null) {
            for (String output : outputs) {
                if (output != null) {
                    ret.externalOutputs.put(output, root.getOrCreateOutput(output));
                }
            }
        }
        return ret;
    }

    private Element compileActuator(Actuator actuator, Element parent) {

        if (actuator.isReference() || actuator.isInput()) {
            return null;
        }

        Element element = new Element(actuator);

        for (IActuator child : actuator.getActuators()) {

            Element cel = compileActuator((Actuator) child, element);
            if (cel != null) {
                element.addChild(cel);
            } else if (child.isInput()) {
                externalInputs.put(child.getName(), root.getOrCreateInput(child.getName()));
            }
        }

        /*
         * compile mediations for any of the inputs. These will extend the input
         * pathways.
         */
        for (Pair<IServiceCall, IComputableResource> actor : actuator.getMediationStrategy()) {
            compileComputation(actor, element, actuator);
        }

        /*
         * go down into computations; filter inputs through local names. Track indirect
         * targets and 'self' when the input is the same name as the actuator.
         */
        for (Pair<IServiceCall, IComputableResource> actor : actuator.getComputationStrategy()) {
            compileComputation(actor, element, actuator);
        }

        return element;
    }

    private Element compileComputation(Pair<IServiceCall, IComputableResource> computation, Element parent,
            Actuator context) {

        IPrototype callPrototype = Extensions.INSTANCE.getPrototype(computation.getFirst().getName());

        Element ret = new Element(computation);

        // TODO description, documentation (template with parameter substitution)
        ret.label = callPrototype.getLabel();

        parent.addChild(ret);

        String computationTarget = computation.getSecond().getTarget() == null ? context.getName()
                : computation.getSecond().getTarget().getLocalName();

        Set<String> computationOutputs = new HashSet<>();
        Set<String> computationInputs = new HashSet<>();

        if (computation.getSecond().getServiceCall() != null) {

            /*
             * Functions: check any parameters that identify artifacts against local catalog
             * and the taginput annotations. Use exports for additional outputs and check
             * with output map.
             */
            IPrototype prototype = Extensions.INSTANCE.getPrototype(computation.getSecond().getServiceCall().getName());
            if (prototype != null) {

                if (prototype.getType() != IArtifact.Type.VOID) {
                    computationOutputs.add(computationTarget);
                }

                /*
                 * match imported artifacts declared in the prototype to imports in the function. If not
                 * available, store the ID so that we can look for artifact parameters later.
                 */
                Set<String> importArgs = new HashSet<>();
                for (Argument arg : prototype.listImports()) {
                    if (elementsByName.containsKey(arg.getName()) || parent.datapaths.containsKey(arg.getName())) {
                        computationInputs.add(arg.getName());
                    } else {
                        importArgs.add(arg.getName());
                    }
                }

                /*
                 * Add any additional parameters marked as artifact (passing the parameter) or named in expressions.
                 */
                for (String arg : computation.getSecond().getServiceCall().getParameters().keySet()) {
                    Argument argument = prototype.getArgument(arg);
                    if (importArgs.contains(arg)) {
                        computationInputs
                                .add(computation.getSecond().getServiceCall().getParameters().get(arg, String.class));
                    } else if (argument == null) {
                        continue;
                    } else if (argument.isExpression()) {
                        Object expression = computation.getSecond().getServiceCall().getParameters().get(arg);
                        String expcode = expression instanceof IKimExpression ? ((IKimExpression) expression).getCode()
                                : expression.toString();
                        String explang = expression instanceof IKimExpression
                                ? ((IKimExpression) expression).getLanguage()
                                : null;
                        for (String input : getExpressionInputs(expcode, explang, context)) {
                            computationInputs.add(input);
                        }
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

                /*
                 * Check if this is a transformation type so we can add the default target as
                 * argument if not specified.
                 */
                boolean singleArtifact = prototype.listImports().size() == 1;

                /*
                 * add any further outputs if it's used.
                 */
                for (Argument arg : prototype.listExports()) {
                    if (elementsByName.containsKey(arg.getName())) {
                        computationOutputs.add(arg.getName());
                    }
                }

                /*
                 * we let computations with a single artifact parameter default their argument
                 * to the main target.
                 */
                if (computationInputs.isEmpty() && singleArtifact) {
                    computationInputs.add(computationTarget);
                }

                if (prototype.getType() == IArtifact.Type.OBJECT) {
                    ret.type = ElementType.INSTANTIATOR;
                }
                
            } else {
                // shouldn't happen but here goes
                throw new KlabInternalErrorException("function " + computation.getSecond().getServiceCall().getName()
                        + " is undeclared in dataflow");
            }

        } else if (computation.getSecond().getExpression() != null) {

            computationOutputs.add(computationTarget);
            for (String input : getExpressionInputs(computation.getSecond().getExpression(),
                    computation.getSecond().getLanguage(), context)) {
                computationInputs.add(input);
            }

        } else if (computation.getSecond().getUrn() != null) {

            ret.type = ElementType.RESOURCE;
            IResource resource = Resources.INSTANCE.resolveResource(computation.getSecond().getUrn());
            if (resource != null) {

                if (resource.getType() != IArtifact.Type.VOID) {
                    computationOutputs.add(computationTarget);
                }

                // personalize label and description
                ret.name = resource.getUrn();
                ret.label = resource.getAdapterType().toUpperCase() + " resource";
                ret.setTooltip("Contextualize URN " + resource.getUrn());

                /*
                 * Resources: use inputs, check output map for additional outputs and add ret as
                 * a producer if used.
                 */
                for (Attribute input : resource.getInputs()) {
                    if (parent.datapaths.containsKey(input.getName())) {
                        computationInputs.add(input.getName());
                    }
                }

                for (Attribute output : resource.getOutputs()) {
                    if (elementsByName.containsKey(output.getName())) {
                        computationOutputs.add(output.getName());
                    }
                }
            }

        } else if (computation.getSecond().getType() == IComputableResource.Type.CLASSIFICATION) {

            // works as a filter. Ignore expressions in classifiers for now.
            computationInputs.add(computationTarget);
            computationOutputs.add(computationTarget);
            ret.type = ElementType.TABLE;

        } else if (computation.getSecond().getLookupTable() != null) {

            computationOutputs.add(computationTarget);

            /*
             * Lookup tables need their inputs and if the result column contains
             * expressions, they will also need their expression inputs, but we ignore them
             * as in classifications.
             */
            for (String s : computation.getSecond().getLookupTable().getArguments()) {
                if (!"?".equals(s) && !"*".equals(s) && !"self".equals(s)) {
                    computationInputs.add(s);
                }
            }
            ret.type = ElementType.TABLE;

        } else {
        	System.out.println("ZIOCA");
        }

        for (String input : computationInputs) {

            String inport = ret.getOrCreateInput(input);
            if (!parent.datapaths.containsKey(input)) {
                String actualName = formalNameOf(input, context);
                Element producer = elementsByName.get(actualName);
                if (producer != null) {
                    String output = producer.getOrCreateOutput(actualName);
                    parent.datapaths.put(input, output);
                } else if (externalInputs.containsKey(actualName)) {
                    parent.datapaths.put(input, externalInputs.get(actualName));
                }
            }
            connections.add(new Pair<>(parent.datapaths.get(input), inport));
        }

        for (String output : computationOutputs) {
            parent.datapaths.put(output, ret.id);
        }

        return ret;
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
            if (child.getAlias().equals(input)) {
                return child.getName();
            }
        }
        throw new KlabInternalErrorException("local  name " + input + " is unknown in actuator " + context);
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
        Descriptor descriptor = Extensions.INSTANCE.getLanguageProcessor(explang).describe(expcode, new Context() {

            @Override
            public Collection<String> getStateIdentifiers() {
                return stateAliases;
            }

            @Override
            public IScale getScale() {
                return ((Actuator) context).getDataflow().getScale();
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
                return ((Actuator) context).getSession().getMonitor();
            }

            @Override
            public Collection<String> getIdentifiers() {
                return aliases;
            }

            @Override
            public Type getIdentifierType(String identifier) {
                return types.get(identifier);
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

    public Map<String, String> getExternalOutputs() {
        return externalOutputs;
    }

}