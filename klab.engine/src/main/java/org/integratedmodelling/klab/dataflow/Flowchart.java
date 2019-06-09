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
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
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
            return "Element [id=" + id + ", name=" + name + ", type=" + type + "]";
        }

        public String getNodeId() {
            return type == null ? id : (type.name().toLowerCase() + "." + id);
        }

        public void addChild(Element cel) {
            children.add(cel);
        }

    }

    /**
     * Create a flowchart from a dataflow.
     * 
     * @param dataflow
     * @return
     */
    public static Flowchart create(Dataflow dataflow, String output) {
        Flowchart ret = new Flowchart();
        ret.initialize(dataflow.getActuators().get(0), output);
        ret.compile((Actuator) dataflow.getActuators().get(0), null, output);
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
    private void initialize(IActuator actuator, String output) {
        if (!(actuator instanceof Dataflow)) {
            if (actuator.isInput()) {
                externalInputs.add(actuator.getName());
            } else if (!((Actuator) actuator).isReference()) {
                Element element = compileElement(actuator);
                if (this.root == null) {
                    this.root = element;
                }
            }
        }
        for (IActuator child : actuator.getActuators()) {
            initialize(child, null);
        }
    }

    private Element compileElement(IActuator actuator) {
        Element ret = new Element();
        elements.put(actuator.getName(), ret);
        ret.id = actuator.getDataflowId();
        ret.type = Element.Type.ACTUATOR;
        ret.setName(actuator.getName());
        return ret;
    }

    private Element compile(Actuator actuator, Element parent, String pullOutput) {

        if (actuator.isReference() || actuator.isInput()) {
            return null;
        }

        Element element = elements.get(actuator.getName());
        boolean hasOutput = actuator.getObservable().is(Type.QUALITY) || actuator.getObservable().is(Type.TRAIT);
        //        if (actuator.getObservable().is(Type.COUNTABLE) && actuator.getMode() == Mode.RESOLUTION) {
        //            hasOutput = true;
        //        }

        String output = hasOutput ? element.getOrCreateOutput(actuator.getName()) : null;

        /*
         * Child actuators are either inputs to our computations, references to
         * previously seen actuators, or external inputs. We map the local name for the
         * input in this actuator to the corresponding output of the actuator that
         * produces it, or to the external input where it will be available.
         */
        Map<String, String> inputPathways = new HashMap<>();
        Map<String, String> localNames = new HashMap<>();

                for (IActuator child : actuator.getActuators()) {
        
                    localNames.put(child.getName(), child.getAlias());
                    
                    /*
                     * Computations defined in here go in as children
                     */
                    if (!child.isInput() && !((Actuator) child).isReference()) {
                        Element cel = compile((Actuator) child, element, null);
                        if (cel != null) {
                            element.addChild(cel);
                        }
                    }
                    
//        
//                    /*
//                     * Make this optional (if (separateInputs && ...))so we can also have all inputs
//                     * connect to the actuator if wanted.
//                     */
//                    if (makeActuatorPorts || !(child.getAlias().equals(child.getName()) && makeRenamedPorts)) {
//                        /*
//                         * Create an input into this element with the translated name and connect it to
//                         * the original producer, then set the input ID in the map for the aliased name.
//                         */
//                        String localInput = element.getOrCreateInput(child.getName());
//                        if (child.isInput()) {
//                            output = root.getOrCreateOutput(child.getName());
//                        } else {
//                            Element producer = elements.get(child.getName());
//                            if (producer != null) {
//                                output = producer.getOrCreateOutput(child.getName());
//                            }
//                        }
//                        if (output != null) {
//                            connections.add(new Pair<>(output, localInput));
//                            inputPathways.put(child.getAlias(), localInput);
//                        }
//        
//                    } else {
//                        /*
//                         * Find the producer, get the output ID and set it in the map for the aliased
//                         * (YES - same if not aliased) name.
//                         */
//                        Element producer = elements.get(child.getName());
//                        if (producer != null) {
//                            output = producer.getOrCreateOutput(child.getName());
//                            inputPathways.put(child.getAlias(), output);
//                        }
//                    }
        
                }

        /*
         * each actuator has one or more tracks for its targets. Should have the map of
         * localName -> current (latest) output for each target. The first time a target
         * is mentioned, the link to its previous output should be created - either the
         * original source or, if renamed, the renaming input on this element - and the
         * new output should substitute the previous in the map.
         */

        /*
         * compile mediations for any of the inputs. These will extend the input pathways.
         */
        for (Pair<IServiceCall, IComputableResource> actor : actuator.getMediationStrategy()) {
            compileComputation(actor, element, inputPathways, actuator);
        }

        /*
         * go down into computations; filter inputs through local names. Track indirect
         * targets and 'self' when the input is the same name as the actuator.
         */
        for (Pair<IServiceCall, IComputableResource> actor : actuator.getComputationStrategy()) {
            compileComputation(actor, element, inputPathways, actuator);
        }

        for (String input : inputPathways.keySet()) {
            if (input.equals(localNames.get(actuator.getName())) && output != null) {
                connections.add(new Pair<>(inputPathways.get(input), output));
            } else {
                String addout = element.getOrCreateOutput(input);
                elements.put(addout, element);
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

        if (context.getName().equals("continental_region")) {
            System.out.println("SDIDOI");
        }

        parent.addChild(ret);

        String computationTarget = computation.getSecond().getTarget() == null ? context.getAlias()
                : computation.getSecond().getTarget().getLocalName();
        // FIXME with 'do []' or other void computations this should be null

        Set<String> computationOutputs = new HashSet<>();
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

                if (prototype.getType() != IArtifact.Type.VOID) {
                    computationOutputs.add(computationTarget);
                }

                /*
                 * match imported artifacts to imports in the function
                 */
                for (Argument arg : prototype.listImports()) {
                    if (inputPathways.containsKey(arg.getName())) {
                        computationInputs.add(arg.getName());
                    }
                }

                /*
                 * Add any additional parameters marked as artifact. FIXME unsure that this is possible or should be.
                 */
                for (String arg : computation.getSecond().getServiceCall().getParameters().keySet()) {
                    Argument argument = prototype.getArgument(arg);
                    if (argument == null) {
                        continue;
                    }
                    if (argument.isArtifact()) {
                        computationInputs
                                .add(computation.getSecond().getServiceCall().getParameters().get(arg, String.class));
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
                 * Check if this is a transformation type so we can add the default target as argument
                 * if not specified.
                 */
                boolean singleArtifact = prototype.listImports().size() == 1;

                /*
                 * add any further outputs if it's used.
                 */
                for (Argument arg : prototype.listExports()) {
                    if (elements.containsKey(arg.getName())) {
                        computationOutputs.add(arg.getName());
                    }
                }

                /*
                 * we let computations with a single artifact parameter default their argument to
                 * the main target.
                 */
                if (computationInputs.isEmpty() && singleArtifact) {
                    computationInputs.add(computationTarget);
                }

                if (prototype.getType() == IArtifact.Type.OBJECT) {
                    ret.type = Element.Type.INSTANTIATOR;
                }

                if (prototype.getType() != IArtifact.Type.VOID && computationInputs.isEmpty()) {
                    throw new KlabInternalErrorException(
                            "function " + prototype.getName() + " does not declare its inputs correctly");
                }
            } else {
                // shouldn't happen but here goes
                throw new KlabInternalErrorException("function " + computation.getSecond().getServiceCall().getName()
                        + " is undeclared in dataflow");
            }

        } else if (computation.getSecond().getExpression() != null) {

            /*
             * TODO not if void (do[])
             */
            computationOutputs.add(computationTarget);
            for (String input : getExpressionInputs(computation.getSecond().getExpression(),
                    computation.getSecond().getLanguage(), context)) {
                computationInputs.add(input);
            }

        } else if (computation.getSecond().getUrn() != null) {

            ret.type = Element.Type.RESOURCE;
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
                    if (inputPathways.containsKey(input.getName())) {
                        computationInputs.add(input.getName());
                    }
                }

                for (Attribute output : resource.getOutputs()) {
                    if (elements.containsKey(output.getName())) {
                        computationOutputs.add(output.getName());
                    }
                }

            }

        } else if (computation.getSecond().getClassification() != null) {

            computationOutputs.add(computationTarget);

            /*
             * input is simply the target of the computation. May have expressions in the
             * classifiers but we can probably ignore them (they're 'global' within the
             * actuator.
             */
            computationInputs.add(computationTarget);
            ret.type = Element.Type.TABLE;

        } else if (computation.getSecond().getLookupTable() != null) {

            computationOutputs.add(computationTarget);

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

        for (String input : computationInputs) {

            String inport = ret.getOrCreateInput(input);
            if (!inputPathways.containsKey(input)) {
                Element producer = elements.get(formalNameOf(input, context));
                if (producer != null) {
                    String output = producer.getOrCreateOutput(input);
                    inputPathways.put(input, output);
                }
            }
            connections.add(new Pair<>(inputPathways.get(input), inport));
        }

        for (String output : computationOutputs) {
            String outport = ret.getOrCreateOutput(output);
            inputPathways.put(output, outport);
        }

        return ret;
    }

    /**
     * Get formal name for passed input in context actuator
     * @param input
     * @param context
     * @return
     */
    private String formalNameOf(String input, Actuator context) {
        
        for (IActuator child : context.getActuators()) {
            if (child.getAlias().equals(input)) {
                return child.getName();
            }
        }
        throw new KlabInternalErrorException("local name " + input + " is unknown in actuator " + context);
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

}
