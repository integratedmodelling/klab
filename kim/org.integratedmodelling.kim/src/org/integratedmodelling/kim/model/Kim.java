/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.kim.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kim.api.IConceptDescriptor;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimMacro;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.Literal;
import org.integratedmodelling.kim.kim.Metadata;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.kim.ObservableSemantics;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.common.SemanticType;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;

/**
 * Parsing functions and interfaces. Holds the state of the knowledge base as
 * new concepts are declared, so that the appropriate highlighting can be
 * computed.
 * 
 * @author Ferd
 *
 */
public enum Kim {

    INSTANCE;

    /**
     * This ID is used for a common project where all namespaces read from isolated
     * files will be added.
     */
    public final static String COMMON_PROJECT_ID = "klab.internal.common.project";

    private Validator validatorCallback = null;

    /**
     * Known URN descriptors. Must be filled in from the outside.
     */
    private Map<String, UrnDescriptor> urnDescriptors = new HashMap<>();

    /**
     * This contains concept descriptors for all concepts encountered, including
     * library ones and anything added externally, flattened to include children and
     * indexed by name.
     */
    private Map<String, Map<String, ConceptDescriptor>> namespaceRegister = new HashMap<>();

    /**
     * Core concepts (observation ontology) are now explicitly declared for use in
     * worldviews, and are validated through a callback.
     */
    private Map<String, EnumSet<Type>> coreConcepts = new HashMap<>();

    /**
     * Workspace for libraries. May be null. Loaded automatically at startup if
     * present, with code generation only in the engine.
     */
    private KimWorkspace libWorkspace;

    private List<Notifier> notifiers = new ArrayList<>();

    private boolean libraryInitialized;
    private boolean workspaceInited;
    private boolean initialBuildDone;

    /*
     * common workspace for test and sidecar namespaces.
     */
    private KimWorkspace commonWorkspace;

    /*
     * flags for resources and URNs
     */
    public static final int ALIVE = 0x000001;
    public static final int DEAD = 0x000002;
    public static final int ACCESSIBLE = 0x000004;
    public static final int KNOWN = 0x000008;

    /**
     * these can be installed to resolve any resource URI to the workspace root. For
     * now, k.LAB requires all resources to be filesystem based.
     */
    public static interface UriResolver {

        File resolveResourceUriToWorkspaceRootDirectory(URI uri);
    }

    private Map<String, UriResolver> uriResolvers = new HashMap<>();

    public static class ConceptDescriptor implements IConceptDescriptor {

        private String name;
        private EnumSet<Type> flags = EnumSet.noneOf(Type.class);
        private String documentation;
        private IKimConceptStatement macro;

        public ConceptDescriptor(String id, Type... flags) {
            this.name = id;
            this.flags.addAll(Arrays.asList(flags));
        }

        public ConceptDescriptor(String id, String documentation, Type... flags) {
            this.name = id;
            this.flags.addAll(Arrays.asList(flags));
            this.documentation = documentation;
        }

        public ConceptDescriptor(String id, EnumSet<Type> type, String string) {
            this.name = id;
            this.flags = type;
            this.documentation = string;
        }

        public ConceptDescriptor(String id, EnumSet<Type> type, IKimConceptStatement macro, String string) {
            this.name = id;
            this.flags = type;
            this.documentation = string;
            this.macro = macro;
        }

        @Override
        public EnumSet<Type> getFlags() {
            return flags;
        }

        @Override
        public boolean is(Type type) {
            return flags.contains(type);
        }

        @Override
        public IKimConceptStatement getMacro() {
            return macro;
        }

        @Override
        public boolean isUndefined() {
            return flags.isEmpty();
        }

        @Override
        public String getDocumentation() {
            return documentation;
        }

        @Override
        public String toString() {
            String ret = "<";
            if (this.isUndefined()) {
                ret += "UNDEFINED";
            } else {
                for (Type t : flags) {
                    ret += (ret.length() == 1 ? "" : " ") + t.name();
                }
            }
            return ret + ">";
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static enum FunctionType {
        SPACE_DOMAIN,
        TIME_DOMAIN,
        TABULAR_DATA,
        SPATIAL_DATA,
        OBJECT_DATA,
        STATE_CONTEXTUALIZER,
        PROCESS_CONTEXTUALIZER,
        SUBJECT_CONTEXTUALIZER,
        EVENT_CONTEXTUALIZER,
        RELATIONSHIP_CONTEXTUALIZER,
        SUBJECT_INSTANTIATOR,
        EVENT_INSTANTIATOR,
        RELATIONSHIP_INSTANTIATOR,
        LITERAL_ATOMIC,
        LITERAL_LIST,
    }

    public static enum LiteralType {
        NUMBER,
        TEXT,
        BOOLEAN,
        LIST
    }

    /**
     * One or more notifiers can be installed to listen to namespaces being created
     * or redefined.
     * 
     * @author Ferd
     *
     */
    public static interface Notifier {

        /**
         * Main method to notify that a new or updated namespace is available. The
         * passed bean holds all the info required to register the namespace in the
         * runtime.
         * 
         * @param namespace
         * @return whatever object the namespace produced. Will be a INamespace in all
         *         API-compliant implementations, but we avoid the dependency here and
         *         make it any object.
         */
        Object synchronizeNamespaceWithRuntime(IKimNamespace namespace);
    }

    public static class UrnDescriptor {

        private int flags = 0;
        private String documentation;

        public UrnDescriptor(int flags) {
            this.flags = flags;
        }

        public UrnDescriptor(int flags, String documentation) {
            this.flags = flags;
            this.documentation = documentation;
        }

        public boolean isAlive() {
            return (flags & ALIVE) != 0;
        }

        public boolean isDead() {
            return (flags & DEAD) != 0;
        }

        public boolean isAccessible() {
            return (flags & ACCESSIBLE) != 0;
        }

        public boolean isKnown() {
            return (flags & KNOWN) != 0;
        }

        public String getDocumentation() {
            return documentation;
        }
    }

    public static interface Validator {

        /**
         * Quickly check if the passed function name is known to the runtime.
         * 
         * @param functionName
         * @return true if known
         */
        boolean isFunctionKnown(String functionName);

        /**
         * Quickly check if the passed annotation name is known to the runtime.
         * 
         * @param annotationName
         * @return true if known
         */
        boolean isAnnotationKnown(String annotationName);

        /**
         * Return any errors, warnings and info messages caused by the passed function.
         * Only called if {@link #isFunctionKnown(String)} returns true.
         * 
         * @param functionCall
         * @param expectedType
         * @return pairs of message + level, or an empty list if OK.
         */
        List<Pair<String, Level>> validateFunction(IServiceCall functionCall, Set<IPrototype.Type> expectedType);

        /**
         * Return any errors, warnings and info messages caused by the passed annotation
         * or its usage. Only called if {@link #isAnnotationKnown(String)} returns true.
         * 
         * @param annotationCall
         * @param target
         *            the statement that the annotation describes
         * @return pairs of message + level, or an empty list if OK.
         */
        List<Pair<String, Level>> validateAnnotation(IServiceCall annotationCall, IKimStatement target);

        /**
         * 
         * @param functionId
         * @return prototype or null
         */
        IPrototype getFunctionPrototype(String functionId);

        /**
         * 
         * @param functionId
         * @return prototype or null
         */
        IPrototype getAnnotationPrototype(String functionId);

        /**
         * Return a descriptor for the passed URN. Never return null - if a URN is
         * unknown, just say so.
         * 
         * @param urn
         * @return a URN descriptor
         */
        UrnDescriptor classifyUrn(String urn);

        /**
         * If the core type is known, return the type of declarable this core type
         * represents. If it is unknown or it's not declarable, return 0.
         * 
         * @param string
         * @param statedType
         * @return the type
         */
        EnumSet<Type> classifyCoreType(String string, EnumSet<Type> statedType);

    }

    public KimConcept declareConcept(ConceptDeclaration declaration) {
        return declaration == null ? null : KimConcept.normalize(declaration, null);
    }

    public KimConcept declareConcept(ConceptDeclaration declaration, IKimMacro macro) {
        return declaration == null ? null : KimConcept.normalize(declaration, null);
    }

    public KimObservable declareObservable(ObservableSemantics declaration) {
        return declaration == null ? null : KimObservable.normalize(declaration, null);
    }

    public KimConcept declareConcept(ConceptDeclaration declaration, IKimStatement parent) {
        return declaration == null ? null : KimConcept.normalize(declaration, parent);
    }

    public KimConcept declareConcept(ConceptDeclaration declaration, IKimMacro macro, IKimStatement parent) {
        return declaration == null ? null : KimConcept.normalize(declaration, parent);
    }

    public KimObservable declareObservable(ObservableSemantics declaration, IKimStatement parent) {
        return declaration == null ? null : KimObservable.normalize(declaration, parent);
    }

    public void addNotifier(Notifier notifier) {
        this.notifiers.add(notifier);
    }

    private static UrnDescriptor okUrn = new UrnDescriptor(ALIVE | KNOWN | ACCESSIBLE, "Demo URN");

    public Number parseNumber(org.integratedmodelling.kim.kim.Number number) {
        ICompositeNode node = NodeModelUtils.findActualNodeFor(number);
        if (number.isExponential() || number.isDecimal()) {
            return Double.parseDouble(node.getText().trim());
        }
        return Integer.parseInt(node.getText().trim());
    }

    public Object parseValue(Value value, IKimNamespace namespace) {
        if (value.getLiteral() != null) {
            return parseLiteral(value.getLiteral(), namespace);
        } else if (value.getExpr() != null) {
            return value.getExpr();
        } else if (value.getFunction() != null) {
            return new KimServiceCall(value.getFunction(), null);
        } else if (value.getId() != null) {
            Object id = value.getId();
            if (namespace != null) {
                Object did = namespace.getSymbolTable().get(id);
                if (did != null) {
                    id = did;
                }
            }
            return id;
        } else if (value.getList() != null) {
            List<Object> ret = new ArrayList<>();
            for (Value val : value.getList().getContents()) {
                if (!(val.getLiteral() != null && val.getLiteral().isComma())) {
                    ret.add(parseValue(val, namespace));
                }
            }
            return ret;
        } else if (value.getMap() != null) {
            // return parseMap(value.getMap(), namespace);
        }

        return null;
    }
    
    public KimWorkspace loadWorkspace(String workspaceUri, Collection<File> projectRoots) {
    	KimWorkspace ret = KimWorkspace.getWorkspaceForResourceURI(URI.createURI(workspaceUri));
    	// TODO add projects so that we known them when loading
    	return ret;
    }


    public Parameters parseMap(Metadata map, IKimNamespace namespace) {
        Map<String, Object> ret = new HashMap<>();
        // TODO
        return new Parameters(ret);
    }

    public Object parseLiteral(Literal literal, IKimNamespace namespace) {
        if (literal.getBoolean() != null) {
            return Boolean.parseBoolean(literal.getBoolean());
        } else if (literal.getNumber() != null) {
            return parseNumber(literal.getNumber());
        } else if (literal.getId() != null) {
            Object id = literal.getId();
            if (namespace != null) {
                Object did = namespace.getSymbolTable().get(id);
                if (did != null) {
                    id = did;
                }
            }
            return id;
        } else if (literal.getString() != null) {
            return literal.getString();
        } else if (literal.getFrom() != null) {
            Range range = new Range();
            range.setLowerBound(parseNumber(literal.getFrom()).doubleValue());
            if (literal.getTo() != null) {
                range.setUpperBound(parseNumber(literal.getTo()).doubleValue());
            }
            return range;
        }
        return null;
    }

    public UrnDescriptor getUrnDescriptor(String urn) {

        /*
         * TODO if it's a FILE urn, check for existence of file as named. If it's a
         */
        UrnDescriptor ret = urnDescriptors.get(urn);

        if (ret == null && validatorCallback != null) {
            ret = validatorCallback.classifyUrn(urn);
            if (ret != null) {
                urnDescriptors.put(urn, ret);
            }
        }

        /*
         * TODO remove the OK default after all presentations are done.
         */
        return ret == null ? okUrn : ret;
    }

    public ConceptDescriptor getConceptDescriptor(String conceptId) {
        SemanticType st = new SemanticType(conceptId);
        Map<String, ConceptDescriptor> map = null;
        if (st.isCorrect()) {
            map = namespaceRegister.get(st.getNamespace());
        }
        return map == null ? null : map.get(st.getName());
    }

    public void setUrnDescriptor(String urn, UrnDescriptor descriptor) {
        urnDescriptors.put(urn, descriptor);
    }

    public void setConceptDescriptor(String conceptId, ConceptDescriptor descriptor) {
        setConceptDescriptor(conceptId, descriptor, false);
    }

    public void setConceptDescriptor(String conceptId, ConceptDescriptor descriptor, boolean createNamespace) {
        SemanticType st = new SemanticType(conceptId);
        Map<String, ConceptDescriptor> map = namespaceRegister.get(st.getNamespace());
        if (map == null) {
            if (createNamespace) {
                map = initializeNamespaceRegisters(st.getNamespace());
            } else {
                throw new UnsupportedOperationException("namespace for " + conceptId + " is not registered");
            }
        }
        map.put(st.getName(), descriptor);
    }

    public Map<String, ConceptDescriptor> initializeNamespaceRegisters(String string) {
        Map<String, ConceptDescriptor> ret = new HashMap<>();
        namespaceRegister.put(string, ret);
        // namespaceModels.put(string, new HashMap<>());
        return ret;
    }

    public boolean isLibraryInitialized() {
        return libraryInitialized;
    }

    public void setLibraryInitialized(boolean b) {
        libraryInitialized = b;
    }

    /**
     * Get the workspace containing the worldview and any library.
     *
     * @param libname
     *            the libname
     * @param overridingProjects
     *            the overriding projects
     * @return the workspace
     */
    public KimWorkspace getLibrary(String libname, File... overridingProjects) {

        if (!workspaceInited) {
            // TODO substitute with callback
            libWorkspace = new KimWorkspace(
                    new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + libname));
            workspaceInited = true;
        }
        return libWorkspace;
    }

    public boolean hasErrors(EObject object) {
        ICompositeNode node = NodeModelUtils.getNode(object);
        return node == null || checkNodeForErrors(node);
    }

    private boolean checkNodeForErrors(INode node) {
        if (node.getSyntaxErrorMessage() != null) {
            return true;
        }
        if (node instanceof ICompositeNode) {
            for (INode child : ((ICompositeNode) node).getChildren()) {
                if (checkNodeForErrors(child)) {
                    return true;
                }
            }
        }
        return false;
    }

    public EnumSet<Type> makeQuality(EnumSet<Type> original, Type... quality) {
        // remove any direct observable flags, add the one specified and quality
        EnumSet<Type> ret = EnumSet.copyOf(original);
        ret.removeAll(IKimConcept.DIRECT_OBSERVABLE_TYPES);
        for (Type t : quality) {
            ret.add(t);
            if (t == Type.DISTANCE) {
                // this does not go in by itself
                ret.add(Type.EXTENSIVE_PROPERTY);
            }
        }
        ret.add(Type.QUALITY);
        ret.add(Type.OBSERVABLE);
        return ret;
    }

    public void reportLibraryError(Issue issue) {
        // TODO use callback
        System.out.println(issue.toString());
    }

    public EnumSet<Type> getType(String string) {

        String id = string.toLowerCase();

        switch (id) {
        case "thing":
            return EnumSet.of(Type.SUBJECT, Type.DIRECT_OBSERVABLE, Type.COUNTABLE, Type.OBSERVABLE);
        case "class":
            return EnumSet.of(Type.CLASS, Type.QUALITY, Type.OBSERVABLE);
        case "quantity":
            return EnumSet.of(Type.QUANTITY, Type.QUALITY, Type.OBSERVABLE);
        case "quality":
            return EnumSet.of(Type.QUALITY, Type.OBSERVABLE);
        case "ordering":
            return EnumSet.of(Type.ORDERING, Type.TRAIT, Type.ATTRIBUTE);
        case "attribute":
            return EnumSet.of(Type.ATTRIBUTE, Type.TRAIT);
        case "identity":
            return EnumSet.of(Type.IDENTITY, Type.TRAIT);
        case "role":
            return EnumSet.of(Type.ROLE);
        case "realm":
            return EnumSet.of(Type.REALM, Type.TRAIT);
        case "domain":
            return EnumSet.of(Type.DOMAIN);
        case "energy":
            return EnumSet.of(Type.ENERGY, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "entropy":
            return EnumSet.of(Type.ENTROPY, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "length":
            return EnumSet.of(Type.LENGTH, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "mass":
            return EnumSet.of(Type.MASS, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "volume":
            return EnumSet.of(Type.VOLUME, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "weight":
            return EnumSet.of(Type.WEIGHT, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "money":
            return EnumSet.of(Type.MONEY, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "duration":
            return EnumSet.of(Type.DURATION, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "area":
            return EnumSet.of(Type.AREA, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "presence":
            return EnumSet.of(Type.PRESENCE, Type.QUALITY, Type.OBSERVABLE);
        case "proportion":
            return EnumSet.of(Type.PROPORTION, Type.QUALITY, Type.OBSERVABLE);
        case "uncertainty":
            return EnumSet.of(Type.UNCERTAINTY, Type.QUALITY, Type.OBSERVABLE);
        case "acceleration":
            return EnumSet.of(Type.ACCELERATION, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "priority":
            return EnumSet.of(Type.PRIORITY, Type.QUALITY, Type.OBSERVABLE);
        case "value":
            return EnumSet.of(Type.VALUE, Type.QUALITY, Type.OBSERVABLE);
        case "ratio":
            return EnumSet.of(Type.RATIO, Type.QUALITY, Type.OBSERVABLE);
        case "count":
            return EnumSet.of(Type.NUMEROSITY, Type.QUALITY, Type.OBSERVABLE);
        case "electric-potential":
            return EnumSet.of(Type.ELECTRIC_POTENTIAL, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "charge":
            return EnumSet.of(Type.CHARGE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "resistance":
            return EnumSet.of(Type.RESISTANCE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "amount":
            return EnumSet.of(Type.AMOUNT, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "resistivity":
            return EnumSet.of(Type.RESISTIVITY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "occurrence":
            return EnumSet.of(Type.OCCURRENCE, Type.QUALITY, Type.OBSERVABLE);
        case "probability":
            return EnumSet.of(Type.PROBABILITY, Type.QUALITY, Type.OBSERVABLE);
        case "pressure":
            return EnumSet.of(Type.PRESSURE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "angle":
            return EnumSet.of(Type.ANGLE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "velocity":
            return EnumSet.of(Type.VELOCITY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "temperature":
            return EnumSet.of(Type.TEMPERATURE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "viscosity":
            return EnumSet.of(Type.VISCOSITY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "distance":
            return EnumSet.of(Type.DISTANCE, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE);
        case "process":
            return EnumSet.of(Type.PROCESS, Type.DIRECT_OBSERVABLE, Type.OBSERVABLE);
        case "assessment":
            return EnumSet.of(Type.ASSESSMENT, Type.PROCESS, Type.DIRECT_OBSERVABLE, Type.OBSERVABLE);
        case "agent":
            return EnumSet.of(Type.AGENT, Type.DIRECT_OBSERVABLE, Type.COUNTABLE, Type.OBSERVABLE);
        case "event":
            return EnumSet.of(Type.EVENT, Type.DIRECT_OBSERVABLE, Type.COUNTABLE, Type.OBSERVABLE);
        case "relationship":
            return EnumSet.of(Type.RELATIONSHIP, Type.DIRECT_OBSERVABLE, Type.COUNTABLE, Type.OBSERVABLE);
        case "configuration":
            return EnumSet.of(Type.CONFIGURATION);
        case "extent":
            return EnumSet.of(Type.EXTENT);
        case "observability":
            return EnumSet.of(Type.OBSERVABILITY, Type.DENIABLE, Type.TRAIT);
        }

        throw new UnsupportedOperationException("internal error: type " + string + " not handled");
    }

    static public String getTypeDescription(EnumSet<Type> type) {

        for (Type t : IKimConcept.QUALITY_TYPES) {
            if (type.contains(t)) {
                return t.name().toLowerCase();
            }
        }
        for (Type t : IKimConcept.TRAIT_TYPES) {
            if (type.contains(t)) {
                return t.name().toLowerCase();
            }
        }
        for (Type t : IKimConcept.DECLARABLE_TYPES) {
            if (type.contains(t)) {
                return t.name().toLowerCase();
            }
        }
        return "";
    }

    static public EnumSet<Type> intersection(EnumSet<Type> a, EnumSet<Type> b) {
        EnumSet<Type> x = EnumSet.copyOf(a);
        x.retainAll(b);
        return x;
    }

    static public boolean isCompatible(EnumSet<Type> a, EnumSet<Type> b) {
        EnumSet<Type> common = intersection(a, b);
        // one and only one common declarable must have left
        return intersection(common, IKimConcept.DECLARABLE_TYPES).size() == 1;
    }

    public void setUriResolver(String scheme, UriResolver resolver) {
        this.uriResolvers.put(scheme, resolver);
    }

    public UriResolver getUriResolver(String scheme) {
        return this.uriResolvers.get(scheme);
    }

    public KimNamespace getNamespace(EObject statement, boolean createIfAbsent) {

        KimNamespace ret = null;
        Namespace namespace = KimValidator.getNamespace(statement);

        if (namespace != null) {
            KimProject project = null;
            KimWorkspace workspace = KimWorkspace.getWorkspaceForResource(namespace.eResource());
            if (workspace != null) {
                project = workspace.getProjectForResource(namespace.eResource());
            }
            if (project != null) {
                ret = project.getNamespace(EcoreUtil.getURI(namespace), namespace, createIfAbsent);
            } else if (createIfAbsent) {
                // it's a project-less namespace, e.g. a sidecar file
                project = getCommonProject();
                ret = project.getNamespace(EcoreUtil.getURI(namespace), namespace, createIfAbsent);
            }
        }

        return ret;
    }

    public KimProject getCommonProject() {
        KimWorkspace workspace = getCommonWorkspace();
        return workspace.getProjects().iterator().next();
    }

    public KimWorkspace getCommonWorkspace() {
        if (this.commonWorkspace == null) {
            File tempDir = new File(System.getProperty("java.io.tmpdir"));
            try {
                this.commonWorkspace = new KimWorkspace(tempDir.toURI().toURL(), tempDir);
                URL url = new URL(this.commonWorkspace.getURL() + "/" + COMMON_PROJECT_ID);
                KimProject ret = new KimProject(COMMON_PROJECT_ID, commonWorkspace, new Properties());
                this.commonWorkspace.registerProject(ret, url);
            } catch (MalformedURLException e) {
                // fffff
                throw new RuntimeException(e);
            }
        }
        return this.commonWorkspace;
    }

    /**
     * 
     * @return all workspaces
     */
    public Collection<KimWorkspace> getWorkspaces() {
        return KimWorkspace.getWorkspaces();
    }

    /**
     * Return a project by ID, looking up in all workspaces or (optionally) in the workspaces under a passed 
     * file root.
     * 
     * @param id the project ID
     * @param workspaceRoot the file root of the workspace. If the project is present in more than one workspace, the version in the
     *        passed root is returned. Can be null, if so the first instance of the project found is returned.
     * @return the project or null 
     */
    public KimProject getProject(String id, File workspaceRoot) {

        KimProject ret = null;
        for (KimWorkspace workspace : getWorkspaces()) {
            for (KimProject project : workspace.getProjects()) {
                if (project.getName().equals(id)) {
                    if (workspaceRoot == null) {
                        return project;
                    } else if (workspace.getRoot().equals(workspaceRoot) || ret == null) {
                        ret = project;
                    }
                }
            }
        }
        return ret;
    }

    public void removeNamespace(Namespace namespace) {

        KimProject project = null;
        if (namespace.isWorldviewBound()) {
            project = getCommonWorkspace().getProjects().iterator().next();
        } else {
            project = KimWorkspace.getWorkspaceForResource(namespace.eResource())
                    .getProjectForResource(namespace.eResource());
        }

        if (project != null) {
            project.removeNamespace(namespace);
        }
    }

    public void handleAddition(String relativePath) {
        if (relativePath.endsWith(".kim")) {
            // System.out.println("ADDED " + relativePath);
        }
    }

    public void handleChange(String relativePath) {
        if (relativePath.endsWith(".kim")) {
            // System.out.println("CHANGED " + relativePath);
        }
    }

    public void handleDeletion(String relativePath) {
        if (relativePath.endsWith(".kim")) {
            // System.out.println("DELETED " + relativePath);
        }
    }

    /**
     * Create a number of concepts mimicking the IM worldview for testing.
     */
    public void setupTestConcepts() {
        setConceptDescriptor("geography:Elevation",
                new ConceptDescriptor("geography:Elevation", "The height of the terrain over sea level.",
                        Type.OBSERVABLE, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.LENGTH),
                true);
        setConceptDescriptor("geography:Slope", new ConceptDescriptor("geography:Slope", Type.OBSERVABLE, Type.QUALITY,
                Type.INTENSIVE_PROPERTY, Type.ANGLE), true);
        setConceptDescriptor("hydrology:HydrologicalSoilGroup",
                new ConceptDescriptor("hydrology:HydrologicalSoilGroup", Type.OBSERVABLE, Type.QUALITY, Type.CLASS),
                true);
        setConceptDescriptor("infrastructure:City", new ConceptDescriptor("infrastructure:City", Type.OBSERVABLE,
                Type.SUBJECT, Type.COUNTABLE, Type.DIRECT_OBSERVABLE), true);
        setConceptDescriptor("infrastructure:Building", new ConceptDescriptor("infrastructure:Building",
                Type.OBSERVABLE, Type.SUBJECT, Type.COUNTABLE, Type.DIRECT_OBSERVABLE), true);
        setConceptDescriptor("earth:Earthquake", new ConceptDescriptor("earth:Earthquake", Type.OBSERVABLE, Type.EVENT,
                Type.COUNTABLE, Type.DIRECT_OBSERVABLE), true);
        setConceptDescriptor("hydrology:SurfaceWaterFlow", new ConceptDescriptor("hydrology:SurfaceWaterFlow",
                Type.OBSERVABLE, Type.PROCESS, Type.DIRECT_OBSERVABLE), true);
        setConceptDescriptor("im:Tall",
                new ConceptDescriptor("im:Tall", Type.TRAIT, Type.ATTRIBUTE, Type.ORDERING, Type.SUBJECTIVE), true);
        setConceptDescriptor("im:Small",
                new ConceptDescriptor("im:Small", Type.TRAIT, Type.ATTRIBUTE, Type.ORDERING, Type.SUBJECTIVE), true);
        setConceptDescriptor("im:Normalized", new ConceptDescriptor("im:Normalized", Type.TRAIT, Type.ATTRIBUTE), true);
        setConceptDescriptor("im:Large",
                new ConceptDescriptor("im:Large", Type.TRAIT, Type.ATTRIBUTE, Type.ORDERING, Type.SUBJECTIVE), true);
        setConceptDescriptor("im:Potential", new ConceptDescriptor("im:Potential", Type.TRAIT, Type.ATTRIBUTE), true);
        setConceptDescriptor("im:Level", new ConceptDescriptor("im:Level", Type.TRAIT, Type.ATTRIBUTE, Type.ORDERING,
                Type.ABSTRACT, Type.SUBJECTIVE), true);
        setConceptDescriptor("im:High",
                new ConceptDescriptor("im:High", Type.TRAIT, Type.ATTRIBUTE, Type.ORDERING, Type.SUBJECTIVE), true);
        setConceptDescriptor("im:Moderate",
                new ConceptDescriptor("im:Moderate", Type.TRAIT, Type.ATTRIBUTE, Type.ORDERING, Type.SUBJECTIVE), true);
        setConceptDescriptor("im:Low",
                new ConceptDescriptor("im:Low", Type.TRAIT, Type.ATTRIBUTE, Type.ORDERING, Type.SUBJECTIVE), true);
        setConceptDescriptor("materials:Concrete",
                new ConceptDescriptor("materials:Concrete", Type.TRAIT, Type.IDENTITY), true);
    }

    public boolean initialBuildDone() {
        return initialBuildDone;
    }

    public void setInitialBuildDone(boolean b) {
        initialBuildDone = b;
    }

    /**
     * Validate a function call by returning a list of the correspondent
     * notifications. If the list is empty, the call is valid. If null is returned,
     * the function is unknown to the runtime.
     * 
     * @param call
     * @param expectedReturnType
     * @return a list of notifications, possibly empty, or null if the function is
     *         unknown.
     */
    public List<KimNotification> validateFunctionCall(IServiceCall call, Set<IPrototype.Type> expectedReturnType) {
        List<KimNotification> ret = new ArrayList<>();
        if (validatorCallback != null) {
            List<Pair<String, Level>> results = validatorCallback.validateFunction(call, expectedReturnType);
            if (results != null) {
                for (Pair<String, Level> result : results) {
                    ret.add(new KimNotification(result.getFirst(), result.getSecond()));
                }
            }
        }
        return ret;
    }

    /**
     * Validate a function call by returning a list of the correspondent
     * notifications. If the list is empty, the call is valid. If null is returned,
     * the function is unknown to the runtime.
     * 
     * @param call
     * @param target
     * @return a list of notifications, possibly empty, or null if the function is
     *         unknown.
     */
    public List<KimNotification> validateAnnotation(IKimAnnotation call, IKimStatement target) {
        List<KimNotification> ret = new ArrayList<>();
        if (validatorCallback != null) {
            List<Pair<String, Level>> results = validatorCallback.validateAnnotation(call, target);
            if (results != null) {
                for (Pair<String, Level> result : results) {
                    ret.add(new KimNotification(result.getFirst(), result.getSecond()));
                }
            }
        }
        return ret;
    }

    /**
     * True if the function name is known to the runtime
     * 
     * @param name
     * @return true if known
     */
    public boolean isFunctionKnown(String name) {
        return validatorCallback != null && validatorCallback.isFunctionKnown(name);
    }

    /**
     * True if the function name is known to the runtime
     * 
     * @param name
     * @return true if known
     */
    public boolean isAnnotationKnown(String name) {
        return validatorCallback != null && validatorCallback.isAnnotationKnown(name);
    }

    /**
     * Check a core type ID against a stated type and return the (possibly fixed)
     * type if OK, 0 if type is incompatible.
     * 
     * @param string
     * @param type
     * @return the final type
     */
    public EnumSet<Type> checkCoreConcept(String string, EnumSet<Type> type) {

        EnumSet<Type> ret = EnumSet.noneOf(Type.class);

        if (coreConcepts.containsKey(string)) {
            ret = coreConcepts.get(string);
        } else {
            /**
             * use whatever type the callback returned. Without callback, we just take the
             * first stated type as the truth.
             */
            if (validatorCallback == null) {
                ret = type;
            } else {
                ret = validatorCallback.classifyCoreType(string, type);
            }
        }

        /*
         * if no match between the "truth" and the requested type, return 0 which will
         * cause an error.
         */
        EnumSet<Type> copy = EnumSet.copyOf(ret);
        copy.retainAll(IKimConcept.DECLARABLE_TYPES);
        if (copy.size() != 1) {
            return EnumSet.noneOf(Type.class);
        }

        /*
         * if OK and not seen before, record it so that we don't reuse it in a different
         * way.
         */
        if (coreConcepts.get(string) == null) {
            coreConcepts.put(string, EnumSet.copyOf(ret));
        }
        /*
         * TODO return the type set through the callback
         */
        return ret;
    }

    public void declareCoreConceptPeer(String worldviewConcept, String coreConcept) {
        // TODO use callback to define inheritance from core concept
    }

    public Validator getValidator() {
        return validatorCallback;
    }

    public void setValidator(Validator callback) {
        validatorCallback = callback;
    }

    public List<Notifier> getNotifiers() {
        return this.notifiers;
    }

    public void warn(String warning) {
        // TODO redirect to a callback
        System.err.println("WARNING: " + warning);
    }

    public void error(String warning) {
        // TODO redirect to a callback
        System.err.println("ERROR: " + warning);
    }

    public void info(String warning) {
        // TODO redirect to a callback
        System.out.println("INFO: " + warning);
    }

    public void debug(String warning) {
        // TODO redirect to a callback
        System.out.println("DEBUG: " + warning);
    }

}
